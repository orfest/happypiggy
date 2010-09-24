package ru.nsu.ccfit.pm.econ.view.shared;

import java.util.Collection;
import java.util.List;

import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.ICompanyRoster;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.view.ICompanyMessageListener;

/**
 * Gateway for views to interact with <tt>PlayerController</tt> company roster.
 * Also provides means for company message publishing, if called from server.
 * @see ICompanyRoster
 * @see ICompanyMessageListener
 * 
 * @author dragonfly
 */
public class CompanyRosterGateway implements ICompanyRoster, ICompanyMessageListener {
	
	static final Logger logger = LoggerFactory.getLogger(CompanyRosterGateway.class);
	
	protected CompanyMessageListenerList listeners = new CompanyMessageListenerList();
	private ICompanyRoster controller;

	@Override
	public IUCompany getCompanyById(long companyId) {
		return controller.getCompanyById(companyId);
	}

	@Override
	public IUCompany getCompanyByName(String companyName) {
		return controller.getCompanyByName(companyName);
	}

	@Override
	public List<? extends IUCompany> getCompanyList() {
		return controller.getCompanyList();
	}

	@Override
	public void publishOfficialMessage(IUTextOnlyCompanyMessage message) {
		controller.publishOfficialMessage(message);
	}

	@Override
	public void publishOfficialMessage(long messageId) {
		controller.publishOfficialMessage(messageId);
	}

	@Override
	public void publishRumorMessage(IUTextOnlyCompanyMessage message,
			Collection<? extends IUPlayer> receivers) {
		
		controller.publishRumorMessage(message, receivers);
	}

	@Override
	public void publishRumorMessage(long messageId, Collection<Long> receiverIds) {
		controller.publishRumorMessage(messageId, receiverIds);
	}

	@Override
	public void onCompanyMessageReceived(final IUTextOnlyCompanyMessage companyMessage) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onCompanyMessageReceived(companyMessage);
			}
		});
	}

	@Override
	public void onCumulativeCompanyMessagesUpdate(
			final Collection<? extends IUTextOnlyCompanyMessage> addedMessages) {
		
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onCumulativeCompanyMessagesUpdate(addedMessages);
			}
		});
	}
	
	public ListenerList<ICompanyMessageListener> getCompanyMessageListeners() {
		return this.listeners;
	}

	@Inject
	public void setCompanyRoster(ICompanyRoster controller) {
		if (this.controller != null)
			logger.warn("Redefining controller");
		this.controller = controller;
	}
	
	protected static class CompanyMessageListenerList
		extends ListenerList<ICompanyMessageListener> implements ICompanyMessageListener {

		@Override
		public void onCompanyMessageReceived(IUTextOnlyCompanyMessage companyMessage) {
			for (ICompanyMessageListener listener : this)
				listener.onCompanyMessageReceived(companyMessage);
		}

		@Override
		public void onCumulativeCompanyMessagesUpdate(
				Collection<? extends IUTextOnlyCompanyMessage> addedMessages) {

			for (ICompanyMessageListener listener : this)
				listener.onCumulativeCompanyMessagesUpdate(addedMessages);
		}
		
	}

}

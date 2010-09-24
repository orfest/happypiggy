package ru.nsu.ccfit.pm.econ.common.view;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;

/**
 * Listener interface for monitoring company messages.
 * 
 * @author dragonfly
 */
public interface ICompanyMessageListener {
	
	void onCompanyMessageReceived(IUTextOnlyCompanyMessage companyMessage);
	
	void onCumulativeCompanyMessagesUpdate(Collection<? extends IUTextOnlyCompanyMessage> addedMessages);

}

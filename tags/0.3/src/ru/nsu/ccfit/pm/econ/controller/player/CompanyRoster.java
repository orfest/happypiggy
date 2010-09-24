package ru.nsu.ccfit.pm.econ.controller.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.ICompanyRoster;
import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventConsumer;
import ru.nsu.ccfit.pm.econ.common.controller.player.IPlayerRoster;
import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTransaction;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBuyOffersChangeEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUCompanyMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUDividendPayoutEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameSnapshotEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTransactionEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTransferSharesEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.view.ICompanyMessageListener;
import ru.nsu.ccfit.pm.econ.common.view.IEconomicActivityListener;
import ru.nsu.ccfit.pm.econ.controller.player.data.Company;
import ru.nsu.ccfit.pm.econ.controller.player.data.CompanyMessage;
import ru.nsu.ccfit.pm.econ.controller.player.data.GameTime;
import ru.nsu.ccfit.pm.econ.controller.player.data.TextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.controller.player.events.CompanyMessageEvent;

public class CompanyRoster extends AbstractGameEventGatewayClient implements
		IGameEventConsumer, ICompanyRoster {
	
	static final Logger logger = LoggerFactory.getLogger(CompanyRoster.class);
	
	private List<Company> companyList = new ArrayList<Company>();
	
	/*
	 * Injectables.
	 */
	private IULocalState local;
	private IPlayerRoster playerRoster;
	private ICompanyMessageListener listener;
	private IEconomicActivityListener eaListener;
	
	@Inject
	public void setLocalState(IULocalState local) {
		this.local = local;
	}

	@Inject
	public void setPlayerRoster(IPlayerRoster playerRoster) {
		this.playerRoster = playerRoster;
	}
	
	@Inject
	public void setCompanyMessageListener(ICompanyMessageListener listener) {
		this.listener = listener;
	}
	
	@Inject
	public void setEconomicActivityListener(IEconomicActivityListener listener) {
		this.eaListener = listener;
	}

	@Override
	public boolean processEvent(IUGameEvent event) {
		if (event instanceof IUGameSnapshotEvent) {
			processGameSnapshotEvent((IUGameSnapshotEvent)event);
		} else if (event instanceof IUCompanyMessageEvent) {
			processCompanyMessageEvent((IUCompanyMessageEvent)event);
		} else if (event instanceof IUDividendPayoutEvent) {
			processDividendPayoutEvent((IUDividendPayoutEvent)event);
		} else if (event instanceof IUTransactionEvent) {
			processTransactionEvent((IUTransactionEvent)event);
		} else if (event instanceof IUTransactionEvent) {
			processTransferSharesEvent((IUTransferSharesEvent)event);
		} else if (event instanceof IUBuyOffersChangeEvent) {
			processBuyOffersChangeEvent((IUBuyOffersChangeEvent)event);
		} else {
			return false;
		}
		
		return true;
	}

	private void processGameSnapshotEvent(IUGameSnapshotEvent event) {
		synchronized (companyList) {
			companyList.clear();
			for (IUCompany company : event.getScenario().getCompanies()) {
				companyList.add(new Company(company, local, playerRoster));
			}
			// TODO call ICompanyMessageListener#onCumulativeCompanyMessagesUpdate if needed
		}
	}
	
	private void processCompanyMessageEvent(IUCompanyMessageEvent event) {
		IUTextOnlyCompanyMessage message = event.getCompanyMessage();
		Company company = getModifiableCompanyById(message.getCompanyId());
		
		if (!local.isInsideServer()) {
			company.addPublishedMessage(new TextOnlyCompanyMessage(message));
		} else {
			// The message is already marked as published during publish call
			// and receivers are set. However, we need to set publish time
			// and add it to the published messages list.
			CompanyMessage internalMessage = getCompanyMessageById(message.getId());
			internalMessage.setPublished(true);
			internalMessage.setPublishTime(new GameTime(message.getPublishTime()));
			company.addPublishedMessage(internalMessage);
		}
		
		listener.onCompanyMessageReceived(message);
	}
	
	private void processDividendPayoutEvent(IUDividendPayoutEvent event) {
		Company company = getModifiableCompanyById(event.getCompanyId());
		company.setProfitForPreviousPeriod(event.getCompanyProfit());
		company.setDividendPayoutRatio(event.getDividendPayoutRatio());
	}
	
	private void processTransactionEvent(IUTransactionEvent event) {
		IUTransaction transaction = event.getTransaction();
		IUShareHolding shareHolding = transaction.getShareHolding();
		Company company = getModifiableCompanyById(shareHolding.getCompanyId());
		company.transferShareHolding(shareHolding.getAmount(), 
				transaction.getSellerId(), transaction.getBuyerId());
	}
	
	private void processTransferSharesEvent(IUTransferSharesEvent event) {
		IUShareHolding shareHolding = event.getTransferredShareHolding();
		Company company = getModifiableCompanyById(shareHolding.getCompanyId());
		company.transferShareHolding(shareHolding.getAmount(), 
				event.getSenderId(), shareHolding.getOwnerId());
		// TODO check that ownerId is as expected (i.e. updated)
	}
	
	private void processBuyOffersChangeEvent(IUBuyOffersChangeEvent event) {
		Company company = getModifiableCompanyById(event.getCompanyId());
		company.setShareMarketValue(event.getNewShareMarketValue());
		eaListener.onShareMarketValueChange(company, event.getNewShareMarketValue());
	}

	@Override
	public IUCompany getCompanyById(long companyId) {
		return getModifiableCompanyById(companyId);
	}

	@Override
	public IUCompany getCompanyByName(String companyName) {
		synchronized (companyList) {
			for (IUCompany company : companyList) {
				if (company.getName().equals(companyName))
					return company;
			}
		}
		
		return null;
	}

	@Override
	public List<? extends IUCompany> getCompanyList() {
		return Collections.unmodifiableList(companyList);
	}
	
	private Company getModifiableCompanyById(long companyId) {
		synchronized (companyList) {
			for (Company company : companyList) {
				if (company.getId() == companyId)
					return company;
			}
		}
		
		return null;
	}

	@Override
	public void publishOfficialMessage(IUTextOnlyCompanyMessage message) {
		publishOfficialMessage(message.getId());
	}

	@Override
	public void publishOfficialMessage(long messageId) {
		if (!local.isInsideServer())
			throw new IllegalStateException("Message publishing is not allowed from client");
		
		CompanyMessage message = getCompanyMessageById(messageId);
		if (message == null)
			throw new IllegalArgumentException("No message with id=" + messageId + " found");
		
		message.publishToAll(playerRoster);
		CompanyMessageEvent event = new CompanyMessageEvent(local, message);
		gateway.sendEvent(event);
	}

	@Override
	public void publishRumorMessage(IUTextOnlyCompanyMessage message,
			Collection<? extends IUPlayer> receivers) {
		
		Vector<Long> receiverIds = new Vector<Long>(receivers.size());
		for (IUPlayer player : receivers) {
			receiverIds.add(player.getId());
		}
		
		publishRumorMessage(message.getId(), receiverIds);
	}

	@Override
	public void publishRumorMessage(long messageId, Collection<Long> receiverIds) {
		if (!local.isInsideServer())
			throw new IllegalStateException("Message publishing is not allowed from client");

		CompanyMessage message = getCompanyMessageById(messageId);
		if (message == null)
			throw new IllegalArgumentException("No message with id=" + messageId + " found");

		message.publishTo(receiverIds, playerRoster);
		CompanyMessageEvent event = new CompanyMessageEvent(local, message);
		event.setReceiverIds(receiverIds);
		gateway.sendEvent(event);
		
	}
	
	private CompanyMessage getCompanyMessageById(long id) {
		if (!local.isInsideServer())
			throw new IllegalStateException("This method cannot be run from client");
		
		synchronized (companyList) {
			for (Company company : companyList) {
				CompanyMessage message = company.getModifiableMessageById(id);
				if (message != null && message.getId() == id)
					return message;
			}
		}
		
		return null;
	}

}

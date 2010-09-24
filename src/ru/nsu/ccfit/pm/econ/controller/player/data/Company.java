package ru.nsu.ccfit.pm.econ.controller.player.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.controller.player.IPlayerRoster;
import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;

import static com.google.common.base.Preconditions.checkNotNull;

public class Company implements IUCompany {
	
	static final Logger logger = LoggerFactory.getLogger(Company.class);
	
	public int accountingPeriod;
	public List<CompanyMessage> allMessages;
	public String companyType;
	public String description;
	public double dividendPayoutRatio;
	public double expectedProfit;
	public long id;
	public String name;
	public double profitBeforeGameStart;
	public double profitForPreviousPeriod;
	public List<TextOnlyCompanyMessage> publishedMessages;
	public Map<Long, ShareHolding> shareHoldings;			// keys are ownerIds
	public double shareMarketValue;
	public double teacherSharePart;
	public int totalSharesAmount;
	
	public Company(IUCompany c, IULocalState localState, IPlayerRoster playerRoster) {
		this.accountingPeriod = c.getAccountingPeriod();
		this.companyType = c.getCompanyType();
		this.description = c.getDescription();
		this.dividendPayoutRatio = c.getDividendPayoutRatio();
		this.expectedProfit = c.getExpectedProfit();
		this.id = c.getId();
		this.name = c.getName();
		this.profitBeforeGameStart = c.getProfitBeforeGameStart();
		this.profitForPreviousPeriod = c.getProfitForPreviousPeriod();
		this.shareMarketValue = c.getShareMarketValue();
		this.teacherSharePart = c.getTeacherSharePart();
		this.totalSharesAmount = c.getTotalSharesAmount();
		
		if (localState.isInsideServer()) {
			checkNotNull(c.getAllMessages(), "IUCompany#getAllMessages() should not be null when run in server");
			
			this.allMessages = new ArrayList<CompanyMessage>();
			this.publishedMessages = new ArrayList<TextOnlyCompanyMessage>();
			
			for (IUCompanyMessage companyMessage : c.getAllMessages()) {
				CompanyMessage mesg = new CompanyMessage(companyMessage, playerRoster);
				
				this.allMessages.add(mesg);
				if (mesg.isPublished())
					this.publishedMessages.add(mesg);
			}
		} else {
			this.publishedMessages = new ArrayList<TextOnlyCompanyMessage>();
			
			for (IUTextOnlyCompanyMessage companyMessage : c.getPublishedMessages()) {
				TextOnlyCompanyMessage mesg = new TextOnlyCompanyMessage(companyMessage);
				this.publishedMessages.add(mesg);
			}
		}
		
		
		this.shareHoldings = new HashMap<Long, ShareHolding>();

		synchronized (shareHoldings) {
			for (IUShareHolding shareHolding : c.getShareHoldings()) {
				ShareHolding sh = new ShareHolding(shareHolding);
				this.shareHoldings.put(sh.getOwnerId(), sh);
			}
		}
	}

	@Override
	public int getAccountingPeriod() {
		return accountingPeriod;
	}

	@Override
	public Collection<? extends IUCompanyMessage> getAllMessages() {
		checkNotNull(allMessages, "IUCompany#getAllMessages() may be called only on server");
		return Collections.unmodifiableCollection(allMessages);
	}

	@Override
	public String getCompanyType() {
		return companyType;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public double getDividendPayoutRatio() {
		return dividendPayoutRatio;
	}

	@Override
	public double getExpectedProfit() {
		return expectedProfit;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getProfitBeforeGameStart() {
		return profitBeforeGameStart;
	}

	@Override
	public double getProfitForPreviousPeriod() {
		return profitForPreviousPeriod;
	}

	@Override
	public Collection<? extends IUTextOnlyCompanyMessage> getPublishedMessages() {
		return Collections.unmodifiableCollection(publishedMessages);
	}

	@Override
	public Collection<? extends IUShareHolding> getShareHoldings() {
		return Collections.unmodifiableCollection(shareHoldings.values());
	}

	@Override
	public double getShareMarketValue() {
		return shareMarketValue;
	}

	@Override
	public double getTeacherSharePart() {
		return teacherSharePart;
	}

	@Override
	public int getTotalSharesAmount() {
		return totalSharesAmount;
	}
	
	public void addPublishedMessage(IUTextOnlyCompanyMessage message) {
		publishedMessages.add(new TextOnlyCompanyMessage(message));
	}
	
	public void setProfitForPreviousPeriod(double value) {
		profitForPreviousPeriod = value;
	}
	
	public void setDividendPayoutRatio(double value) {
		dividendPayoutRatio = value;
	}
	
	public void transferShareHolding(int amount, long fromId, long toId) {
		synchronized (shareHoldings) {
			ShareHolding from = shareHoldings.get(fromId);
			ShareHolding to = shareHoldings.get(toId);
			
			if (to == null) {
				to = new ShareHolding(id, toId, 0);
				shareHoldings.put(toId, to);
			}
			
			if (from == null || to == null || from.getAmount() < amount) {
				logger.warn("Attempted invalid share transfer ({} shares from {} to {}); " 
						+ "local state had not been changed", 
						new Object[]{amount, fromId, toId});
				// TODO this is a hack to workaround bug #30 at initial share distribution
				if (to != null && from == null) {
					to.setAmount(to.getAmount() + amount);
				}
				return;
			}
			
			from.setAmount(from.getAmount() - amount);
			to.setAmount(to.getAmount() + amount);
			
			if (from.getAmount() == 0)
				shareHoldings.remove(fromId);
		}
	}
	
	public CompanyMessage getModifiableMessageById(long id) {
		checkNotNull(allMessages, "Unable to search for company message. Are we in client mode?");
		
		synchronized (allMessages) {
			for (CompanyMessage message : allMessages) {
				if (message.getId() == id)
					return message;
			}
		}
		
		return null;
	}

	public void setShareMarketValue(double shareMarketValue) {
		this.shareMarketValue = shareMarketValue;
	}

}

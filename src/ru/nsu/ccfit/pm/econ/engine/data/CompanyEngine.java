package ru.nsu.ccfit.pm.econ.engine.data;

import java.util.ArrayList;
import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;

/**
 * IUCompany interface implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUCompany
 */
public class CompanyEngine implements IUCompany {

	private int accountingPeriod;
	private ArrayList<CompanyMessageEngine> allMessages;
	private String companyType;
	private String description;
	private double dividendPayoutRatio;
	private double expectedProfit;
	private long id;
	private String name;
	private double profitBeforeGameStart;
	private double profitForPreviousPeriod;
	private ArrayList<TextOnlyCompanyMessageEngine> publishedMessages;
	private ArrayList<ShareHoldingEngine> shareHoldings;
	private double shareMarketValue;
	private double teacherSharePart;
	private int totalSharesAmount;

	public CompanyEngine(IUCompany toCopy) {
		accountingPeriod = toCopy.getAccountingPeriod();
		companyType = toCopy.getCompanyType();
		description = toCopy.getDescription();
		dividendPayoutRatio = toCopy.getDividendPayoutRatio();
		expectedProfit = toCopy.getExpectedProfit();
		id = toCopy.getId();
		name = toCopy.getName();
		profitBeforeGameStart = toCopy.getProfitBeforeGameStart();
		profitForPreviousPeriod = toCopy.getProfitForPreviousPeriod();
		shareMarketValue = toCopy.getShareMarketValue();
		teacherSharePart = toCopy.getTeacherSharePart();
		totalSharesAmount = toCopy.getTotalSharesAmount();
		allMessages = new ArrayList<CompanyMessageEngine>(toCopy
				.getAllMessages().size());
		for (IUCompanyMessage cm : toCopy.getAllMessages())
			allMessages.add(new CompanyMessageEngine(cm));
		publishedMessages = new ArrayList<TextOnlyCompanyMessageEngine>(toCopy
				.getPublishedMessages().size());
		for (IUTextOnlyCompanyMessage tm : toCopy.getPublishedMessages())
			publishedMessages.add(new TextOnlyCompanyMessageEngine(tm));
		shareHoldings = new ArrayList<ShareHoldingEngine>(toCopy
				.getShareHoldings().size());
		for (IUShareHolding sh : toCopy.getShareHoldings())
			shareHoldings.add(new ShareHoldingEngine(sh));
	}

	public CompanyEngine(int accountingPeriod,
			Collection<CompanyMessageEngine> allMessages, String companyType,
			String description, double dividendPayoutRatio,
			double expectedProfit, long id, String name,
			double profitBeforeGameStart, double profitForPreviousPeriod,
			Collection<TextOnlyCompanyMessageEngine> publishedMessages,
			Collection<ShareHoldingEngine> shareHoldings,
			double shareMarketValue, double teacherSharePart,
			int totalSharesAmount) {
		this.accountingPeriod = accountingPeriod;
		this.allMessages = new ArrayList<CompanyMessageEngine>(allMessages);
		this.companyType = companyType;
		this.description = description;
		this.dividendPayoutRatio = dividendPayoutRatio;
		this.expectedProfit = expectedProfit;
		this.id = id;
		this.name = name;
		this.profitBeforeGameStart = profitBeforeGameStart;
		this.profitForPreviousPeriod = profitForPreviousPeriod;
		this.publishedMessages = new ArrayList<TextOnlyCompanyMessageEngine>(
				publishedMessages);
		this.shareHoldings = new ArrayList<ShareHoldingEngine>(shareHoldings);
		this.shareMarketValue = shareMarketValue;
		this.teacherSharePart = teacherSharePart;
		this.totalSharesAmount = totalSharesAmount;
	}

	@Override
	public int getAccountingPeriod() {
		return accountingPeriod;
	}

	@Override
	public ArrayList<CompanyMessageEngine> getAllMessages() {
		return allMessages;
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
	public ArrayList<TextOnlyCompanyMessageEngine> getPublishedMessages() {
		return publishedMessages;
	}

	@Override
	public ArrayList<ShareHoldingEngine> getShareHoldings() {
		return shareHoldings;
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

	public void setAccountingPeriod(int accountingPeriod) {
		this.accountingPeriod = accountingPeriod;
	}

	public void setAllMessages(ArrayList<CompanyMessageEngine> allMessages) {
		this.allMessages = allMessages;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDividendPayoutRatio(double dividendPayoutRatio) {
		this.dividendPayoutRatio = dividendPayoutRatio;
	}

	public void setExpectedProfit(double expectedProfit) {
		this.expectedProfit = expectedProfit;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProfitBeforeGameStart(double profitBeforeGameStart) {
		this.profitBeforeGameStart = profitBeforeGameStart;
	}

	public void setProfitForPreviousPeriod(double profitForPreviousPeriod) {
		this.profitForPreviousPeriod = profitForPreviousPeriod;
	}

	public void setPublishedMessages(
			ArrayList<TextOnlyCompanyMessageEngine> publishedMessages) {
		this.publishedMessages = publishedMessages;
	}

	public void setShareHoldings(ArrayList<ShareHoldingEngine> shareHoldings) {
		this.shareHoldings = shareHoldings;
	}

	public void setShareMarketValue(double shareMarketValue) {
		this.shareMarketValue = shareMarketValue;
	}

	public void setTeacherSharePart(double teacherSharePart) {
		this.teacherSharePart = teacherSharePart;
	}

	public void setTotalSharesAmount(int totalSharesAmount) {
		this.totalSharesAmount = totalSharesAmount;
	}

}

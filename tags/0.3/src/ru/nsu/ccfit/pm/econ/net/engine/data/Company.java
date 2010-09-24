package ru.nsu.ccfit.pm.econ.net.engine.data;

import java.util.Collection;
import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.CompanyProto;

/**
 * @author orfest
 *
 */
@ProtoClass(value=CompanyProto.class)
public class Company implements IUCompany {

	@SerializeThis
	private int accountingPeriod = Integer.MAX_VALUE;
	@SerializeThis
	@BewareCollectionOf(CompanyMessage.class)
	private Collection<? extends IUCompanyMessage> allMessages = new LinkedList<IUCompanyMessage>();
	@SerializeThis
	private String companyType = "";
	@SerializeThis
	private String description = "";
	@SerializeThis
	private double dividendPayoutRatio = Double.MAX_VALUE;
	@SerializeThis
	private double expectedProfit = Double.MAX_VALUE;
	@SerializeThis
	private long id = Long.MAX_VALUE;
	@SerializeThis
	private String name = "";
	@SerializeThis
	private double profitBeforeGameStart = Double.MAX_VALUE;
	@SerializeThis
	private double profitForPreviousPeriod = Double.MAX_VALUE;
	@SerializeThis
	@BewareCollectionOf(IUTextOnlyCompanyMessage.class)
	private Collection<? extends IUTextOnlyCompanyMessage> publishedMessages = new LinkedList<IUTextOnlyCompanyMessage>();
	@SerializeThis
	@BewareCollectionOf(IUShareHolding.class)
	private Collection<? extends IUShareHolding> shareHoldings = new LinkedList<IUShareHolding>();
	@SerializeThis
	private double shareMarketValue = Double.MAX_VALUE;
	@SerializeThis
	private double teacherSharePart = Double.MAX_VALUE;
	@SerializeThis
	private int totalSharesAmount = Integer.MAX_VALUE;

	@Override
	public int getAccountingPeriod() {
		return accountingPeriod;
	}

	@Override
	public Collection<? extends IUCompanyMessage> getAllMessages() {
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
	public Collection<? extends IUTextOnlyCompanyMessage> getPublishedMessages() {
		return publishedMessages;
	}

	@Override
	public Collection<? extends IUShareHolding> getShareHoldings() {
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

	public void setAllMessages(Collection<? extends IUCompanyMessage> allMessages) {
		this.allMessages = allMessages;
	}

	public void setShareHoldings(
			Collection<? extends IUShareHolding> shareHoldings) {
		this.shareHoldings = shareHoldings;
	}

	public void setAccountingPeriod(int accountingPeriod) {
		this.accountingPeriod = accountingPeriod;
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
			Collection<? extends IUTextOnlyCompanyMessage> publishedMessages) {
		this.publishedMessages = publishedMessages;
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

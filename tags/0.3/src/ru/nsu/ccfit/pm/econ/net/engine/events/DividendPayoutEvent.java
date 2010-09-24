package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUDividendPayoutEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;

/**
 * @author orfest
 * 
 */
public class DividendPayoutEvent extends GameEvent implements
		IUDividendPayoutEvent {

	@SerializeThis
	private double dividendPayoutRatio = Double.MAX_VALUE;
	@SerializeThis
	private double companyProfit = Double.MAX_VALUE;
	@SerializeThis
	private double dividendPayoutValue = Double.MAX_VALUE;
	@SerializeThis
	private long companyId = Long.MAX_VALUE;

	public DividendPayoutEvent(){
	}

	public long getCompanyId() {
		return companyId;
	}

	public double getDividendPayoutValue() {
		return dividendPayoutValue;
	}

	public double getCompanyProfit() {
		return companyProfit;
	}

	public double getDividendPayoutRatio() {
		return dividendPayoutRatio;
	}

	public void setDividendPayoutRatio(double dividendPayoutRatio) {
		this.dividendPayoutRatio = dividendPayoutRatio;
	}

	public void setCompanyProfit(double companyProfit) {
		this.companyProfit = companyProfit;
	}

	public void setDividendPayoutValue(double dividendPayoutValue) {
		this.dividendPayoutValue = dividendPayoutValue;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

}

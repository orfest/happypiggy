package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankPercentEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;

/**
 * @author orfest
 * 
 */
public class BankPercentEvent extends GameEvent implements IUBankPercentEvent {

	@SerializeThis
	private double loanInterestRate = Double.MAX_VALUE;
	@SerializeThis
	private double depositInterestRate = Double.MAX_VALUE;

	public BankPercentEvent() {
	}

	public double getLoanInterestRate() {
		return loanInterestRate;
	}

	public double getDepositInterestRate() {
		return depositInterestRate;
	}

	public void setLoanInterestRate(double loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

	public void setDepositInterestRate(double depositInterestRate) {
		this.depositInterestRate = depositInterestRate;
	}

}

package ru.nsu.ccfit.pm.econ.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankRequestEvent;

/**
 * Class that holds loan request to bank
 * 
 * @author pupatenko
 * 
 * @see IUBankRequestEvent
 */
public class BankLoanRequestEngine {
	private double initialValue;
	private int period;

	public BankLoanRequestEngine(double initialValue, int period) {
		this.initialValue = initialValue;
		this.period = period;
	}

	public BankLoanRequestEngine(BankLoanRequestEngine toCopy) {
		initialValue = toCopy.initialValue;
		period = toCopy.period;
	}

	public double getInitialValue() {
		return initialValue;
	}

	public int getPeriod() {
		return period;
	}

	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}

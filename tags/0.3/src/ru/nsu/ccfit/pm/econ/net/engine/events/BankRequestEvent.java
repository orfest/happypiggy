package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.data.BankTransactionType;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankRequestEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;

/**
 * @author orfest
 * 
 */
public class BankRequestEvent extends GameEvent implements IUBankRequestEvent {
	@SerializeThis
	private BankTransactionType transactionType = BankTransactionType.DEPOSIT;
	@SerializeThis
	private double initialValue = Double.MAX_VALUE;
	@SerializeThis
	private int period = Integer.MAX_VALUE;

	public BankRequestEvent() {
	}

	public BankTransactionType getTransactionType() {
		return transactionType;
	}

	public double getInitialValue() {
		return initialValue;
	}

	public int getPeriod() {
		return period;
	}

	public void setTransactionType(BankTransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}

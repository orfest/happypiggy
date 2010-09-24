package ru.nsu.ccfit.pm.econ.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.BankTransactionType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBankTransaction;

/**
 * IUBankTransaction implementation for engine.
 * 
 * @author pupatenko
 */
public class BankTransactionEngine implements IUBankTransaction {

	private long bankId;
	private long clientId;
	private double initialValue;
	private double interestRate;
	private int issueTurn;
	private int repayTurn;
	private double repayValue;
	private BankTransactionType type;

	/*
	 * public BankTransactionEngine() { }
	 */

	public BankTransactionEngine(IUBankTransaction toCopy) {
		this.bankId = toCopy.getBankId();
		this.clientId = toCopy.getClientId();
		this.initialValue = toCopy.getInitialValue();
		this.interestRate = toCopy.getInterestRate();
		this.issueTurn = toCopy.getIssueTurn();
		this.repayTurn = toCopy.getRepayTurn();
		this.repayValue = toCopy.getRepayValue();
		this.type = toCopy.getType();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BankTransactionEngine) {
			BankTransactionEngine b = (BankTransactionEngine) obj;
			return (b.bankId == bankId && b.clientId == clientId
					&& b.initialValue == initialValue
					&& b.interestRate == interestRate
					&& b.issueTurn == issueTurn && b.repayTurn == repayTurn && b.repayValue == repayValue);
		} else
			return super.equals(obj);
	}

	public BankTransactionEngine(long bankId, long clientId,
			double initialValue, double interestRate, int issueTurn,
			int repayTurn, double repayValue, BankTransactionType type) {
		this.bankId = bankId;
		this.clientId = clientId;
		this.initialValue = initialValue;
		this.interestRate = interestRate;
		this.issueTurn = issueTurn;
		this.repayTurn = repayTurn;
		this.repayValue = repayValue;
		this.type = type;
	}

	@Override
	public long getBankId() {
		return bankId;
	}

	@Override
	public long getClientId() {
		return clientId;
	}

	@Override
	public double getInitialValue() {
		return initialValue;
	}

	@Override
	public double getInterestRate() {
		return interestRate;
	}

	@Override
	public int getIssueTurn() {
		return issueTurn;
	}

	@Override
	public int getRepayTurn() {
		return repayTurn;
	}

	@Override
	public double getRepayValue() {
		return repayValue;
	}

	@Override
	public BankTransactionType getType() {
		return type;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public void setIssueTurn(int issueTurn) {
		this.issueTurn = issueTurn;
	}

	public void setRepayTurn(int repayTurn) {
		this.repayTurn = repayTurn;
	}

	public void setRepayValue(double repayValue) {
		this.repayValue = repayValue;
	}

	public void setType(BankTransactionType type) {
		this.type = type;
	}

}

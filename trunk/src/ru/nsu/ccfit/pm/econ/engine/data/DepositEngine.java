package ru.nsu.ccfit.pm.econ.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.BankTransactionType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUDeposit;

/**
 * IUDeposit interface implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUDeposit
 */
public class DepositEngine implements IUDeposit {

	private BankTransactionType type;
	private long bankId;
	private long clientId;
	private double initialValue;
	private double interestRate;
	private int issueTurn;
	private int repayTurn;
	private double repayValue;

	public DepositEngine(BankTransactionType type, long bankId, long clientId,
			double initialValue, double interestRate, int issueTurn,
			int repayTurn, double repayValue) {
		this.type = type;
		this.bankId = bankId;
		this.clientId = clientId;
		this.initialValue = initialValue;
		this.interestRate = interestRate;
		this.issueTurn = issueTurn;
		this.repayTurn = repayTurn;
		this.repayValue = repayValue;
	}

	public DepositEngine(IUDeposit toCopy) {
		type = toCopy.getType();
		bankId = toCopy.getBankId();
		clientId = toCopy.getClientId();
		initialValue = toCopy.getInitialValue();
		interestRate = toCopy.getInterestRate();
		issueTurn = toCopy.getIssueTurn();
		repayTurn = toCopy.getRepayTurn();
		repayValue = toCopy.getRepayValue();
	}

	@Override
	public BankTransactionType getType() {
		return type;
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

	public void setType(BankTransactionType type) {
		this.type = type;
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

}

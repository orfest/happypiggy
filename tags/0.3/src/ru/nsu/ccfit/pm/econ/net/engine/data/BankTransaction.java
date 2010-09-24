package ru.nsu.ccfit.pm.econ.net.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.BankTransactionType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBankTransaction;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.BankTransactionProto;

/**
 * @author orfest
 *
 */
@ProtoClass(value=BankTransactionProto.class)
public class BankTransaction implements IUBankTransaction {

	@SerializeThis
	private BankTransactionType type = BankTransactionType.DEPOSIT;
	@SerializeThis
	private double repayValue = Double.MAX_VALUE;
	@SerializeThis
	private int repayTurn = Integer.MAX_VALUE;
	@SerializeThis
	private int issueTurn = Integer.MAX_VALUE;
	@SerializeThis
	private double interestRate = Double.MAX_VALUE;
	@SerializeThis
	private double initialValue = Double.MAX_VALUE;
	@SerializeThis
	private long clientId = Long.MAX_VALUE;
	@SerializeThis
	private long bankId = Long.MAX_VALUE;

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

	public void setType(BankTransactionType type) {
		this.type = type;
	}

	public void setRepayValue(double repayValue) {
		this.repayValue = repayValue;
	}

	public void setRepayTurn(int repayTurn) {
		this.repayTurn = repayTurn;
	}

	public void setIssueTurn(int issueTurn) {
		this.issueTurn = issueTurn;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

}

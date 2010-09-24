/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.BankTransactionType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUDeposit;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.DepositProto;

/**
 * @author orfest
 * 
 */
@ProtoClass(DepositProto.class)
public class Deposit implements IUDeposit {

	@SerializeThis
	private BankTransactionType type;
	@SerializeThis
	private long bankId;
	@SerializeThis
	private long clientId;
	@SerializeThis
	private double initialValue;
	@SerializeThis
	private double interestRate;
	@SerializeThis
	private int issueTurn;
	@SerializeThis
	private int repayTurn;
	@SerializeThis
	private double repayValue;
	
	public Deposit() {
	}

	public BankTransactionType getType() {
		return type;
	}

	public void setType(BankTransactionType type) {
		this.type = type;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public double getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getIssueTurn() {
		return issueTurn;
	}

	public void setIssueTurn(int issueTurn) {
		this.issueTurn = issueTurn;
	}

	public int getRepayTurn() {
		return repayTurn;
	}

	public void setRepayTurn(int repayTurn) {
		this.repayTurn = repayTurn;
	}

	public double getRepayValue() {
		return repayValue;
	}

	public void setRepayValue(double repayValue) {
		this.repayValue = repayValue;
	}

}

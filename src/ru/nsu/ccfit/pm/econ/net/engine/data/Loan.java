/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.BankTransactionType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.LoanProto;

/**
 * @author orfest
 *
 */
@ProtoClass(value=LoanProto.class)
public class Loan implements IULoan {

	@SerializeThis
	private BankTransactionType type = BankTransactionType.DEPOSIT;
	@SerializeThis
	private long bankId = Long.MIN_VALUE;
	@SerializeThis
	private long clientId = Long.MIN_VALUE;
	@SerializeThis
	private double initialValue = Double.MIN_NORMAL;
	@SerializeThis
	private double interestRate = Double.MIN_VALUE;
	@SerializeThis
	private int issueTurn;
	@SerializeThis
	private int repayTurn;
	@SerializeThis
	private double repayValue;
	
	public Loan() {
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

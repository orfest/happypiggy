/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net.engine.roles;

import java.util.Collection;
import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUDeposit;
import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUStudent;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.Deposit;
import ru.nsu.ccfit.pm.econ.net.engine.data.Loan;

/**
 * @author orfest
 *
 */
public class Student extends Player implements IUStudent {

	@SerializeThis
	private double cash = Double.MIN_VALUE;
	@SerializeThis
	@BewareCollectionOf(value=Loan.class)
	private Collection<? extends IULoan> unmodifiableLoanList = new LinkedList<IULoan>();
	@SerializeThis
	@BewareCollectionOf(value=Deposit.class)
	private Collection<? extends IUDeposit> unmodifiableDepositList = new LinkedList<IUDeposit>();
	
	/**
	 * 
	 */
	public Student() {
		// TODO Auto-generated constructor stub
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public Collection<? extends IULoan> getUnmodifiableLoanList() {
		return unmodifiableLoanList;
	}

	public void setUnmodifiableLoanList(Collection<? extends IULoan> unmodifiableLoanList) {
		this.unmodifiableLoanList = unmodifiableLoanList;
	}

	public Collection<? extends IUDeposit> getUnmodifiableDepositList() {
		return unmodifiableDepositList;
	}

	public void setUnmodifiableDepositList(Collection<? extends IUDeposit> unmodifiableDepositList) {
		this.unmodifiableDepositList = unmodifiableDepositList;
	}

}

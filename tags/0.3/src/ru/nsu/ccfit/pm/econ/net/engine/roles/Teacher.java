/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net.engine.roles;

import java.util.Collection;

import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUTeacher;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.Loan;

/**
 * @author orfest
 *
 */
public class Teacher extends Player implements IUTeacher {

	@SerializeThis
	private double loanInterestRate = Double.MIN_VALUE;
	@SerializeThis
	@BewareCollectionOf(value=Loan.class)
	private Collection<? extends IULoan> unmodifiableTakenLoansList = new LinkedList<IULoan>(); 
	
	/**
	 * 
	 */
	public Teacher() {
	}

	public double getLoanInterestRate() {
		return loanInterestRate;
	}

	public void setLoanInterestRate(double loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

	public Collection<? extends IULoan> getUnmodifiableTakenLoansList() {
		return unmodifiableTakenLoansList;
	}

	public void setUnmodifiableTakenLoansList(Collection<? extends IULoan> unmodifiableTakenLoansList) {
		this.unmodifiableTakenLoansList = unmodifiableTakenLoansList;
	}

}

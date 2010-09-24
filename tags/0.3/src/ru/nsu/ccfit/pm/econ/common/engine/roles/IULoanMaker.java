package ru.nsu.ccfit.pm.econ.common.engine.roles;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;

/**
 * Unmodifiable interface denoting players that are able to make a loan.
 * @author dragonfly
 */
public interface IULoanMaker extends IUPlayer {
	
	/**
	 * Unexpired player loans.
	 * @return Collection of player loans.
	 */
	Collection<? extends IULoan> getUnmodifiableLoanList();

}

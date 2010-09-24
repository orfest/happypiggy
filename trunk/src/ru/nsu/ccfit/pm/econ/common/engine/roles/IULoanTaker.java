package ru.nsu.ccfit.pm.econ.common.engine.roles;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;

/**
 * Unmodifiable interface denoting players that are able to loan other 
 * players, i.e. to play a bank role.
 * @author dragonfly
 */
public interface IULoanTaker extends IUPlayer {
	
	/**
	 * Unexpired loans made for other players.
	 * @return Collection of taken loans.
	 */
	Collection<? extends IULoan> getUnmodifiableTakenLoansList();
	
	/**
	 * Bank loan interest rate during this game turn (if turn is not 
	 * finished yet).
	 * @return Loan interest rate during current turn.
	 */
	double getLoanInterestRate();

}

package ru.nsu.ccfit.pm.econ.common.engine.events;

import javax.annotation.Nonnegative;

/**
 * Unmodifiable interface for setting bank interest rates.
 * Bank should set it's interest rate in the beginning of 
 * each turn.
 * @author dragonfly
 */
public interface IUBankPercentEvent extends IUGameEvent {
	
	/**
	 * Loan interest rate per turn.
	 * @return Loan interest rate.
	 */
	@Nonnegative
	double getLoanInterestRate();
	
	/**
	 * Deposit interest rate per turn.
	 * @return Deposit interest rate.
	 */
	@Nonnegative
	double getDepositInterestRate();

}

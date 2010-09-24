package ru.nsu.ccfit.pm.econ.common.engine.roles;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUDeposit;

/**
 * Unmodifiable interface denoting players that are able to take deposits from other 
 * players, i.e. to play a bank role.
 * @author dragonfly
 */
public interface IUDepositTaker extends IUPlayer {
	
	/**
	 * Unexpired deposits taken from other players.
	 * @return Collection of taken deposits.
	 */
	Collection<? extends IUDeposit> getUnmodifiableTakenDepositsList();
	
	/**
	 * Bank deposit interest rate during this game turn (if turn is not 
	 * finished yet).
	 * @return Deposit interest rate during current turn.
	 */
	double getDepositInterestRate();

}

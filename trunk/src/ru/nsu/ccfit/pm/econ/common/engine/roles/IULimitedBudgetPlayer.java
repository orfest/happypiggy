package ru.nsu.ccfit.pm.econ.common.engine.roles;

/**
 * Unmodifiable interface that denotes a player that has a limited amount of money. 
 * @author dragonfly
 *
 */
public interface IULimitedBudgetPlayer extends IUPlayer {
	
	/**
	 * This player's current amount of money. 
	 * <p>This number can be negative, in which case player is not able to spend money, 
	 * only to receive it. This can happen if certain some of money was repossessed
	 * by a bank due to a loan expiring.</p> 
	 * @return Current amount of money.
	 */
	double getCash();

}

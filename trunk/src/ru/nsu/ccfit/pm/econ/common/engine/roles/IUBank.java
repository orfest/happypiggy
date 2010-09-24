package ru.nsu.ccfit.pm.econ.common.engine.roles;

/**
 * Unmodifiable interface denoting bank player (i.e. player that is able to loan other player, 
 * take deposits from them, and receive loans from teacher player). 
 * @author dragonfly
 */
public interface IUBank extends IUPlayer, IULimitedBudgetPlayer, IULoanMaker, IULoanTaker, IUDepositTaker {
	
	/**
	 * Short bank name. Used by end players to distinguish between multiple banks. 
	 * @return Bank name.
	 */
	String getBankName();

}

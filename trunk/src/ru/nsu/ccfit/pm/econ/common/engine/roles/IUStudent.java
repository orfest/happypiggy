package ru.nsu.ccfit.pm.econ.common.engine.roles;

/**
 * Unmodifiable interface denoting student player (i.e. ordinary player 
 * without bank capabilities). 
 * @author dragonfly
 *
 */
public interface IUStudent extends IUPlayer, IULimitedBudgetPlayer, IULoanMaker, IUDepositMaker {

}

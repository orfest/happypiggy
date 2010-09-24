package ru.nsu.ccfit.pm.econ.common.view;

import ru.nsu.ccfit.pm.econ.common.controller.player.IMineStatsAccessor;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IULimitedBudgetPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUShareholder;

/**
 * Listener for player properties updates.
 * @see IMineStatsAccessor
 * 
 * @author dragonfly
 */
public interface IPlayerStatsUpdateListener {
	
	/**
	 * Fired when player role gets determined, together with its id.
	 * @param player Player role object.
	 */
	void onRoleDetermined(IUPlayer player);
	
	/**
	 * Fired when cash balance changes.
	 * @param player Limited budget player role object.
	 * @param oldCashValue Cash value before update.
	 * @param newCashValue Current (after update) cash value.
	 */
	void onCashChange(IULimitedBudgetPlayer player, double oldCashValue, double cashValue);
	
	/**
	 * Fired when player's shares change.
	 * @param player Shareholder player role object.
	 * @param updatedShareHolding New share holding object after update.
	 * @param addedAmount Number of shares added (if positive) or 
	 * 						subtracted (if negative) during share 
	 * 						transaction operation.
	 * @param subtractedMoney Amount of money that was subtracted 
	 * 						(if positive) to buy the share holding.
	 * 						If this value is negative, it's absolute
	 * 						value indicates amount of money received 
	 * 						during transaction. 
	 */
	void onSharesChange(IUShareholder player, IUShareHolding updatedShareHolding, 
			int addedAmount, double subtractedMoney);

}

package ru.nsu.ccfit.pm.econ.common.view;

import ru.nsu.ccfit.pm.econ.common.controller.player.IEconomicActivities;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;

import com.google.gag.annotation.remark.LOL;

/**
 * Listener interface for economic activity (index) change notifications.
 * 
 * @author dragonfly
 */
public interface IEconomicActivityListener {

	/**
	 * Fired when stock index changes.
	 * 
	 * @see IEconomicActivities#getStockIndex()
	 * @param newStockIndex
	 *            New stock index value.
	 */
	void onStockIndexChange(double newStockIndex);

	/**
	 * Fired when share market value of some company changes.
	 * 
	 * @param company
	 *            Company in question.
	 * @param newShareMarketValue
	 *            New share market value for that company.
	 */
	void onShareMarketValueChange(IUCompany company, double newShareMarketValue);

	/**
	 * Fired when share market value dramatically changes during a single
	 * (current) turn.
	 * 
	 * @see IEconomicActivities#setDramaticShareMarketPrecentageChange(double)
	 * @param company
	 *            Company that caused this event.
	 * @param valueChange
	 *            Current share market value minus share market value at the
	 *            turn start.
	 * @param valueChangePrecentage
	 *            <tt>valueChange</tt> expressed in percents of original value,
	 *            i.e. divided by share market value at the turn start.
	 */
	@LOL
	void onDramaticTurnShareMarketValueChange(IUCompany company,
			double valueChange, double valueChangePrecentage);

}

package ru.nsu.ccfit.pm.econ.view.shared;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;

/**
 * Listener interface for company stock chart lines visibility changes.
 * 
 * @author dragonfly
 */
public interface IStockChartVisibilityListener {
	
	/**
	 * Fired when player change visibility of company index on the graph
	 * 
	 * @param company Subject company
	 * @param isVisible New visibility state
	 */
	void onVisibilityChange(IUCompany company, boolean isVisible);

}

package ru.nsu.ccfit.pm.econ.common.controller.player;

import javax.annotation.Nonnegative;

import ru.nsu.ccfit.pm.econ.common.view.IEconomicActivityListener;

/**
 * Interface that provides access to economic activities, as well as
 * to economic activity notification settings.
 * @see IEconomicActivityListener
 * 
 * @author dragonfly
 */
public interface IEconomicActivities {
	
	/**
	 * @return Current stock index value.
	 */
	double getStockIndex();
	
	/**
	 * Define a value that is used to determine, whether to fire 
	 * {@link IEconomicActivityListener#onDramaticTurnShareMarketValueChange(IUCompany, double, double)}
	 * event or not. Default value is positive infinity.
	 * @param precentage Absolute percentage value that should be exceeded for 
	 * 					share market value change to be considered dramatic.
	 */
	void setDramaticShareMarketPrecentageChange(@Nonnegative double precentage);
	
	/**
	 * @return Absolute precentage value that should be exceeded for 
	 * 			share market value change to be considered dramatic.
	 */
	double getDramaticShareMarketPrecentageChange();

}

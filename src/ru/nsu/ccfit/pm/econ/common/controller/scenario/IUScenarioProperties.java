package ru.nsu.ccfit.pm.econ.common.controller.scenario;

import javax.annotation.Nonnegative;
import javax.annotation.Nullable;

/**
 * Unmodifiable interface that represents various scenario properties.
 * @author dragonfly
 */
public interface IUScenarioProperties {
	
	/**
	 * Scenario name. This value is used by end users to distinguish between multiple scenarios.
	 * @return Short scenario name.
	 */
	String getName();
	
	/**
	 * Scenario description. May be quite lengthy. May be empty or <tt>null</tt>.
	 * @return Scenario description.
	 */
	@Nullable
	String getDescription();
	
	/**
	 * Scenario author or authors. May be empty or <tt>null</tt>.
	 * @return Scenario author(s).
	 */
	@Nullable
	String getAuthor();
	
	/**
	 * Full currency name, subjective case, starting with lowercase letter.
	 * <p>For example: "рубль"</p>
	 * @return Full currency name.
	 */
	String getFullCurrencyName();
	
	/**
	 * Short currency name or symbol.
	 * <p>For example: "руб." or "$"</p>
	 * <p>Depending on implementation this field might hold formatting string 
	 * for currency, e.g. "$ %.2f" or "%.2f р.".</p>
	 * @return Short currency name.
	 */
	String getShortCurrencyName();
	
	/**
	 * Game turn length in minutes.
	 * @return Turn length in minutes.
	 */
	@Nonnegative
	int getTurnLengthMinutes();
	
	/**
	 * Sum of money each player receives before initial allocation 
	 * of shares.
	 * @return Starting cash value for any player.
	 */
	@Nonnegative
	double getStartingCashValue();

}

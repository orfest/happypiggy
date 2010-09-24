package ru.nsu.ccfit.pm.econ.controller.scenario;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenarioProperties;

/**
 * @author orfest
 *
 */
public class ScenarioProperties implements IUScenarioProperties {

	private String author;
	private int turnLengthMinutes;
	private double startingCashValue;
	private String shortCurrencyName;
	private String name;
	private String description;
	private String fullCurrencyName;
	
	/**
	 * 
	 */
	public ScenarioProperties() {
		// TODO Auto-generated constructor stub
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setTurnLengthMinutes(int turnLengthMinutes) {
		this.turnLengthMinutes = turnLengthMinutes;
	}

	public void setStartingCashValue(double startingCashValue) {
		this.startingCashValue = startingCashValue;
	}

	public void setShortCurrencyName(String shortCurrencyName) {
		this.shortCurrencyName = shortCurrencyName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFullCurrencyName(String fullCurrencyName) {
		this.fullCurrencyName = fullCurrencyName;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getFullCurrencyName() {
		return fullCurrencyName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getShortCurrencyName() {
		return shortCurrencyName;
	}

	@Override
	public double getStartingCashValue() {
		return startingCashValue;
	}

	@Override
	public int getTurnLengthMinutes() {
		return turnLengthMinutes;
	}

}

package ru.nsu.ccfit.pm.econ.engine.data;

import java.util.ArrayList;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;

/**
 * @author pupatenko
 * 
 */
public class ScenarioEngine implements IUScenario {
	private ArrayList<CompanyEngine> companies;
	private String author;
	private String description;
	private String fullCurrencyName;
	private String name;
	private String shortCurrencyName;
	private double startingCashValue;
	private int turnLengthMinutes;

	public ScenarioEngine(ArrayList<CompanyEngine> companies, String author,
			String description, String fullCurrencyName, String name,
			String shortCurrencyName, double startingCashValue,
			int turnLengthMinutes) {
		this.companies = companies;
		this.author = author;
		this.description = description;
		this.fullCurrencyName = fullCurrencyName;
		this.name = name;
		this.shortCurrencyName = shortCurrencyName;
		this.startingCashValue = startingCashValue;
		this.turnLengthMinutes = turnLengthMinutes;
	}

	public ScenarioEngine(IUScenario toCopy) {
		companies = new ArrayList<CompanyEngine>(toCopy.getCompanies().size());
		for (IUCompany c : toCopy.getCompanies()) {
			companies.add(new CompanyEngine(c));
		}
		author = toCopy.getAuthor();
		description = toCopy.getDescription();
		this.fullCurrencyName = toCopy.getFullCurrencyName();
		this.name = toCopy.getName();
		this.shortCurrencyName = toCopy.getShortCurrencyName();
		this.startingCashValue = toCopy.getStartingCashValue();
		this.turnLengthMinutes = toCopy.getTurnLengthMinutes();
	}

	@Override
	public ArrayList<CompanyEngine> getCompanies() {
		return companies;
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

	public void setCompanies(ArrayList<CompanyEngine> companies) {
		this.companies = companies;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFullCurrencyName(String fullCurrencyName) {
		this.fullCurrencyName = fullCurrencyName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShortCurrencyName(String shortCurrencyName) {
		this.shortCurrencyName = shortCurrencyName;
	}

	public void setStartingCashValue(double startingCashValue) {
		this.startingCashValue = startingCashValue;
	}

	public void setTurnLengthMinutes(int turnLengthMinutes) {
		this.turnLengthMinutes = turnLengthMinutes;
	}

}

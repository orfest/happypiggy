package ru.nsu.ccfit.pm.econ.net.controller.scenario;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenarioProperties;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.ScenarioPropertiesProto;

@ProtoClass(value = ScenarioPropertiesProto.class)
public class ScenarioProperties implements IUScenarioProperties {

	@SerializeThis
	private String author = "";
	@SerializeThis
	private String description = "";
	@SerializeThis
	private String fullCurrencyName = "";
	@SerializeThis
	private String name = "";
	@SerializeThis
	private String shortCurrencyName = "";
	@SerializeThis
	private double startingCashValue = Double.MAX_VALUE;
	@SerializeThis
	private int turnLengthMinutes = Integer.MAX_VALUE;

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

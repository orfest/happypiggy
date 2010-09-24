package ru.nsu.ccfit.pm.econ.controller.player.data;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenarioProperties;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameProperties;

public class GameProperties implements IUGameProperties {
	
	private String gameSessionName;
	private IUScenarioProperties scenarioProperties;
	
	public GameProperties() {
	}
	
	public GameProperties(IUScenarioProperties scenario) {
		scenarioProperties = scenario;
		gameSessionName = scenario.getName();
	}

	@Override
	public String getGameSessionName() {
		return gameSessionName;
	}

	@Override
	public IUScenarioProperties getScenarioProperties() {
		return scenarioProperties;
	}
	
	public void setGameSessionName(String gameSessionName) {
		this.gameSessionName = gameSessionName;
	}

	public void setScenarioProperties(IUScenarioProperties scenarioProperties) {
		this.scenarioProperties = scenarioProperties;
	}

}

package ru.nsu.ccfit.pm.econ.engine.data;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenarioProperties;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameProperties;

/**
 * IUGameProperties interface implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUGameProperties
 */
public class GamePropertiesEngine implements IUGameProperties {

	private String gameSessionName;
	private IUScenarioProperties scenarioProperties;

	public GamePropertiesEngine(String gameSessionName,
			IUScenarioProperties scenarioProperties) {
		this.gameSessionName = gameSessionName;
		this.scenarioProperties = scenarioProperties;
	}

	public GamePropertiesEngine(IUGameProperties toCopy) {
		gameSessionName = toCopy.getGameSessionName();
		scenarioProperties = toCopy.getScenarioProperties();
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

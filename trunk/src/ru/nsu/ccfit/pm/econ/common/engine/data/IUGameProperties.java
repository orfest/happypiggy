package ru.nsu.ccfit.pm.econ.common.engine.data;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenarioProperties;

/**
 * Unmodifiable interface which provides various game properties.
 * @author dragonfly
 */
public interface IUGameProperties {
	
	/**
	 * Game session name. This value may be used to distinguish multiple active games 
	 * (e.g. when player chooses server to connect to). The value may be same as one  
	 * returned by {@link IUScenarioProperties#getName()}. 
	 * @return Short game name.
	 */
	String getGameSessionName();
	
	/**
	 * Game scenario properties.
	 * @return Game scenario properties.
	 */
	IUScenarioProperties getScenarioProperties();

}

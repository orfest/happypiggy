package ru.nsu.ccfit.pm.econ.engine.events;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * Internal engine event. Used to add new scenario.
 * 
 * @author pupatenko
 * 
 */
public class NewScenarioEventEngine extends GameEventEngine implements
		IUGameEvent {

	private IUScenario scenario;

	public IUScenario getScenario() {
		return scenario;
	}

	public NewScenarioEventEngine(IUScenario scenario) {
		super();
		this.scenario = scenario;
	}

}

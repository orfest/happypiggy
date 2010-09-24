/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.IScenarioPort;
import ru.nsu.ccfit.pm.econ.engine.events.NewScenarioEventEngine;

import com.google.inject.Inject;

/**
 * ScenarioPort implementation for engine
 * 
 * @author pupatenko
 * 
 */
public class ScenarioPortEngine implements IScenarioPort {

	private IEngineDispatcher dispatcher;

	@Inject
	public ScenarioPortEngine(IEngineDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	/**
	 * Note: this method may block!
	 */
	@Override
	public IUScenario getScenario() {
		IUScenario sc = null;
		try {
			sc = dispatcher.getWorker().AddGetScenarioTask();
		} catch (InterruptedException e) {
		}
		return sc;
	}

	@Override
	public boolean setScenario(IUScenario scenario) {
		dispatcher.getWorker().AddNewTask(
				new EventSourcePair(EventSource.Engine,
						new NewScenarioEventEngine(scenario)));
		return true;
	}

}

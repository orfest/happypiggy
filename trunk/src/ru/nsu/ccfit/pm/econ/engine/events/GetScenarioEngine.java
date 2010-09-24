package ru.nsu.ccfit.pm.econ.engine.events;

import java.util.concurrent.LinkedBlockingQueue;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * @author pupatenko
 * 
 */
public class GetScenarioEngine extends GameEventEngine implements IUGameEvent {

	private LinkedBlockingQueue<IUScenario> scenario;

	public GetScenarioEngine() {
		super();
		scenario = new LinkedBlockingQueue<IUScenario>();
	}

	public IUScenario waitForScenario() throws InterruptedException {
		return scenario.take();
	}

	public void addScenario(IUScenario scenario) throws InterruptedException {
		this.scenario.put(scenario);
	}

}

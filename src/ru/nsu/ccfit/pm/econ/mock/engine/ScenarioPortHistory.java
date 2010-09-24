/**
 * 
 */
package ru.nsu.ccfit.pm.econ.mock.engine;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.IScenarioPort;

/**
 * @author orfest
 *
 */
public class ScenarioPortHistory implements IScenarioPort {

	private IUScenario scenario;

	/**
	 * 
	 */
	public ScenarioPortHistory() {
		// TODO Auto-generated constructor stub
	}

	public IUScenario getScenario() {
		return scenario;
	}

	public boolean setScenario(IUScenario scenario) {
		this.scenario = scenario;
		return true;
	}

}

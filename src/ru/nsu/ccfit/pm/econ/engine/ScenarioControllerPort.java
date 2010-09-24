package ru.nsu.ccfit.pm.econ.engine;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.modules.names.ToScenarioController;

import com.google.inject.Inject;

/**
 * @see BaseHandlerPort
 * @author dragonfly
 */
public class ScenarioControllerPort extends BaseHandlerPort {
	
	public ScenarioControllerPort() {
		sourceType = EventSource.ScenarioController;
	}
	
	@Inject
	public void setOutgoingHandler(@ToScenarioController IGameEventHandler outgoingHandler) {
		this.outgoingHandler = outgoingHandler;
	}

}

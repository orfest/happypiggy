package ru.nsu.ccfit.pm.econ.engine;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.modules.names.ToPlayerController;

import com.google.inject.Inject;

/**
 * @see BaseHandlerPort
 * @author dragonfly
 */
public class PlayerControllerPort extends BaseHandlerPort {
	
	public PlayerControllerPort() {
		sourceType = EventSource.PlayerController;
	}
	
	@Inject
	public void setOutgoingHandler(@ToPlayerController IGameEventHandler outgoingHandler) {
		this.outgoingHandler = outgoingHandler;
	}

}

package ru.nsu.ccfit.pm.econ.engine;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.modules.names.ToNetworking;

import com.google.inject.Inject;

/**
 * @see BaseHandlerPort
 * @author dragonfly
 */
public class NetworkingPort extends BaseHandlerPort {
	
	public NetworkingPort() {
		sourceType = EventSource.Networking;
	}
	
	@Inject
	public void setOutgoingHandler(@ToNetworking IGameEventHandler outgoingHandler) {
		this.outgoingHandler = outgoingHandler;
	}

}

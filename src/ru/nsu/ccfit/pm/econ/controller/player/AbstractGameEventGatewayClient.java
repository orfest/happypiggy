package ru.nsu.ccfit.pm.econ.controller.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventConsumer;
import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventGateway;
import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventGatewayClient;

/**
 * Base class for most of game event consumers.
 * @see IGameEventConsumer
 * 
 * @author dragonfly
 */
abstract class AbstractGameEventGatewayClient implements IGameEventGatewayClient {
	
	static final Logger logger = LoggerFactory.getLogger(AbstractGameEventGatewayClient.class);
	
	/*
	 * Injectables.
	 */
	protected IGameEventGateway gateway;

	@Inject
	@Override
	public void setGameEventGateway(IGameEventGateway gateway) {
		if (this.gateway != null) {
			logger.warn("redefining gateway");
			if (this instanceof IGameEventConsumer) 
				this.gateway.removeGameEventConsumer((IGameEventConsumer)this);
		}
		
		this.gateway = gateway;
		
		if (this instanceof IGameEventConsumer) 
			this.gateway.addGameEventConsumer((IGameEventConsumer)this);
	}

}

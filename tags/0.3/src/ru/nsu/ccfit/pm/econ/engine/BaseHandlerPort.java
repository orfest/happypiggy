package ru.nsu.ccfit.pm.econ.engine;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * Base abstract class to support single incoming/outgoing event handler.
 * @author dragonfly
 */
public abstract class BaseHandlerPort implements IGameEventHandler {
	
	protected IGameEventHandler outgoingHandler;
	private IEngineDispatcher engineDispatcher;
	protected EventSource sourceType = null;

	@Override
	public void handleEvent(IUGameEvent event) {
		engineDispatcher.handleEvent(new EventSourcePair(sourceType, event));
	}
	
	public void sendEvent(IUGameEvent event) {
		outgoingHandler.handleEvent(event);
	}

	public abstract void setOutgoingHandler(IGameEventHandler outgoingHandler);

	@Inject
	public void setEngineDispatcher(IEngineDispatcher engineDispatcher) {
		this.engineDispatcher = engineDispatcher;
	}

}

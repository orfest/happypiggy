/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.modules.names.ToNetworking;
import ru.nsu.ccfit.pm.econ.modules.names.ToPlayerController;
import ru.nsu.ccfit.pm.econ.modules.names.ToScenarioController;

import com.google.inject.Inject;

/**
 * Internal class that helps engine to send messages (events)
 * eventHandlerNetworking, eventHandlerPlayerController and
 * eventHandlerScenarioController are injected
 * 
 * @author pupatenko
 * 
 */
public class AnswerSender implements IAnswerSender {
	private BaseHandlerPort eventHandlerNetworking;
	private BaseHandlerPort eventHandlerPlayerController;
	private BaseHandlerPort eventHandlerScenarioController;

	public void Send(IUGameEvent event, EventSource where) {
		switch (where) {
		case Networking:
			eventHandlerNetworking.sendEvent(event);
			return;
		case PlayerController:
			eventHandlerPlayerController.sendEvent(event);
			return;
		case ScenarioController:
			eventHandlerScenarioController.sendEvent(event);
			return;
		default:
			break;
		}
	}

	@Override
	@Inject
	public void setEventHandlerNetworking(@ToNetworking BaseHandlerPort handler) {
		eventHandlerNetworking = handler;
	}

	@Override
	@Inject
	public void setEventHandlerPlayerController(@ToPlayerController BaseHandlerPort handler) {
		eventHandlerPlayerController = handler;
	}

	@Override
	@Inject
	public void setEventHandlerScenarioController(@ToScenarioController BaseHandlerPort handler) {
		eventHandlerScenarioController = handler;
	}
}

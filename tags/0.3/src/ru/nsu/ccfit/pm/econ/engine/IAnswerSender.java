/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * Answer sender interface. Implementations is used to send game events from
 * engine to other components.
 * 
 * @author pupatenko
 * 
 */
public interface IAnswerSender {
	public void setEventHandlerNetworking(BaseHandlerPort handler);

	public void setEventHandlerPlayerController(BaseHandlerPort handler);

	public void setEventHandlerScenarioController(BaseHandlerPort handler);

	public void Send(IUGameEvent event, EventSource where);
}

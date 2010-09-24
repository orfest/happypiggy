package ru.nsu.ccfit.pm.econ.common.controller.player;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * Interface for game event consumers internal to PlayerController.
 * 
 * @author dragonfly
 */
public interface IGameEventConsumer {
	
	/**
	 * Processes arrived event.
	 * @param event Event to process.
	 * @return <tt>true</tt> if this consumer has successfully processed
	 * 			the event, <tt>false</tt> if this consumer is not able 
	 * 			to process this type of event.
	 */
	boolean processEvent(IUGameEvent event);

}

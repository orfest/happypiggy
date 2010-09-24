package ru.nsu.ccfit.pm.econ.common.controller.player;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * PlayerController game event gateway interface. 
 * <p>Allows to send game events and dispatch incoming ones.</p>
 * 
 * @author dragonfly
 */
public interface IGameEventGateway {
	
	/**
	 * Adds game event consumer.
	 * @param consumer Consumer to add.
	 */
	void addGameEventConsumer(IGameEventConsumer consumer);
	
	/**
	 * Removes previously added game event consumer.
	 * @param consumer Consumer to remove.
	 */
	void removeGameEventConsumer(IGameEventConsumer consumer);
	
	/**
	 * Sends event from PlayerController to underlying component, 
	 * like Networking or Engine.
	 * @param event Event object to send.
	 */
	void sendEvent(IUGameEvent event);

}

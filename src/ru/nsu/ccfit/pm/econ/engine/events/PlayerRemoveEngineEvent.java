/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * Internal engine event for player removal
 * 
 * @author pupatenko
 * 
 */
public class PlayerRemoveEngineEvent extends GameEventEngine implements
		IUGameEvent {

	private long id;

	public long getId() {
		return id;
	}

	public PlayerRemoveEngineEvent(long id) {
		super();
		this.id = id;
	}
}

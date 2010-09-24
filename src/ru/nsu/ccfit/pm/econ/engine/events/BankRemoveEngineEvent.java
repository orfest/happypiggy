/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * Internal engine event, used when bank is removed
 * 
 * @author pupatenko
 * 
 */
public class BankRemoveEngineEvent extends GameEventEngine implements
		IUGameEvent {

	private long id;

	public BankRemoveEngineEvent(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}
}

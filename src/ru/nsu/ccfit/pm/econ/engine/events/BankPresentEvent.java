package ru.nsu.ccfit.pm.econ.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * Internal engine event, used when bank presence changed.
 * 
 * @author pupatenko
 * 
 */
public class BankPresentEvent extends GameEventEngine implements IUGameEvent {

	private long id;
	private boolean present;

	public BankPresentEvent(long id, boolean present) {
		super();
		this.id = id;
		this.present = present;
	}

	public long getId() {
		return id;
	}

	public boolean isPresent() {
		return present;
	}
}

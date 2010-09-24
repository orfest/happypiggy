package ru.nsu.ccfit.pm.econ.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.engine.roles.StudentEngine;

/**
 * Internal engine event. Used when new player is added.
 * 
 * @author pupatenko
 * 
 */
public class NewPlayerEngineEvent extends GameEventEngine implements
		IUGameEvent {

	private StudentEngine pl;

	public NewPlayerEngineEvent(StudentEngine pl) {
		super();
		this.pl = pl;
	}

	public StudentEngine getPl() {
		return pl;
	}
}

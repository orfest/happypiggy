package ru.nsu.ccfit.pm.econ.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * Internal engine event that is send when turn is ended
 * 
 * @author pupatenko
 * 
 */
public class TurnEndEngineLocalEvent extends GameEventEngine implements
		IUGameEvent {
	private int num;

	public TurnEndEngineLocalEvent(int num) {
		super();
		this.num = num;
	}

	public int getNum() {
		return num;
	}

	public TurnEndEngineLocalEvent() {
		super();
		num = 0;
	}
}

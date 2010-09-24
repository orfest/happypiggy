package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnStartEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;

/**
 * @author orfest
 * 
 */
public class TurnStartEvent extends GameEvent implements IUTurnStartEvent {

	@SerializeThis
	private int newTurnNumber = Integer.MAX_VALUE;

	public TurnStartEvent() {
	}

	public int getNewTurnNumber() {
		return newTurnNumber;
	}

	public void setNewTurnNumber(int newTurnNumber) {
		this.newTurnNumber = newTurnNumber;
	}

}

package ru.nsu.ccfit.pm.econ.engine.events;

import java.util.ArrayList;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnStartEvent;
import ru.nsu.ccfit.pm.econ.engine.data.GameTimeEngine;

/**
 * Engine representation for {@link IUTurnStartEvent}
 * 
 * @author pupatenko
 * 
 */
public class TurnStartEventEngine extends GameEventEngine implements
		IUTurnStartEvent {

	private int newTurnNumber;

	public TurnStartEventEngine(int newTurnNumber, GameTimeEngine eventTime,
			ArrayList<Long> recieverIds, long senderId, boolean broadcast) {
		super(eventTime, recieverIds, senderId, broadcast);
		this.newTurnNumber = newTurnNumber;
	}

	public TurnStartEventEngine(TurnStartEventEngine toCopy) {
		super(toCopy.getEventTime(), toCopy.getReceiverIds(), toCopy
				.getSenderId(), toCopy.broadcast);
		newTurnNumber = toCopy.newTurnNumber;
	}

	@Override
	public int getNewTurnNumber() {
		return newTurnNumber;
	}
}

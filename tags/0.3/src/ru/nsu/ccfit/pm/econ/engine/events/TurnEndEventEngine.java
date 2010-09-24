/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent;

/**
 * @author pupatenko
 * 
 */
public class TurnEndEventEngine extends GameEventEngine implements
		IUTurnEndEvent {

	private ArrayList<IUPlayerRatingValue> ratingList;

	public TurnEndEventEngine(IUGameTime eventTime,
			Collection<Long> recieverIds, long senderId,
			List<IUPlayerRatingValue> ratingList, boolean broadcast) {
		super(eventTime, recieverIds, senderId, broadcast);
		this.ratingList = new ArrayList<IUPlayerRatingValue>(ratingList);
	}

	public TurnEndEventEngine(TurnEndEventEngine oth) {
		super(oth.getEventTime(), oth.getReceiverIds(), oth.getSenderId(), oth.isBroadcast());
		ratingList = new ArrayList<IUPlayerRatingValue>(oth.getRatingList());
	}

	@Override
	public ArrayList<IUPlayerRatingValue> getRatingList() {
		return ratingList;
	}

}

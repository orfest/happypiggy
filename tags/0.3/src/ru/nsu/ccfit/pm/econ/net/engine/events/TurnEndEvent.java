package ru.nsu.ccfit.pm.econ.net.engine.events;

import java.util.LinkedList;
import java.util.List;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.PlayerRatingValue;


/**
 * @author orfest
 * 
 */
public class TurnEndEvent extends GameEvent implements IUTurnEndEvent {

	@SerializeThis
	@BewareCollectionOf(value = PlayerRatingValue.class)
	private List<IUPlayerRatingValue> ratingList = new LinkedList<IUPlayerRatingValue>();

	public TurnEndEvent() {
	}

	public List<IUPlayerRatingValue> getRatingList() {
		return ratingList;
	}

	public void setRatingList(List<IUPlayerRatingValue> ratingList) {
		this.ratingList = ratingList;
	}

}


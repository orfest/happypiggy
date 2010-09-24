package ru.nsu.ccfit.pm.econ.controller.player.events;

import java.util.List;

import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent;

public class TurnEndEvent extends GameEvent implements IUTurnEndEvent {
	
	public List<IUPlayerRatingValue> ratingList = null;
	
	public TurnEndEvent(IULocalState local) {
		super(local);
		ratingList = null;
	}

	@Override
	public List<IUPlayerRatingValue> getRatingList() {
		return ratingList;
	}

}

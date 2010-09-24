package ru.nsu.ccfit.pm.econ.controller.player.events;

import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnStartEvent;

public class TurnStartEvent extends GameEvent implements IUTurnStartEvent {
	
	public int newTurnNumber = 0;
	
	public TurnStartEvent(IULocalState local) {
		super(local);
		newTurnNumber = local.getTurnNumber() + 1;
	}

	@Override
	public int getNewTurnNumber() {
		return newTurnNumber;
	}

}

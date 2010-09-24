package ru.nsu.ccfit.pm.econ.controller.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventConsumer;
import ru.nsu.ccfit.pm.econ.common.controller.player.ITurnControl;
import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.controller.player.events.TurnEndEvent;
import ru.nsu.ccfit.pm.econ.controller.player.events.TurnStartEvent;

public class TurnControl extends AbstractGameEventGatewayClient implements
		ITurnControl, IGameEventConsumer {
	
	static final Logger logger = LoggerFactory.getLogger(TurnControl.class);
	
	/*
	 * Injectables.
	 */
	private IULocalState local;
	
	@Inject
	public void setLocal(IULocalState local) {
		this.local = local;
	}

	@Override
	public void finishTurn() {
		TurnEndEvent event = new TurnEndEvent(local);
		gateway.sendEvent(event);
	}

	@Override
	public int getTurnNumber() {
		return local.getTurnNumber();
	}

	@Override
	public boolean isTurnFinished() {
		return local.isTurnFinished();
	}

	@Override
	public void startTurn() {
		TurnStartEvent event = new TurnStartEvent(local);
		gateway.sendEvent(event);
	}

	@Override
	public boolean processEvent(IUGameEvent event) {
		return false;
	}

}

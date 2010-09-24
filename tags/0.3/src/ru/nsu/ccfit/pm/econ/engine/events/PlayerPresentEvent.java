package ru.nsu.ccfit.pm.econ.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * 'Player present' internal engine event
 * 
 * @author pupatenko
 * 
 */
public class PlayerPresentEvent extends GameEventEngine implements IUGameEvent {

	private long playerId;
	private boolean present;

	public PlayerPresentEvent(long playerId, boolean present) {
		super();
		this.playerId = playerId;
		this.present = present;
	}

	public long getPlayerId() {
		return playerId;
	}

	public boolean isPresent() {
		return present;
	}

}

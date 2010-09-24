/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent.IUPlayerRatingValue;

/**
 * {@link IUPlayerRatingValue} interface implementation for engine
 * 
 * @author pupatenko
 * 
 */
public class PlayerRatingValue implements IUPlayerRatingValue {

	private long playerId;
	private double ratingValue;

	public PlayerRatingValue(long playerId, double ratingValue) {
		this.playerId = playerId;
		this.ratingValue = ratingValue;
	}

	@Override
	public long getPlayerId() {
		return playerId;
	}

	@Override
	public double getRatingValue() {
		return ratingValue;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public void setRatingValue(double ratingValue) {
		this.ratingValue = ratingValue;
	}

}

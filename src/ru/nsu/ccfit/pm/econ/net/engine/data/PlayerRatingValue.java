/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent.IUPlayerRatingValue;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.PlayerRatingValueProto;

/**
 * @author orfest
 * 
 */
@ProtoClass(value = PlayerRatingValueProto.class)
public class PlayerRatingValue  implements IUPlayerRatingValue{

	@SerializeThis
	long playerId;
	@SerializeThis
	double ratingValue;

	/**
	 * 
	 */
	public PlayerRatingValue() {
	}

	@Override
	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	@Override
	public double getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(double ratingValue) {
		this.ratingValue = ratingValue;
	}
	
}

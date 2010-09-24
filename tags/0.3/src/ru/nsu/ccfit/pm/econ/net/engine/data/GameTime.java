package ru.nsu.ccfit.pm.econ.net.engine.data;

import java.util.Date;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.GameTimeProto;

/**
 * @author orfest
 *
 */
@ProtoClass(value=GameTimeProto.class)
public class GameTime implements IUGameTime {
	
	@SerializeThis
	private Date time = new Date();
	@SerializeThis
	private int turnNumber = Integer.MAX_VALUE;
	@SerializeThis(get="isTurnFinished")
	private boolean turnFinished = false;
	
	public GameTime() {
	}

	@Override
	public Date getTime() {
		return time;
	}

	@Override
	public int getTurnNumber() {
		return turnNumber;
	}

	@Override
	public boolean isTurnFinished() {
		return turnFinished;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public void setTurnFinished(boolean turnFinished) {
		this.turnFinished = turnFinished;
	}

}

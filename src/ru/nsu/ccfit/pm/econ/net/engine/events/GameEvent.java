package ru.nsu.ccfit.pm.econ.net.engine.events;

import java.util.Collection;
import java.util.LinkedList;

import com.google.gag.annotation.disclaimer.IAmAwesome;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.GameTime;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.GameEventProto;

/**
 * @author orfest
 * 
 */

// do not make it abstract :)
@IAmAwesome(value = "orfest")
@ProtoClass(value = GameEventProto.class)
public class GameEvent implements IUGameEvent {

	@SerializeThis
	private long senderId = Long.MAX_VALUE;
	@SerializeThis(get="isBroadcast")
	private boolean broadcast = false;
	@SerializeThis
	@BewareCollectionOf(value = Long.class)
	private Collection<Long> receiverIds = new LinkedList<Long>();
	@SerializeThis
	private IUGameTime eventTime = new GameTime();

	public GameEvent() {
	}

	public IUGameTime getEventTime() {
		return eventTime;
	}

	public Collection<Long> getReceiverIds() {
		return receiverIds;
	}

	public boolean isBroadcast() {
		return broadcast;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}

	public void setReceiverIds(Collection<Long> receiverIds) {
		this.receiverIds = receiverIds;
	}

	public void setEventTime(IUGameTime eventTime) {
		this.eventTime = eventTime;
	}

}

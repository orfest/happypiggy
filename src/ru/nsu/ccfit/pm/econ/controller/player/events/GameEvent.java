package ru.nsu.ccfit.pm.econ.controller.player.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

public class GameEvent implements IUGameEvent {
	
	public IUGameTime eventTime;
	public List<Long> receiverIds = new ArrayList<Long>();
	public long senderId;
	public boolean broadcast = false;
	
	public GameEvent(IULocalState local) {
		eventTime = local.getCurrentGameTime();
		broadcast = true;
		senderId = local.getMineId();
	}

	@Override
	public IUGameTime getEventTime() {
		return eventTime;
	}

	@Override
	public Collection<Long> getReceiverIds() {
		return receiverIds;
	}

	@Override
	public long getSenderId() {
		return senderId;
	}

	@Override
	public boolean isBroadcast() {
		return broadcast;
	}
	
	protected void setSingleReceiver(long receiverId) {
		broadcast = false;
		receiverIds.clear();
		receiverIds.add(receiverId);
	}
	
	protected void setReceiverIds(Collection<Long> receiverIds) {
		this.broadcast = false;
		this.receiverIds = new ArrayList<Long>(receiverIds);
	}

}

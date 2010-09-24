package ru.nsu.ccfit.pm.econ.net.engine.events;

import java.util.Collection;
import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUShareAllocationRequestEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.ShareAllocationRequest;

/**
 * @author orfest
 * 
 */
public class ShareAllocationRequestEvent extends GameEvent implements
		IUShareAllocationRequestEvent {

	@SerializeThis
	@BewareCollectionOf(value=ShareAllocationRequest.class)
	private Collection<IUShareAllocationRequest> requests = new LinkedList<IUShareAllocationRequest>();

	public ShareAllocationRequestEvent() {
	}

	public Collection<IUShareAllocationRequest> getRequests() {
		return requests;
	}

	public void setRequests(Collection<IUShareAllocationRequest> requests) {
		this.requests = requests;
	}

}

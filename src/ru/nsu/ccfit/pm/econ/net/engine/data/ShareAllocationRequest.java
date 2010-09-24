/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUShareAllocationRequestEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.ShareAllocationRequestProto;

/**
 * @author orfest
 * 
 */
@ProtoClass(value=ShareAllocationRequestProto.class)
public class ShareAllocationRequest implements
		IUShareAllocationRequestEvent.IUShareAllocationRequest {

	@SerializeThis
	private int requestedShareAmount = Integer.MAX_VALUE;
	@SerializeThis
	private long companyId = Long.MAX_VALUE;

	public long getCompanyId() {
		return companyId;
	}

	public int getRequestedShareAmount() {
		return requestedShareAmount;
	}

	public void setRequestedShareAmount(int requestedShareAmount) {
		this.requestedShareAmount = requestedShareAmount;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

}

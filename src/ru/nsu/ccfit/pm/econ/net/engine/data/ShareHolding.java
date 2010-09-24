package ru.nsu.ccfit.pm.econ.net.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.ShareHoldingProto;

/**
 * @author orfest
 *
 */
@ProtoClass(value=ShareHoldingProto.class)
public class ShareHolding implements IUShareHolding {
	@SerializeThis
	private long ownerId = Long.MAX_VALUE;
	@SerializeThis
	private long companyId = Long.MAX_VALUE;
	@SerializeThis
	private int amount = Integer.MAX_VALUE;

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public long getCompanyId() {
		return companyId;
	}

	@Override
	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}

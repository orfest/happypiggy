package ru.nsu.ccfit.pm.econ.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;

/**
 * IUShareHolding implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUShareHolding
 * 
 */
public class ShareHoldingEngine implements IUShareHolding {
	private int amount;
	private long companyId;
	private long ownerId;

	public ShareHoldingEngine(int amount, long companyId, long ownerId) {
		this.amount = amount;
		this.companyId = companyId;
		this.ownerId = ownerId;
	}

	public ShareHoldingEngine(IUShareHolding toCopy) {
		amount = toCopy.getAmount();
		companyId = toCopy.getCompanyId();
		ownerId = toCopy.getOwnerId();
	}

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

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
}

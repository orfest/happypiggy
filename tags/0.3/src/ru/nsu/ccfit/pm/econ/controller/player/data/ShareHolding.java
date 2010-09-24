package ru.nsu.ccfit.pm.econ.controller.player.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;

public class ShareHolding implements IUShareHolding {
	
	public int amount;
	public long companyId;
	public long ownerId;
	
	public ShareHolding(IUShareHolding h) {
		this.amount = h.getAmount();
		this.companyId = h.getCompanyId();
		this.ownerId = h.getOwnerId();
	}
	
	public ShareHolding(long companyId, long ownerId, int amount) {
		this.companyId = companyId;
		this.ownerId = ownerId;
		this.amount = amount;
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IUShareHolding) {
			IUShareHolding sh = (IUShareHolding)obj;
			return getAmount() == sh.getAmount() 
				&& getOwnerId() == sh.getOwnerId()
				&& getCompanyId() == sh.getCompanyId();
		}
		
		return super.equals(obj);
	}

}

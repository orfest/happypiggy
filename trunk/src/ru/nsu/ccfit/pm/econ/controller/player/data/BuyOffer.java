package ru.nsu.ccfit.pm.econ.controller.player.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;

public class BuyOffer implements IUBuyOffer {
	
	public long sellerId;
	public ShareHolding shareHolding;
	public double suggestedValue;
	
	public BuyOffer(IUBuyOffer o) {
		this.sellerId = o.getSellerId();
		this.shareHolding = new ShareHolding(o.getShareHolding());
		this.suggestedValue = o.getSuggestedValue();
	}

	@Override
	public long getSellerId() {
		return sellerId;
	}

	@Override
	public IUShareHolding getShareHolding() {
		return shareHolding;
	}

	@Override
	public double getSuggestedValue() {
		return suggestedValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IUBuyOffer) {
			IUBuyOffer o = (IUBuyOffer)obj;
			return getSellerId() == o.getSellerId()
				&& getShareHolding().equals(o.getShareHolding());
			// suggestedValue is not considered to by identifying attribute 
		}
		
		return super.equals(obj);
	}

	public void setSuggestedValue(double suggestedValue) {
		this.suggestedValue = suggestedValue;
	}

}

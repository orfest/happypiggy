/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;

/**
 * Implementation of IUBuyOffer interface for engine
 * 
 * @author pupatenko
 * 
 * @see IUBuyOffer
 */
public class BuyOfferEngine implements IUBuyOffer {
	private long sellerId;
	private ShareHoldingEngine shareHolding;
	private double suggestedValue;

	public BuyOfferEngine(long sellerId, IUShareHolding shareHolding,
			double suggestedValue) {
		this.sellerId = sellerId;
		this.shareHolding = new ShareHoldingEngine(shareHolding);
		this.suggestedValue = suggestedValue;
	}

	public BuyOfferEngine(IUBuyOffer toCopy) {
		this.sellerId = toCopy.getSellerId();
		this.shareHolding = new ShareHoldingEngine(toCopy.getShareHolding());
		this.suggestedValue = toCopy.getSuggestedValue();
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

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public void setShareHolding(ShareHoldingEngine shareHolding) {
		this.shareHolding = shareHolding;
	}

	public void setSuggestedValue(double suggestedValue) {
		this.suggestedValue = suggestedValue;
	}

}

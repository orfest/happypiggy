package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBuyRequestEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;

/**
 * @author orfest
 * 
 */
public class BuyRequestEvent extends GameEvent implements IUBuyRequestEvent {

	@SerializeThis
	private long buyerId = Long.MAX_VALUE;
	@SerializeThis
	private IUBuyOffer offerOfInterest = null;

	public BuyRequestEvent() {
	}

	public IUBuyOffer getOfferOfInterest() {
		return offerOfInterest;
	}

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
	
	public void setOfferOfInterest(IUBuyOffer offerOfInterest) {
		this.offerOfInterest = offerOfInterest;
	}
	
}

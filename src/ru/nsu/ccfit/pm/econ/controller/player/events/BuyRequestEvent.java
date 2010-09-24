package ru.nsu.ccfit.pm.econ.controller.player.events;

import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBuyRequestEvent;
import ru.nsu.ccfit.pm.econ.controller.player.data.BuyOffer;

public class BuyRequestEvent extends GameEvent implements IUBuyRequestEvent {
	
	public long buyerId;
	public BuyOffer offerOfInterest;

	public BuyRequestEvent(IUBuyOffer offerOfInterest, IULocalState local) {
		super(local);
		this.buyerId = local.getMineId();
		this.offerOfInterest = new BuyOffer(offerOfInterest);
	}

	@Override
	public long getBuyerId() {
		return buyerId;
	}

	@Override
	public IUBuyOffer getOfferOfInterest() {
		return offerOfInterest;
	}

}

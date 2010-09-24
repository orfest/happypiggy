package ru.nsu.ccfit.pm.econ.common.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;

/**
 * Unmodifiable interface for passing buy requests (as responses
 * to existing buy offers).
 * <p>If buy request matches some valid buy offer, a transaction
 * would happen.</p>
 * @author dragonfly
 */
public interface IUBuyRequestEvent extends IUGameEvent {
	
	/**
	 * Buy offer of interest to requesting player.
	 * @return Buy offer of interest.
	 */
	IUBuyOffer getOfferOfInterest();
	
	/**
	 * Buyer.
	 * @return Buyer player identifier.
	 */
	long getBuyerId();

}

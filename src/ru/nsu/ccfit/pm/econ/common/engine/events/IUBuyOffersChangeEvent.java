package ru.nsu.ccfit.pm.econ.common.engine.events;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;

/**
 * Unmodifiable interface for passing buy offer changes. 
 * <p>Buy offer changes are <i>always</i> passed in groups (by
 * company and seller), even if only one buy offer changed.</p>
 * <p>Since buy offers may change due to some transaction, 
 * stock index changes (see {@link #getNewStockIndex()}) are also 
 * tied to this event.</p>
 * @author dragonfly
 */
public interface IUBuyOffersChangeEvent extends IUGameEvent {
	
	/**
	 * Group of buy offers for shares of one company from one seller.
	 * @return Collection of buy offers.
	 */
	Collection<? extends IUBuyOffer> getUnmodifiableBuyOffers();
	
	/**
	 * Company the buy offer is related to. This value should match
	 * {@link IUShareHolding#getCompanyId()} from each buy offer.
	 * @return Company identifier.
	 */
	long getCompanyId();
	
	/**
	 * Player that offers the share holdings. This value should match
	 * {@link IUBuyOffer#getSellerId()} for each buy offer.
	 * @return Seller player identifier.
	 */
	long getSellerId();
	
	/**
	 * Stock index value just after buy offer change. This value may be 
	 * changed due to some transaction that resulted in firing this event. 
	 * @return New stock index value if this event originates from server,
	 * 			and <tt>0</tt> if this event originates from client.
	 */
	double getNewStockIndex();
	
	/**
	 * Share market value just after buy offer change. This value is 
	 * usually equal to the price of one share in the latest transaction. 
	 * Shares in question were emitted by company identified by
	 * {@link #getCompanyId()}. 
	 * @return New market value of a single share.
	 */
	double getNewShareMarketValue();

}

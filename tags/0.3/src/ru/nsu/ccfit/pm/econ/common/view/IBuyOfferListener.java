package ru.nsu.ccfit.pm.econ.common.view;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;

/**
 * Listener interface for buy offer changes.
 * 
 * @author dragonfly
 */
public interface IBuyOfferListener {
	
	void onBuyOffersUpdated(long companyId, Collection<? extends IUBuyOffer> updatedOffers);
	
	void onMineBuyOffersChanged(Collection<? extends IUBuyOffer> mineOffers);

}

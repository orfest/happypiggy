package ru.nsu.ccfit.pm.econ.common.controller.player;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.view.IBuyOfferListener;

/**
 * Shares buy and sell interface. Allows to make buy offers and 
 * buy shares based on others buy offers.
 * @see IBuyOfferListener
 * 
 * @author dragonfly
 */
public interface IBuySell {
	
	boolean addBuyOffer(IUBuyOffer offer);
	
	boolean cancelBuyOffer(IUBuyOffer offer);
	
	void buy(IUBuyOffer offerToBuy);
	
	Collection<? extends IUBuyOffer> getMineBuyOffers();
	
	Collection<? extends IUBuyOffer> getBuyOffersForCompany(long companyId);

}

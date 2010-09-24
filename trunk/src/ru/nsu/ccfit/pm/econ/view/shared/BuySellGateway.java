package ru.nsu.ccfit.pm.econ.view.shared;

import java.util.Collection;

import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IBuySell;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.view.IBuyOfferListener;

/**
 * Gateway for views to interact with <tt>PlayerController</tt>
 * buy and sell shares features.
 * @see IBuySell
 * @see IBuyOfferListener
 * 
 * @author dragonfly
 */
public class BuySellGateway implements IBuySell, IBuyOfferListener {
	
	static final Logger logger = LoggerFactory.getLogger(BuySellGateway.class);
	
	protected BuyOfferListenerList listeners = new BuyOfferListenerList();
	private IBuySell controller;

	@Override
	public boolean addBuyOffer(IUBuyOffer offer) {
		return controller.addBuyOffer(offer);
	}

	@Override
	public void buy(IUBuyOffer offerToBuy) {
		controller.buy(offerToBuy);
	}

	@Override
	public boolean cancelBuyOffer(IUBuyOffer offer) {
		return controller.cancelBuyOffer(offer);
	}

	@Override
	public Collection<? extends IUBuyOffer> getBuyOffersForCompany(long companyId) {
		return controller.getBuyOffersForCompany(companyId);
	}

	@Override
	public Collection<? extends IUBuyOffer> getMineBuyOffers() {
		return controller.getMineBuyOffers();
	}

	@Override
	public void onBuyOffersUpdated(final long companyId, final Collection<? extends IUBuyOffer> updatedOffers) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onBuyOffersUpdated(companyId, updatedOffers);
			}
		});
	}

	@Override
	public void onMineBuyOffersChanged(final Collection<? extends IUBuyOffer> mineOffers) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onMineBuyOffersChanged(mineOffers);
			}
		});
	}
	
	@Inject
	public ListenerList<IBuyOfferListener> getBuyOfferListeners() {
		return listeners;
	}

	@Inject
	public void setBuySellController(IBuySell controller) {
		this.controller = controller;
	}
	
	protected static class BuyOfferListenerList
		extends ListenerList<IBuyOfferListener> implements IBuyOfferListener {

		@Override
		public void onBuyOffersUpdated(long companyId, Collection<? extends IUBuyOffer> updatedOffers) {
			for (IBuyOfferListener listener : this)
				listener.onBuyOffersUpdated(companyId, updatedOffers);
		}

		@Override
		public void onMineBuyOffersChanged(Collection<? extends IUBuyOffer> mineOffers) {
			for (IBuyOfferListener listener : this)
				listener.onMineBuyOffersChanged(mineOffers);
		}
		
	}

}

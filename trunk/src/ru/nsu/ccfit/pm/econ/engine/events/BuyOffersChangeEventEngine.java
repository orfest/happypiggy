/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBuyOffersChangeEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.engine.data.BuyOfferEngine;

/**
 * {@link IUBuyOffersChangeEvent} interface implementation for engine
 * 
 * @author pupatenko
 * 
 */
public class BuyOffersChangeEventEngine extends GameEventEngine implements
		IUBuyOffersChangeEvent {

	private long companyId;
	private double newShareMarketValue;
	private double newStockIndex;
	private long sellerId;
	private ArrayList<BuyOfferEngine> buyOffers;

	public BuyOffersChangeEventEngine(IUBuyOffersChangeEvent toCopy) {
		super((IUGameEvent) toCopy);
		companyId = toCopy.getCompanyId();
		newShareMarketValue = toCopy.getNewShareMarketValue();
		newStockIndex = toCopy.getNewStockIndex();
		sellerId = toCopy.getSellerId();
		buyOffers = new ArrayList<BuyOfferEngine>(toCopy
				.getUnmodifiableBuyOffers().size());
		for (IUBuyOffer bo : toCopy.getUnmodifiableBuyOffers())
			buyOffers.add(new BuyOfferEngine(bo));
	}

	public BuyOffersChangeEventEngine(IUGameTime eventTime,
			Collection<Long> recieverIds, long senderId, long companyId, boolean broadcast,
			double newShareMarketValue, double newStockIndex, long sellerId,
			Collection<? extends IUBuyOffer> buyOffers) {
		super(eventTime, recieverIds, senderId, broadcast);
		this.companyId = companyId;
		this.newShareMarketValue = newShareMarketValue;
		this.newStockIndex = newStockIndex;
		this.sellerId = sellerId;
		this.buyOffers = new ArrayList<BuyOfferEngine>(buyOffers.size());
		for (IUBuyOffer bo : buyOffers)
			this.buyOffers.add(new BuyOfferEngine(bo));
	}

	@Override
	public long getCompanyId() {
		return companyId;
	}

	@Override
	public double getNewShareMarketValue() {
		return newShareMarketValue;
	}

	@Override
	public double getNewStockIndex() {
		return newStockIndex;
	}

	@Override
	public long getSellerId() {
		return sellerId;
	}

	@Override
	public Collection<BuyOfferEngine> getUnmodifiableBuyOffers() {
		return Collections.unmodifiableCollection(buyOffers);
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setNewShareMarketValue(double newShareMarketValue) {
		this.newShareMarketValue = newShareMarketValue;
	}

	public void setNewStockIndex(double newStockIndex) {
		this.newStockIndex = newStockIndex;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public ArrayList<BuyOfferEngine> getBuyOffers() {
		return buyOffers;
	}

	public void setBuyOffers(ArrayList<BuyOfferEngine> buyOffers) {
		this.buyOffers = buyOffers;
	}

}

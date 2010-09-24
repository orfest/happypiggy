package ru.nsu.ccfit.pm.econ.net.engine.events;

import java.util.Collection;
import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBuyOffersChangeEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.BuyOffer;

/**
 * @author orfest
 * 
 */
public class BuyOffersChangeEvent extends GameEvent implements
		IUBuyOffersChangeEvent {

	@SerializeThis
	@BewareCollectionOf(value=BuyOffer.class)
	private Collection<? extends IUBuyOffer> unmodifiableBuyOffers = new LinkedList<IUBuyOffer>();
	@SerializeThis
	private long companyId = Long.MAX_VALUE;
	@SerializeThis
	private long sellerId = Long.MAX_VALUE;
	@SerializeThis
	private double newStockIndex = Double.MAX_VALUE;
	@SerializeThis
	private double newShareMarketValue = Double.MAX_VALUE;

	public BuyOffersChangeEvent() {
	}

	public Collection<? extends IUBuyOffer> getUnmodifiableBuyOffers() {
		return unmodifiableBuyOffers;
	}

	public long getCompanyId() {
		return companyId;
	}

	public long getSellerId() {
		return sellerId;
	}

	public double getNewStockIndex() {
		return newStockIndex;
	}

	public double getNewShareMarketValue() {
		return newShareMarketValue;
	}

	public void setUnmodifiableBuyOffers(
			Collection<? extends IUBuyOffer> unmodifiableBuyOffers) {
		this.unmodifiableBuyOffers = unmodifiableBuyOffers;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public void setNewStockIndex(double newStockIndex) {
		this.newStockIndex = newStockIndex;
	}

	public void setNewShareMarketValue(double newShareMarketValue) {
		this.newShareMarketValue = newShareMarketValue;
	}

}

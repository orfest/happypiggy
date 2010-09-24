package ru.nsu.ccfit.pm.econ.controller.player.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBuyOffersChangeEvent;
import ru.nsu.ccfit.pm.econ.controller.player.data.BuyOffer;

public class BuyOffersChangeEvent extends GameEvent implements IUBuyOffersChangeEvent {
	
	public long companyId;
	public long sellerId;
	public List<BuyOffer> buyOffers;

	public BuyOffersChangeEvent(long companyId, Collection<BuyOffer> newOffers, IULocalState local) {
		
		super(local);
		this.companyId = companyId;
		this.sellerId = local.getMineId();
		this.buyOffers = new ArrayList<BuyOffer>(newOffers);
	}

	@Override
	public long getCompanyId() {
		return companyId;
	}

	@Override
	public double getNewShareMarketValue() {
		return 0;
	}

	@Override
	public double getNewStockIndex() {
		return 0;
	}

	@Override
	public long getSellerId() {
		return sellerId;
	}

	@Override
	public Collection<? extends IUBuyOffer> getUnmodifiableBuyOffers() {
		return Collections.unmodifiableCollection(buyOffers);
	}

}

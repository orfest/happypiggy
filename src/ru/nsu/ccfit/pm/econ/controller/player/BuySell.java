package ru.nsu.ccfit.pm.econ.controller.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IBuySell;
import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventConsumer;
import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTransaction;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBuyOffersChangeEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameSnapshotEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTransactionEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTransferSharesEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IULimitedBudgetPlayer;
import ru.nsu.ccfit.pm.econ.common.view.IBuyOfferListener;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerStatsUpdateListener;
import ru.nsu.ccfit.pm.econ.controller.player.data.BuyOffer;
import ru.nsu.ccfit.pm.econ.controller.player.events.BuyOffersChangeEvent;
import ru.nsu.ccfit.pm.econ.controller.player.events.BuyRequestEvent;
import ru.nsu.ccfit.pm.econ.controller.player.roles.Shareholder;
import ru.nsu.ccfit.pm.econ.controller.player.roles.Student;

import static com.google.common.base.Preconditions.checkNotNull;

public class BuySell extends AbstractGameEventGatewayClient implements
		IGameEventConsumer, IBuySell {
	
	static final Logger logger = LoggerFactory.getLogger(BuySell.class);
	
	private List<BuyOffer> mineOffers = new ArrayList<BuyOffer>();
	// keys are companyId, then ownerId
	private Map<Long, Map<Long, List<BuyOffer>>> buyOffers = new HashMap<Long, Map<Long, List<BuyOffer>>>();	
	
	/*
	 * Injectables.
	 */
	private IULocalState local;
	private IBuyOfferListener buyOfferListener;
	private IPlayerStatsUpdateListener playerStatsListener;
	
	@Inject
	public void setLocalState(IULocalState local) {
		this.local = local;
	}
	
	@Inject
	public void setBuyOfferListener(IBuyOfferListener listener) {
		this.buyOfferListener = listener;
	}
	
	@Inject
	public void setPlayerStatsListener(IPlayerStatsUpdateListener listener) {
		this.playerStatsListener = listener;
	}

	@Override
	public boolean processEvent(IUGameEvent event) {
		if (event instanceof IUGameSnapshotEvent) {
			processGameSnapshotEvent((IUGameSnapshotEvent)event);
		} else if (event instanceof IUBuyOffersChangeEvent) {
			processBuyOffersChangeEvent((IUBuyOffersChangeEvent)event);
		} else if (event instanceof IUTransactionEvent) {
			processTransactionEvent((IUTransactionEvent)event);
		} else if (event instanceof IUTransferSharesEvent) {
			processTransferSharesEvent((IUTransferSharesEvent)event);
		} else {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean addBuyOffer(IUBuyOffer offer) {
		long companyId = offer.getShareHolding().getCompanyId();
		long ownerId = offer.getSellerId();
		
		if (local.getPlayer() instanceof Shareholder) {
			Shareholder shareholder = (Shareholder)local.getPlayer();
			if (shareholder.getShareHoldingForCompanyId(companyId).getAmount() < 
					offer.getShareHolding().getAmount()) {
				logger.debug("Player doesn't have enough shares to make such offer");
				return false;
			}
		} else {
			logger.warn("Attempt to add buy offer from player that doesn't possess any shares.");
			return false;
		}
		
		Map<Long, List<BuyOffer>> companyOffers = buyOffers.get(companyId);
		if (companyOffers == null) {
			companyOffers = new HashMap<Long, List<BuyOffer>>();
			buyOffers.put(companyId, companyOffers);
		}
		
		List<BuyOffer> ownerOffers = companyOffers.get(ownerId);
		if (ownerOffers == null) {
			ownerOffers = new ArrayList<BuyOffer>();
			companyOffers.put(ownerId, ownerOffers);
		}
		
		BuyOffer internalOffer = findBuyOffer(offer, ownerOffers);
		if (internalOffer == null) {
			internalOffer = new BuyOffer(offer);
			ownerOffers.add(internalOffer);
			mineOffers.add(internalOffer);
		} else {
			internalOffer.setSuggestedValue(offer.getSuggestedValue());
			internalOffer = checkNotNull(findBuyOffer(offer, mineOffers));
			internalOffer.setSuggestedValue(offer.getSuggestedValue());
		}
		
		BuyOffersChangeEvent event = new BuyOffersChangeEvent(companyId, ownerOffers, local);
		gateway.sendEvent(event);
		buyOfferListener.onMineBuyOffersChanged(getMineBuyOffers());
		
		return true;
	}

	@Override
	public void buy(IUBuyOffer offerToBuy) {
		BuyRequestEvent event = new BuyRequestEvent(offerToBuy, local);
		gateway.sendEvent(event);
	}

	@Override
	public boolean cancelBuyOffer(IUBuyOffer offer) {
		if (!(local.getPlayer() instanceof Shareholder)) {
			logger.warn("Attempt to cancel buy offer from player that doesn't possess any shares.");
			return false;
		}
		
		long companyId = offer.getShareHolding().getCompanyId();
		Map<Long, List<BuyOffer>> companyOffers = buyOffers.get(companyId);
		if (companyOffers == null) {
			logger.warn("Attempt to cancel invalid offer: {}", offer);
			return false;
		}
		
		long ownerId = offer.getSellerId();
		List<BuyOffer> ownerOffers = companyOffers.get(ownerId);
		if (ownerOffers == null) {
			logger.warn("Attempt to cancel invalid offer: {}", offer);
			return false;
		}
		
		BuyOffer internalOffer = findBuyOffer(offer, ownerOffers);
		if (internalOffer == null) {
			logger.warn("Attempt to cancel invalid offer: {}", offer);
			return false;
		} else {
			removeBuyOffer(offer, ownerOffers);
			removeBuyOffer(offer, mineOffers);
		}
		
		BuyOffersChangeEvent event = new BuyOffersChangeEvent(companyId, ownerOffers, local);
		gateway.sendEvent(event);
		buyOfferListener.onMineBuyOffersChanged(getMineBuyOffers());
		
		return true;
	}

	@Override
	public Collection<? extends IUBuyOffer> getBuyOffersForCompany(long companyId) {
		Map<Long, List<BuyOffer>> companyOffers = buyOffers.get(companyId);
		if (companyOffers == null) {
			return Collections.emptyList();
		}
		
		List<BuyOffer> offers = new ArrayList<BuyOffer>();
		for (List<BuyOffer> ownerOffers : companyOffers.values()) {
			offers.addAll(ownerOffers);
		}
		
		return Collections.unmodifiableCollection(offers);
	}

	@Override
	public Collection<? extends IUBuyOffer> getMineBuyOffers() {
		return Collections.unmodifiableCollection(mineOffers);
	}
	
	private void processGameSnapshotEvent(IUGameSnapshotEvent event) {
		Collection<? extends IUBuyOffer> incOffers = event.getAllBuyOffers();
		assert incOffers != null && incOffers.size() == 0;
		// TODO do handle this event if mid-game snapshots are supported
		// also fire listener event
	}
	
	private void processBuyOffersChangeEvent(IUBuyOffersChangeEvent event) {
		Collection<? extends IUBuyOffer> incOffers = event.getUnmodifiableBuyOffers();
		long companyId = event.getCompanyId();
		long ownerId = event.getSellerId();
		
		Map<Long, List<BuyOffer>> companyOffers = buyOffers.get(companyId);
		if (companyOffers == null) {
			companyOffers = new HashMap<Long, List<BuyOffer>>();
			buyOffers.put(companyId, companyOffers);
		}
		
		List<BuyOffer> ownerOffers = companyOffers.get(ownerId);
		if (ownerOffers == null) {
			ownerOffers = new ArrayList<BuyOffer>();
			companyOffers.put(ownerId, ownerOffers);
		}
		
		ownerOffers.clear();
		for (IUBuyOffer offer : incOffers) {
			ownerOffers.add(new BuyOffer(offer));
		}
		
		if (ownerId == local.getMineId())
			recalcMineOffers();
		
		buyOfferListener.onBuyOffersUpdated(companyId, getBuyOffersForCompany(companyId));
		
		if (ownerId == local.getMineId())
			buyOfferListener.onMineBuyOffersChanged(getMineBuyOffers());
	}
	
	private void processTransactionEvent(IUTransactionEvent event) {
		IUTransaction transaction = event.getTransaction();
		IUShareHolding trSh = transaction.getShareHolding();
		IUShareHolding updatedSh = null;
		
		if (!(local.getPlayer() instanceof Shareholder)) {
			logger.warn("Received IUTransactionEvent when I can't operate on shares: {}", event);
			return;
		}
		
		Shareholder shareholder = (Shareholder)local.getPlayer();
		
		// TODO redo this in such a way that it doesn't modify IULocalState (use LocalState)
		long mineId = local.getMineId();
		double subtractedMoney = 0.0;
		if (transaction.getBuyerId() == mineId) {
			updatedSh = shareholder.addShareHolding(trSh);
			subtractedMoney = transaction.getValue();
		} else if (transaction.getSellerId() == mineId) {
			updatedSh = shareholder.subtractShareHolding(trSh);
			subtractedMoney = -transaction.getValue();
		} else {
			logger.warn("Received IUTransactionEvent not intended for me: {}", event);
			return;
		}
		
		if (local.getPlayer() instanceof IULimitedBudgetPlayer) {
			Student student = (Student)local.getPlayerAsStudent();
			double oldCashValue = student.getCash();
			student.setCash(oldCashValue - subtractedMoney);
			
			playerStatsListener.onCashChange(student, oldCashValue, student.getCash());
		}
		
		playerStatsListener.onSharesChange(shareholder, updatedSh, trSh.getAmount(), subtractedMoney);
	}
	
	private void processTransferSharesEvent(IUTransferSharesEvent event) {
		IUShareHolding transferredSh = event.getTransferredShareHolding();
		
		if (!(local.getPlayer() instanceof Shareholder)) {
			logger.warn("Received IUTransferSharesEvent when I can't operate on shares: {}", event);
			return;
		}
		
		Shareholder shareholder = (Shareholder)local.getPlayer();
		IUShareHolding updatedSh = shareholder.addShareHolding(transferredSh);
		
		playerStatsListener.onSharesChange(shareholder, updatedSh, transferredSh.getAmount(), 0.0);
	}
	
	@Nullable
	private BuyOffer findBuyOffer(IUBuyOffer offerToFind, List<BuyOffer> list) {
		for (BuyOffer offer : list) {
			if (offer.equals(offerToFind))
				return offer;
		}
		
		return null;
	}
	
	private void removeBuyOffer(IUBuyOffer offerToRemove, List<BuyOffer> list) {
		Iterator<BuyOffer> it = list.iterator();
		while (it.hasNext()) {
			BuyOffer offer = it.next();
			if (offer.equals(offerToRemove))
				it.remove();
		}
	}
	
	private void recalcMineOffers() {
		List<BuyOffer> mineOffers = new ArrayList<BuyOffer>();
		
		for (Map<Long, List<BuyOffer>> companyOffers : buyOffers.values()) {
			List<BuyOffer> mineOffersForCompany = companyOffers.get(local.getMineId());
			if (mineOffersForCompany != null) {
				mineOffers.addAll(mineOffersForCompany);
			}
		}
		
		this.mineOffers = mineOffers;
	}

}

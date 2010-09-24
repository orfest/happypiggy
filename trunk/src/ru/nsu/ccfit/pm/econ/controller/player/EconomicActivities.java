package ru.nsu.ccfit.pm.econ.controller.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IEconomicActivities;
import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventConsumer;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBuyOffersChangeEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameSnapshotEvent;
import ru.nsu.ccfit.pm.econ.common.view.IEconomicActivityListener;

public class EconomicActivities extends AbstractGameEventGatewayClient
		implements IGameEventConsumer, IEconomicActivities {
	
	static final Logger logger = LoggerFactory.getLogger(EconomicActivities.class);
	
	/*
	 * Injectables.
	 */
	private IEconomicActivityListener listener;

	private double dramaticChange = 0;

	private double stockIndex = Double.MAX_VALUE;
	
	@Inject
	public void setListener(IEconomicActivityListener listener) {
		this.listener = listener;
	}

	@Override
	public double getDramaticShareMarketPrecentageChange() {
		return dramaticChange;
	}

	@Override
	public double getStockIndex() {
		return stockIndex;
	}

	@Override
	public void setDramaticShareMarketPrecentageChange(double precentage) {
		this.dramaticChange = precentage;
		// TODO fire event if change is already too big
	}
	
	@Override
	public boolean processEvent(IUGameEvent event) {
		if (event instanceof IUBuyOffersChangeEvent){
			processBuyOffersChangeEvent((IUBuyOffersChangeEvent)event);
		} else if (event instanceof IUGameSnapshotEvent){
			processGameSnapshotEvent((IUGameSnapshotEvent)event);
		} else {
			return false;
		}
		
		return true;
	}
	
	private void processBuyOffersChangeEvent(IUBuyOffersChangeEvent event) {
		if (event.getNewStockIndex() != stockIndex) {
			stockIndex = event.getNewStockIndex();
			listener.onStockIndexChange(stockIndex);
		}
		
		// onShareMarketValueChange is fired by CompanyRoster
		// TODO fire onDramaticTurnShareMarketValueChange if needed
	}
	
	private void processGameSnapshotEvent(IUGameSnapshotEvent event) {
		for (IUCompany company : event.getScenario().getCompanies()) {
			listener.onShareMarketValueChange(company, company.getShareMarketValue());
		}
		
		if (event.getStockIndex() != stockIndex) {
			stockIndex = event.getStockIndex();
			listener.onStockIndexChange(stockIndex);
		}
	}

}

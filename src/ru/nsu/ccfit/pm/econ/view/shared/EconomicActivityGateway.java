package ru.nsu.ccfit.pm.econ.view.shared;

import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IEconomicActivities;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.view.IEconomicActivityListener;

/**
 * Gateway for views to interact with <tt>PlayerController</tt> "economic activity"
 * features.
 * @see IEconomicActivities
 * @see IEconomicActivityListener
 * 
 * @author dragonfly
 */
public class EconomicActivityGateway implements IEconomicActivities, IEconomicActivityListener {
	
	static final Logger logger = LoggerFactory.getLogger(EconomicActivityGateway.class);
	
	protected EconomicActivityListenerList listeners = new EconomicActivityListenerList();
	private IEconomicActivities controller;

	@Override
	public double getDramaticShareMarketPrecentageChange() {
		return controller.getDramaticShareMarketPrecentageChange();
	}

	@Override
	public double getStockIndex() {
		return controller.getStockIndex();
	}

	@Override
	public void setDramaticShareMarketPrecentageChange(double precentage) {
		controller.setDramaticShareMarketPrecentageChange(precentage);
	}

	@Override
	public void onDramaticTurnShareMarketValueChange(final IUCompany company,
			final double valueChange, final double valueChangePrecentage) {
		
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onDramaticTurnShareMarketValueChange(company, 
						valueChange, valueChangePrecentage);
			}
		});
	}

	@Override
	public void onShareMarketValueChange(final IUCompany company, final double newShareMarketValue) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onShareMarketValueChange(company, newShareMarketValue);
			}
		});
	}

	@Override
	public void onStockIndexChange(final double newStockIndex) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onStockIndexChange(newStockIndex);
			}
		});
	}
	
	public ListenerList<IEconomicActivityListener> getEconomicActivityListeners() {
		return listeners;
	}
	
	@Inject
	public void setEconomicActivities(IEconomicActivities controller) {
		if (this.controller != null)
			logger.warn("Redefining controller");
		this.controller = controller;
	}
	
	protected static class EconomicActivityListenerList 
		extends ListenerList<IEconomicActivityListener> implements IEconomicActivityListener {

		@Override
		public void onDramaticTurnShareMarketValueChange(IUCompany company,
				double valueChange, double valueChangePrecentage) {
			for (IEconomicActivityListener listener : this) 
				listener.onDramaticTurnShareMarketValueChange(company, valueChange, valueChangePrecentage);
		}

		@Override
		public void onShareMarketValueChange(IUCompany company, double newShareMarketValue) {
			for (IEconomicActivityListener listener : this) 
				listener.onShareMarketValueChange(company, newShareMarketValue);
		}

		@Override
		public void onStockIndexChange(double newStockIndex) {
			for (IEconomicActivityListener listener : this) 
				listener.onStockIndexChange(newStockIndex);
		}
		
	}

}

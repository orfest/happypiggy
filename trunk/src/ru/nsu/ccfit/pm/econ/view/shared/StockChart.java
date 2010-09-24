package ru.nsu.ccfit.pm.econ.view.shared;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.pivot.charts.ChartViewListener;
import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.content.Point;
import org.apache.pivot.charts.content.ValueSeries;
import org.apache.pivot.collections.List;
import org.apache.pivot.wtkx.Bindable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.view.IEconomicActivityListener;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;

import com.google.gag.annotation.remark.Facepalm;
import com.google.inject.Inject;

public class StockChart extends LineChartView implements Bindable,
		InjectionVisitable, IEconomicActivityListener, IStockChartVisibilityListener {

	static final Logger logger = LoggerFactory.getLogger(StockChart.class);

	private static final int stkChartLength = 33;

	private HashMap<Long, ArrayList<Float>> stockValues = new HashMap<Long, ArrayList<Float>>();
	private HashMap<Long, Boolean> visibility = new HashMap<Long, Boolean>();
	private HashMap<Long, Integer> seriesNum = new HashMap<Long, Integer>();
	private int seriesTotal = 0;
	
	/*
	 * Injectables.
	 */
	private EconomicActivityGateway economicActivitiesGw;

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");
		getChartData().clear();
	}

	@Inject
	public void setEconomicActivityGateway(EconomicActivityGateway gateway) {
		if (this.economicActivitiesGw != null) {
			logger.warn("Redefining cncGateway");
			this.economicActivitiesGw.getEconomicActivityListeners().remove(this);
		}

		this.economicActivitiesGw = gateway;
		this.economicActivitiesGw.getEconomicActivityListeners().add(this);
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
		// no children
	}

	@Override
	public void onDramaticTurnShareMarketValueChange(IUCompany company,
			double valueChange, double valueChangePrecentage) {
		// TODO Auto-generated method stub
	}

	@Override
	@Facepalm
	public void onShareMarketValueChange(IUCompany company, double newShareMarketValue) {
		if (!stockValues.containsKey(company.getId())) {
			// new company
			ArrayList<Float> val = new ArrayList<Float>(stkChartLength);
			for (int i = 0; i < stkChartLength; i++) {
				val.add((float) newShareMarketValue);
			}
			stockValues.put(company.getId(), val);
			seriesNum.put(company.getId(), seriesTotal);
			seriesTotal++;
			visibility.put(company.getId(), true);
			
			ValueSeries<Point> vs = new ValueSeries<Point>();
			vs.setName(company.getName());
			addSeries(vs);
		}
		ArrayList<Float> points = stockValues.get(company.getId());
		points.remove(0);
		points.add((float) newShareMarketValue);
		int num = seriesNum.get(company.getId());
		boolean vis = visibility.get(company.getId());
		if (!vis) {
			ValueSeries<Point> s0 = getSeries(num);
			s0.clear();
		} else {
	        List<?> previousChartData = this.getChartData();
			ValueSeries<Point> s = getSeries(num);
			s.clear();
			for (int j = 0; j < stkChartLength; j++) {
				Point p = new Point();
				p.setX((float) j);
				p.setY(stockValues.get(company.getId()).get(j));
				s.add(p);
			}
	        for (ChartViewListener listener : this.getChartViewListeners()) {
	            listener.chartDataChanged(this, previousChartData);
	        }
		}
	}

	@Override
	public void onStockIndexChange(double newStockIndex) {
		// no action required
	}

	@Override
	public void onVisibilityChange(IUCompany company, boolean isVisible) {
		if (!visibility.containsKey(company.getId()))
			return;
		visibility.remove(company.getId());
		visibility.put(company.getId(), isVisible);
	}
	
	@SuppressWarnings("unchecked")
	private ValueSeries<Point> getSeries(int idx) {
		return (ValueSeries<Point>) this.getChartData().get(idx);
	}
	
	@SuppressWarnings("unchecked")
	private void addSeries(ValueSeries<Point> series) {
		((List<ValueSeries<Point>>)getChartData()).add(series);
	}

}

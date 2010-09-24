package ru.nsu.ccfit.pm.econ.view.shared;

import java.util.LinkedList;

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

import com.google.inject.Inject;

public class IndexChart extends LineChartView implements Bindable,
		InjectionVisitable, IEconomicActivityListener {

	static final Logger logger = LoggerFactory.getLogger(IndexChart.class);
	private static final int indChartLength = 33;

	private java.util.List<Float> indexValues = new LinkedList<Float>();

	/*
	 * Injectables.
	 */
	private EconomicActivityGateway pcEaGateway;

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");
	}

	@Inject
	public void setEconomicActivityGateway(EconomicActivityGateway gateway) {
		if (this.pcEaGateway != null) {
			logger.warn("Redefining cncGateway");
			this.pcEaGateway.getEconomicActivityListeners().remove(this);
		}

		this.pcEaGateway = gateway;
		this.pcEaGateway.getEconomicActivityListeners().add(this);
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
		// no children
	}

	@Override
	public void onDramaticTurnShareMarketValueChange(IUCompany company,
			double valueChange, double valueChangePrecentage) {
		// no action required
	}

	@Override
	public void onShareMarketValueChange(IUCompany company, double newShareMarketValue) {
		// no action required
	}

	@Override
	public void onStockIndexChange(double newStockIndex) {
        List<?> previousChartData = this.getChartData();
		// fill data
		if (indexValues.size() == 0) {
			for (int i = 0; i < indChartLength; i++) {
				indexValues.add((float) newStockIndex);
			}
		}
		indexValues.remove(0);
		indexValues.add((float) newStockIndex);

		ValueSeries<Point> s0 = getSeries();
		s0.clear();
		for (int j = 0; j < indChartLength; j++) {
			Point p = new Point();
			p.setX((float) j);
			p.setY(indexValues.get(j));
			s0.add(p);
		}
        for (ChartViewListener listener : this.getChartViewListeners()) {
            listener.chartDataChanged(this, previousChartData);
        }

	}
	
	@SuppressWarnings("unchecked")
	private ValueSeries<Point> getSeries() {
		return (ValueSeries<Point>) this.getChartData().get(0);
	}

}

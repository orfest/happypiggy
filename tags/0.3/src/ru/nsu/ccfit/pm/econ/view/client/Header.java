package ru.nsu.ccfit.pm.econ.view.client;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.pivot.wtk.Border;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.view.shared.IndexChart;
import ru.nsu.ccfit.pm.econ.view.shared.StockChart;
import ru.nsu.ccfit.pm.econ.view.shared.TurnIndicator;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;

public class Header extends Border implements Bindable, InjectionVisitable {
	
	static final Logger logger = LoggerFactory.getLogger(Header.class);
	
	/*
	 * Child elements.
	 */
	@WTKX private TurnIndicator turnIndicator;
	@WTKX private IndexChart indexChart;
	@WTKX private StockChart stockChart;
	private List<InjectionVisitable> visitableChildren = new ArrayList<InjectionVisitable>();

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");
		
		visitableChildren.add(checkNotNull(turnIndicator));
		visitableChildren.add(checkNotNull(indexChart));
		visitableChildren.add(checkNotNull(stockChart));
		// other child elements should be added to visitable list here
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		for (InjectionVisitable child : visitableChildren)
			child.acceptInjectionVisitor(visitor);
	}

}

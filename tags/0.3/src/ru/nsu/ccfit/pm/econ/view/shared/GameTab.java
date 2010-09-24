package ru.nsu.ccfit.pm.econ.view.shared;

import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Container;
import org.apache.pivot.wtk.GridPane;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TabPaneAttributeListener;
import org.apache.pivot.wtk.media.Image;

import com.google.gag.annotation.remark.Facepalm;

import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;

@Facepalm
public class GameTab extends GridPane implements TabPaneAttributeListener, IUpdatableView, InjectionVisitable {
	
	private int numUpdates = 0;
	private String baseLabel;
	
	public GameTab() {
		super();
		setColumnCount(1);
	}

	@Override
	public void setNumberOfUpdates(int newNumUpdates) {
		if (newNumUpdates < 0)
			newNumUpdates = 0;
		
		numUpdates = newNumUpdates;
		applyNumberOfUpdates();
	}
	
	@Override
	public int getNumberOfUpdates() {
		return numUpdates;
	}
	
	@Override
	public void increaseNumberOfUpdates(int increaseBy) {
		setNumberOfUpdates(getNumberOfUpdates() + increaseBy);
	}
	
	@Override
	public void decreaseNumberOfUpdates(int decreaseBy) {
		if (decreaseBy > getNumberOfUpdates())
			setNumberOfUpdates(0);
		else
			setNumberOfUpdates(getNumberOfUpdates() - decreaseBy);
	}
	
	private TabPane getTabPane() {
		return (TabPane) this.getParent();
	}
	
	private void applyNumberOfUpdates() {
		TabPane.setLabel(this, getLabelNameWithNumberOfUpdates());
	}
	
	private String getLabelNameWithNumberOfUpdates() {
		if (numUpdates == 0)
			return baseLabel;
		else
			return baseLabel + " (" + numUpdates + ")";
	}

	@Override
	public void closeableChanged(TabPane tabPane, Component component) {
		// no action required
	}

	@Override
	public void iconChanged(TabPane tabPane, Component component, Image previousIcon) {
		// no action required
	}

	@Override
	public void labelChanged(TabPane tabPane, Component component, String previousLabel) {
		if (component != this)
			return;
		
		String label = TabPane.getLabel(component);
		if (!label.equals(getLabelNameWithNumberOfUpdates())) {
			baseLabel = label;
			applyNumberOfUpdates();
		}
	}

	@Override
	protected void setParent(Container parent) {
		super.setParent(parent);
		
		TabPane tabPane;
		try {
			tabPane = getTabPane();
			if (tabPane == null) 
				throw new IllegalStateException("GameTab should be a child of TabPane component");
		} catch (ClassCastException e) {
			throw new IllegalStateException("GameTab should be a child of TabPane component", e);
		}
		
		baseLabel = TabPane.getLabel(this);
		tabPane.getTabPaneAttributeListeners().add(this);
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
	}

}

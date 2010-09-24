package ru.nsu.ccfit.pm.econ.view.shared;

/**
 * Provides operations for update notification management.
 * 
 * @author dragonfly
 */
public interface IUpdatableView {
	
	public void setNumberOfUpdates(int newNumUpdates);
	
	public int getNumberOfUpdates();
	
	public void increaseNumberOfUpdates(int increaseBy);
	
	public void decreaseNumberOfUpdates(int decreaseBy);

}

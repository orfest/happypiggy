package ru.nsu.ccfit.pm.econ.view.shared.wtkx;

/**
 * Listener interface to react to value changes.
 * @see Value
 * @author dragonfly
 *
 * @param <T> Class that implements value object.
 * @param <S> Class that represents value data.
 */
public interface ValueListener<T extends Value<S>, S> {
	
	/**
	 * Called when value is changed.
	 * @param valueObject Value object that hold the data.
	 * @param newValue Updated value.
	 * @param oldValue Old value.
	 */
	void valueChanged(T valueObject, S newValue, S oldValue);

}

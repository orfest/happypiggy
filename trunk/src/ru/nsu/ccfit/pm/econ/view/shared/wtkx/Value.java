package ru.nsu.ccfit.pm.econ.view.shared.wtkx;

import org.apache.pivot.util.ListenerList;

/**
 * Base class for value objects that are used to facilitate Pivot 
 * data binding. Value objects hold value of a certain type and 
 * are able to notify about value changes via {@link ValueListener}.
 * @author dragonfly
 *
 * @param <S> Class that represents value data, which is stored inside
 * 				value object.
 */
public class Value<S> {
	
	private S value;
	protected ValueListenerList<S> listeners = new ValueListenerList<S>();
	
	public Value(S defaultValue) {
		this.value = defaultValue;
	}

	public S getValue() {
		return value;
	}

	public void setValue(S value) {
		S oldValue = this.value;
		this.value = value;
		listeners.valueChanged(this, this.value, oldValue);
	}
	
	public ListenerList<ValueListener<Value<S>, S>> getValueListeners() {
		return listeners;
	}
	
	protected static class ValueListenerList<S> 
		extends ListenerList<ValueListener<Value<S>, S>> implements ValueListener<Value<S>, S> {

		@Override
		public void valueChanged(Value<S> valueObject, S newValue, S oldValue) {
			for (ValueListener<Value<S>, S> listener : this) {
				listener.valueChanged(valueObject, newValue, oldValue);
			}
		}
		
	}

}

package ru.nsu.ccfit.pm.econ.view.shared.wtkx;

/**
 * Boolean value object.
 * <p>Use <tt>val</tt> property to get primitive boolean type.</p>
 * @see Value
 * @author dragonfly
 */
public class BooleanValue extends Value<Boolean> {
	
	public BooleanValue() {
		super(false);
	}

	public boolean getVal() {
		return getValue();
	}

	public void setVal(boolean value) {
		setValue(value);
	}
	
}

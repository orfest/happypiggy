package ru.nsu.ccfit.pm.econ.view.shared.wtkx;

/**
 * Integer value object. 
 * <p>Use <tt>val</tt> property to get primitive int type.</p>
 * @see Value
 * @author dragonfly
 */
public class IntegerValue extends Value<Integer> {
	
	public IntegerValue() {
		super(0);
	}
	
	public int getVal() {
		return getValue();
	}
	
	public void setVal(int value) {
		setValue(value);
	}

}

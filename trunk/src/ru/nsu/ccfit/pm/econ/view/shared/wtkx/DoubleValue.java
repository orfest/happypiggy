package ru.nsu.ccfit.pm.econ.view.shared.wtkx;

/**
 * Double value object. 
 * <p>Use <tt>val</tt> property to get primitive double type.</p>
 * @see Value
 * @author dragonfly
 */
public class DoubleValue extends Value<Double> {
	
	public DoubleValue() {
		super(0.0);
	}
	
	public double getVal() {
		return getValue();
	}
	
	public void setVal(double value) {
		setValue(value);
	}

}

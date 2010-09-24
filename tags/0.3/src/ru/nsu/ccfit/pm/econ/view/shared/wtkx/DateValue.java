package ru.nsu.ccfit.pm.econ.view.shared.wtkx;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date value object.
 * @see Value
 * @author dragonfly
 */
public class DateValue extends Value<Date> {
	
	public DateValue() {
		super(new Date(0));
	}
	
	private DateFormat dateFormat = new SimpleDateFormat("mm:ss");
	
	public String getAsString() {
		return dateFormat.format(getValue());
	}
	
	public void setDateFormat(String format) {
		dateFormat = new SimpleDateFormat(format);
		listeners.valueChanged(this, getValue(), getValue());
	}

}

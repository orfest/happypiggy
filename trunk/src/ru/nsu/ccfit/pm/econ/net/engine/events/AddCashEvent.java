package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUAddCashEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;

/**
 * @author orfest
 * 
 */
public class AddCashEvent extends GameEvent implements IUAddCashEvent {

	@SerializeThis
	private double addedCash = Double.MAX_VALUE;
	@SerializeThis
	private String message = "";

	public AddCashEvent() {
	}

	public double getAddedCash() {
		return addedCash;
	}

	public String getMessage() {
		return message;
	}

	public void setAddedCash(double addedCash) {
		this.addedCash = addedCash;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

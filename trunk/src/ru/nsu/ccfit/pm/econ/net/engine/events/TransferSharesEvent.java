package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTransferSharesEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.ShareHolding;

/**
 * @author orfest
 * 
 */
public class TransferSharesEvent extends GameEvent implements
		IUTransferSharesEvent {

	@SerializeThis
	private IUShareHolding transferredShareHolding = new ShareHolding();
	@SerializeThis
	private String message = "";

	public TransferSharesEvent() {
	}

	public IUShareHolding getTransferredShareHolding() {
		return transferredShareHolding;
	}

	public String getMessage() {
		return message;
	}

	public void setTransferredShareHolding(
			IUShareHolding transferredShareHolding) {
		this.transferredShareHolding = transferredShareHolding;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

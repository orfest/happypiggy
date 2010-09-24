package ru.nsu.ccfit.pm.econ.common.engine.events;

import javax.annotation.Nullable;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;

/**
 * Unmodifiable interface for unconditional share holding transfer.
 * This feature may be used by Teacher to help certain players.
 * @see IUAddCashEvent
 * @author dragonfly
 */
public interface IUTransferSharesEvent extends IUGameEvent {
	
	/**
	 * Share holding that gets transferred from sender to 
	 * receiver.
	 * @return Transferred share holding.
	 */
	IUShareHolding getTransferredShareHolding();
	
	/**
	 * Accompanying text message.
	 * @return Text message or <tt>null</tt> if none available.
	 */
	@Nullable
	String getMessage();

}

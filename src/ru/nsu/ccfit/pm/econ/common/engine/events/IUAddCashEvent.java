package ru.nsu.ccfit.pm.econ.common.engine.events;

import javax.annotation.Nullable;

/**
 * Unmodifiable interface for unconditional cash value change.
 * This feature may be used by Teacher to help certain players.
 * @see IUTransferSharesEvent
 * @author dragonfly
 */
public interface IUAddCashEvent extends IUGameEvent {
	
	/**
	 * Cash value difference. New recipient player cash value is
	 * calculated by adding this value to current cash amount.
	 * @return Added cash value. Can be negative (which results in
	 * 			cash withdrawal).
	 */
	double getAddedCash();
	
	/**
	 * Accompanying text message.
	 * @return Text message or <tt>null</tt> if none available.
	 */
	@Nullable
	String getMessage();

}

package ru.nsu.ccfit.pm.econ.common.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUTransaction;

/**
 * Unmodifiable interface for passing transaction events to
 * interested parties (seller, buyer and server).
 * <p>Note that handling of this event should not result in 
 * immediate buy offers update. Buy offers update will arrive 
 * later as separate event.</p>
 * @see IUBuyOffersChangeEvent
 * @author dragonfly
 */
public interface IUTransactionEvent extends IUGameEvent {
	
	/**
	 * Transaction that happened.
	 * @return Transaction object.
	 */
	IUTransaction getTransaction();

}

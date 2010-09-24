package ru.nsu.ccfit.pm.econ.common.engine.events;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;

/**
 * Unmodifiable base interface for game events. This interface
 * is also used to represent game history events.
 * @author dragonfly
 */
public interface IUGameEvent {
	
	/**
	 * Event time, including turn number.
	 * @return Event time.
	 */
	IUGameTime getEventTime();
	
	/**
	 * Set of event receivers.
	 * <p>If everyone should receive this event, {@link #isBroadcast()}
	 * should return <tt>true</tt>.</p>
	 * @return Collection of event receiver identifiers.
	 */
	Collection<Long> getReceiverIds();
	
	/**
	 * Whether this event should be broadcasted to all players or not. 
	 * <p>If event is broadcast {@link #getReceiverIds()} should return 
	 * all player identifiers.</p>
	 * @return <tt>true</tt> if this event should be broadcasted to all
	 * 			players, and <tt>false</tt> otherwise.
	 */
	boolean isBroadcast();
	
	/**
	 * Event sender.
	 * @return Event sender identifier.
	 */
	long getSenderId();

}

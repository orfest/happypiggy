package ru.nsu.ccfit.pm.econ.common.engine.events;

import javax.annotation.Nonnegative;

/**
 * Unmodifiable interface for signaling turn start event.
 * <p>Depending on engine implementation this event may 
 * be fired solely by engine, or firstly by Teacher and 
 * then by engine (upon receiving this event from Teacher).
 * In case this event is fired by Teacher, 
 * {@link #getNewTurnNumber()} value is ignored by engine.</p>
 * @author dragonfly
 */
public interface IUTurnStartEvent extends IUGameEvent {
	
	/**
	 * Number of turn which has just started. Turns are numbered 
	 * from <tt>1</tt>. Turn before game start has number <tt>0</tt>. 
	 * @return New turn number.
	 */
	@Nonnegative
	int getNewTurnNumber();

}

package ru.nsu.ccfit.pm.econ.common.engine.data;

import java.util.Date;

import javax.annotation.Nonnegative;

/**
 * Unmodifiable interface which represents game time.
 * @author dragonfly
 */
public interface IUGameTime {
	
	/**
	 * Turn number to which the time relates. I.e. the number of turn 
	 * where certain event occurred. Turns are numbered starting from 1.
	 * @return Turn number.
	 */
	@Nonnegative
	int getTurnNumber();
	
	/**
	 * Represents time passed between turn start and the event. 
	 * <p>This value should be less or equal to turn length, but this 
	 * property must not be enforced.</p>
	 * <p>Implementations should not return internal representation
	 * of time to avoid its modification by caller. Also returned 
	 * value should never be <tt>null</tt>.</p>
	 * @return Time passed from the turn start.
	 */
	Date getTime();
	
	/**
	 * Whether current turn (see {@link #getTurnNumber()} is finished
	 * or not. If the turn is finished {@link #getTime()} value should
	 * not generally matter, but nevertheless should be valid.
	 * @return <tt>true</tt> if turn is already finished, but next turn 
	 * 			has not started yet, and <tt>false</tt> otherwise.
	 */
	boolean isTurnFinished();

}

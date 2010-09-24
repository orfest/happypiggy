package ru.nsu.ccfit.pm.econ.common.controller.player;

import java.util.Date;

import javax.annotation.Nonnegative;

import ru.nsu.ccfit.pm.econ.common.view.IGameTimeListener;

/**
 * Interface used to supply information about current game time.
 * 
 * <p>Warning: implementations should avoid returning internal 
 * Data object representations to avoid mutability issues.</p>
 * @see IGameTimeListener
 * 
 * @author dragonfly
 */
public interface IGameTime {
	
	/**
	 * Whether game is already running or not.
	 * @return <tt>true</tt> if game is running, <tt>false</tt> otherwise.
	 */
	boolean isGameStarted();
	
	/**
	 * Current turn number. Turns are numbered starting from 1.
	 * The turn before game start has number 0.
	 * @return Current turn number. 
	 */
	@Nonnegative
	int getTurnNumber();
	
	/**
	 * Time elapsed since current turn start. This value is expected to 
	 * be less or equal to the turn length as returned by 
	 * {@link #getTurnLength()}.
	 * @return Time elapsed since current turn start.
	 */
	Date getTime();
	
	/**
	 * Whether current turn is in finished state or not.
	 * <p>Finished state of the turn is used for various game actions, 
	 * such as dividend voting and loans/deposit acceptance decisions.
	 * When turn is in finished state turn time clock does not tick.</p>
	 * @return <tt>true</tt> if current turn is finished, 
	 * 			<tt>false</tt> otherwise.
	 */
	boolean isTurnFinished();
	
	/**
	 * Maximum turn length as defined by scenario or Teacher.
	 * @return Maximum turn length.
	 */
	Date getTurnLength();
	
	/**
	 * @return Time elapsed since the game started.
	 */
	Date getTimeElapsedFromGameStart();

}

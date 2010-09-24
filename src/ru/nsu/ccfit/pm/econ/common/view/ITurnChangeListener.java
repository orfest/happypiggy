package ru.nsu.ccfit.pm.econ.common.view;

/**
 * Listener interface for turn change and game start events.
 * 
 * @author dragonfly
 */
public interface ITurnChangeListener {
	
	/**
	 * Fired on game start.
	 */
	void onGameStart();
	
	/**
	 * Fired when current turn number changes.
	 * @param newTurnNumber New current turn number.
	 */
	void onTurnNumberChange(int newTurnNumber);
	
	/**
	 * Fired when turn state changes.
	 * @param isFinished Whether new current turn state is 
	 * 					finished or not.
	 */
	void onTurnStateChange(boolean isFinished);

}

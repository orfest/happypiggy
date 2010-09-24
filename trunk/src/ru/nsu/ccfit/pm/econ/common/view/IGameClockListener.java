package ru.nsu.ccfit.pm.econ.common.view;

import java.util.Date;

/**
 * Listener interface for game clock tick event.
 * 
 * @author dragonfly
 */
public interface IGameClockListener {
	
	/**
	 * Fired each second when time elapsed since turn start 
	 * changes.
	 * @param time Time elapsed from the current turn start. 
	 */
	void onTickTock(Date time);

}

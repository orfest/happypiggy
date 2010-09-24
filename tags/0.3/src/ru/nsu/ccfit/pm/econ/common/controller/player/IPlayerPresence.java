package ru.nsu.ccfit.pm.econ.common.controller.player;

/**
 * Interface that provides player presence status. Available from 
 * <tt>PlayerController</tt> only when in server.
 * <p>Components that may use this interface:
 * <ul>
 * <li>ServerUI</li>
 * </ul>
 * @see ru.nsu.ccfit.pm.econ.common.engine.IPlayerPresence
 * 
 * @author dragonfly
 */
public interface IPlayerPresence {
	
	/**
	 * Get player presence status.
	 * @param playerId Identifies player.
	 * @return Player presence status. 
	 * 			If <tt>playerId</tt> is invalid, <tt>false</tt> is returned.
	 */
	boolean isPlayerPresent(long playerId);

}

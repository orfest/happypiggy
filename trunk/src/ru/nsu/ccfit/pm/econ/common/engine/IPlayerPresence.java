package ru.nsu.ccfit.pm.econ.common.engine;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUBank;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUStudent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUTeacher;

/**
 * Interface to register players and signal player presence.
 * <p>Components that may use this interface:
 * <ul>
 * <li>Networking</li>
 * <li>PlayerController</li>
 * <li>BotEngine</li>
 * <li>ScenarioController</li>
 * </ul>
 * 
 * @author dragonfly
 */
public interface IPlayerPresence {
	
	/**
	 * Registers a new player within the engine.
	 * @param player Player data. Instead of generic {@link IUPlayer} interface callers 
	 * 					should supply instances of {@link IUStudent}, {@link IUBank} or {@link IUTeacher}.
	 * @return Id that should be assigned to this player. Upon return caller should assign this id 
	 * 			so that {@link IUPlayer#getId()} returns this value.
	 * @throws IllegalArgumentException If player data is invalid (e.g. name is empty or invalid).
	 */
	long registerPlayer(IUPlayer player) throws IllegalArgumentException;
	
	/**
	 * Unregister player within the engine, effectively "killing" it from the point of 
	 * view of the engine.
	 * <p>If passed <tt>playerId</tt> is invalid, nothing happens.
	 * @param playerId Id of the player to unregister.
	 */
	void unregisterPlayer(long playerId);
	
	/**
	 * Change player presence status.
	 * <p>Player presence affects whether certain operations may or may not be 
	 * performed with it. E.g. if player is not present, it does not participate in
	 * any kind of voting. Generally if player is not present this means that it's 
	 * client has disconnected for some reason.</p>
	 * @param playerId Identifies player to change it's presence. 
	 * 					If this field is invalid, nothing happens.
	 * @param isPresent Whether player is present or not.
	 * @see #isPlayerPresent(long)
	 */
	void setPlayerPresence(long playerId, boolean isPresent);
	
	/**
	 * Get player presence status.
	 * @param playerId Identifies player.
	 * @return Player presence status. 
	 * 			If <tt>playerId</tt> is invalid, <tt>false</tt> is returned.
	 * @see #setPlayerPresence(long, boolean)
	 */
	boolean isPlayerPresent(long playerId);

}

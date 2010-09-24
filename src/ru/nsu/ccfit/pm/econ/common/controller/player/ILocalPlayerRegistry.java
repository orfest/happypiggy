package ru.nsu.ccfit.pm.econ.common.controller.player;

import ru.nsu.ccfit.pm.econ.common.engine.IPlayerPresence;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUBank;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUStudent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUTeacher;

/**
 * Interface to register players local to the entity where 
 * engine is running (that is, mainly to register Teacher 
 * within the server).
 * <p>Components that may use this interface:
 * <ul>
 * <li>ServerUI</li>
 * </ul>
 * @see IPlayerPresence
 * 
 * @author dragonfly
 */
public interface ILocalPlayerRegistry {
	
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

}

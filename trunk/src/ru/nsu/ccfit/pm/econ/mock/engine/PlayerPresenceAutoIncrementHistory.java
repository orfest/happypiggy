/**
 * 
 */
package ru.nsu.ccfit.pm.econ.mock.engine;

import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.engine.IPlayerPresence;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

/**
 * @author orfest
 *
 */
public class PlayerPresenceAutoIncrementHistory implements IPlayerPresence {

	public LinkedList<IUPlayer> getPlayers() {
		return players;
	}

	private long id = 1; 
	private LinkedList<IUPlayer> players = new LinkedList<IUPlayer>();
	
	/**
	 * 
	 */
	public PlayerPresenceAutoIncrementHistory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPlayerPresent(long playerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long registerPlayer(IUPlayer player) throws IllegalArgumentException {
		players.add(player);
		return id++;
	}

	@Override
	public void setPlayerPresence(long playerId, boolean isPresent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterPlayer(long playerId) {
		// TODO Auto-generated method stub

	}

}

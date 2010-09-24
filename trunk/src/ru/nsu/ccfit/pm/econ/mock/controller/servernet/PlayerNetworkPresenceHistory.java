/**
 * 
 */
package ru.nsu.ccfit.pm.econ.mock.controller.servernet;

import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.controller.servernet.IPlayerNetworkPresence;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;

/**
 * @author orfest
 *
 */
public class PlayerNetworkPresenceHistory implements IPlayerNetworkPresence {

	private LinkedList<IUPersonDescription> players = new LinkedList<IUPersonDescription>();
	
	/**
	 * 
	 */
	public PlayerNetworkPresenceHistory() {
	}

	@Override
	public void addPlayer(IUPersonDescription playerData) {
		players.add(playerData);
	}

	public LinkedList<IUPersonDescription> getPlayers() {
		return players;
	}

	@Override
	public void removePlayer(IUPersonDescription playerData) {

	}

}

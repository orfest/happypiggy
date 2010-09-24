package ru.nsu.ccfit.pm.econ.common.net;

import ru.nsu.ccfit.pm.econ.common.controller.servernet.IPlayerNetworkPresence;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;

/**
 * Interface that combines available network operations that may
 * be performed on players.
 * <p>Components that may use this interface:
 * <ul>
 * <li>ServerNetworkController</li>
 * </ul>
 * </p>
 * 
 * @see IPlayerNetworkPresence
 * @author dragonfly
 */
public interface IPlayerNetworkOperations {
	
	/**
	 * Kick selected player.
	 * <p>Primarily used to kick unwanted players from roster before game begins.
	 * But may as well be used to kick players during the game.</p>
	 * @param playerData Player to kick.
	 * @param reason Text message explaining kick reason.
	 */
	void kick(IUPersonDescription playerData, String reason);
	
	/**
	 * Ban selected annoying player. Currently the ban is permanent.
	 * <p>After player is banned any further connection attempt from that 
	 * player should be rejected with specified message.</p>
	 * @param playerData Player to ban.
	 * @param reason Text message explaining ban reason.
	 */
	void ban(IUPersonDescription playerData, String reason);
	
	/**
	 * Temporarily kick all connected players.
	 * @param reason Text message explaining kick reason.
	 */
	void kickAll(String reason);

}

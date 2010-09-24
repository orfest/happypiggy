package ru.nsu.ccfit.pm.econ.common.view.server;

import ru.nsu.ccfit.pm.econ.common.controller.servernet.IPlayerNetworkPresence;
import ru.nsu.ccfit.pm.econ.common.controller.servernet.IServerNetworkController;

/**
 * Interface to signal connected players.
 * <p>This interface is used <i>only</i> on player connection stage 
 * (i.e. before Teacher starts the game).</p>
 * <p>Components that may use this interface:
 * <ul><li>ServerNetworkController</li></ul>
 * </p>
 * @see IPlayerNetworkPresence
 * @see IServerNetworkController
 * @author dragonfly
 */
public interface IPlayerRoster extends IPlayerNetworkPresence {

}

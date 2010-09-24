package ru.nsu.ccfit.pm.econ.view.server;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.view.server.IPlayerRoster;

/**
 * @see IPlayerRoster
 * @author dragonfly
 */
public interface IPlayerConnectionListener {
	
	void onPlayerConnect(IUPersonDescription playerData);
	
	void onPlayerDisconnect(IUPersonDescription playerData);

}

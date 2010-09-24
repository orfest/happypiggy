package ru.nsu.ccfit.pm.econ.net;

import java.net.InetSocketAddress;
import java.net.Socket;

import ru.nsu.ccfit.pm.econ.common.controller.servernet.ServerStartStatus;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;

/**
 * @author orfest
 * 
 */
public interface IServerCoordinator {
	public String shouldPlayerBeAdded(IUPersonDescription playerDescription);

	public void gameEventArrived(IUGameEvent gameEvent);

	public void playerConnected(IUPersonDescription playerDescription, Socket socket);

	public void clientDisconnected(IUPersonDescription playerData);
	
	public void bindResult(ServerStartStatus status, InetSocketAddress bindAddr);
}

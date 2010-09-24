/**
 * 
 */
package ru.nsu.ccfit.pm.econ.controller.servernet;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.List;

import ru.nsu.ccfit.pm.econ.common.controller.servernet.ServerStartStatus;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameProperties;
import ru.nsu.ccfit.pm.econ.common.net.INetworkServer;
import ru.nsu.ccfit.pm.econ.common.view.server.IServerLifecycleNetworkEvents;

/**
 * @author orfest
 * 
 */
public class ServerControllerStarterThread implements Runnable {

	private IServerLifecycleNetworkEvents events;
	private InetSocketAddress bindAddress;
	private boolean autolocation;
	private IUGameProperties gamePropeties;
	private INetworkServer server;
	private ServerNetworkController controller;

	public ServerControllerStarterThread(IServerLifecycleNetworkEvents events_, IUGameProperties gamePropeties_,
			boolean autolocation_, InetSocketAddress bindAddress_, INetworkServer server_,
			ServerNetworkController controller_) {
		bindAddress = bindAddress_;
		events = events_;
		gamePropeties = gamePropeties_;
		autolocation = autolocation_;
		server = server_;
		controller = controller_;
	}

	@Override
	public void run() {
		try {
			List<InetSocketAddress> addresses = server.startServer(gamePropeties, autolocation, bindAddress);
			if (addresses == null){
				throw new SocketException();
			}
			controller.setRunning();
			ServerStartStatus status = ServerStartStatus.OK;
			if (!autolocation)
				status = ServerStartStatus.OK_NO_AUTOLOCATE;
			events.onServerStart(status, addresses.get(0));
		} catch (SocketException e) {
			events.onServerStartFailed(ServerStartStatus.CANNOT_BIND);
		}
	}

}

package ru.nsu.ccfit.pm.econ.common.view.server;

import java.net.InetSocketAddress;

import ru.nsu.ccfit.pm.econ.common.controller.servernet.IServerNetworkController;
import ru.nsu.ccfit.pm.econ.common.controller.servernet.ServerStartStatus;

/**
 * Interface to signal server life cycle events.
 * <p>Components that may use this interface:
 * <ul><li>ServerNetworkController</li></ul>
 * </p>
 * @see IServerNetworkController
 * @author dragonfly
 */
public interface IServerLifecycleNetworkEvents {
	
	/**
	 * Called on successful server start.
	 * <p><tt>status</tt> argument should have only success values - either
	 * {@link ServerStartStatus#OK} or {@link ServerStartStatus#OK_NO_AUTOLOCATE}.
	 * </p>
	 * @param status Server start status.
	 * @param bindAddress Address socket server is bound to.
	 */
	void onServerStart(ServerStartStatus status, InetSocketAddress bindAddress);
	
	/**
	 * Called on failed server start. Used to notify of the reason server 
	 * start has failed.
	 * <p><tt>status</tt> argument should have only fail values - other than
	 * {@link ServerStartStatus#OK} and {@link ServerStartStatus#OK_NO_AUTOLOCATE}.
	 * </p>
	 * @param status Server start status.
	 */
	void onServerStartFailed(ServerStartStatus status);
	
	/**
	 * Called when server has terminated.
	 */
	void onServerTerminate();

}

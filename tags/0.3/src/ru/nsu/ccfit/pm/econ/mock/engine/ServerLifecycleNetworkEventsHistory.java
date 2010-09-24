/**
 * 
 */
package ru.nsu.ccfit.pm.econ.mock.engine;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.controller.servernet.ServerStartStatus;
import ru.nsu.ccfit.pm.econ.common.view.server.IServerLifecycleNetworkEvents;

/**
 * @author orfest
 *
 */
public class ServerLifecycleNetworkEventsHistory implements IServerLifecycleNetworkEvents {

	private static Logger logger = LoggerFactory.getLogger(ServerLifecycleNetworkEventsHistory.class);
	private int started = 0;
	private int failed = 0;
	private int terminate = 0;
	
	/**
	 * 
	 */
	public ServerLifecycleNetworkEventsHistory() {
	}

	@Override
	public void onServerStart(ServerStartStatus status, InetSocketAddress bindAddress) {
		started++;
		logger.info("onServerStart");
	}

	@Override
	public void onServerStartFailed(ServerStartStatus status) {
		failed++;
		logger.info("onServerStartFailed");
	}

	@Override
	public void onServerTerminate() {
		terminate++;
		logger.info("onServerTerminate");
	}

	public Object getStarted() {
		return started;
	}

	public Object getFailed() {
		return failed;
	}

	public Object getTerminate() {
		return terminate;
	}

}

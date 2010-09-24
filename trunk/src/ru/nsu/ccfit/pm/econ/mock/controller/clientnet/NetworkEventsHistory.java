/**
 * 
 */
package ru.nsu.ccfit.pm.econ.mock.controller.clientnet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.controller.clientnet.INetworkEvents;
import ru.nsu.ccfit.pm.econ.common.net.IUServerProperties;

/**
 * @author orfest
 *
 */
public class NetworkEventsHistory implements INetworkEvents {

	private static Logger logger = LoggerFactory.getLogger(NetworkEventsHistory.class);
	
	private boolean connect = false;
	private boolean disconnect = false;
	private Exception disconnectCause = null;
	private boolean kick = false;
	private boolean ban = false;
	private String kickReason = null;
	private boolean serverLookupUpdate = false;
	private List<? extends IUServerProperties> servers = null; 
	
	public NetworkEventsHistory() {
	}

	@Override
	public void onConnect() {
		logger.info("onConnect");
		connect = true;
	}

	@Override
	public void onDisconnect(Exception cause) {
		logger.info("onDisconnect");
		disconnect = true;
		disconnectCause = cause;

	}

	@Override
	public void onKick(String reason, boolean isBanned) {
		logger.info("onKick");
		kick = true;
		kickReason = reason;
		ban = isBanned;
	}

	@Override
	public void onServerLookupUpdate(List<? extends IUServerProperties> serverList) {
		logger.info("onServerLookupUpdate");
		serverLookupUpdate = true;
		servers = serverList;
	}

	public boolean isConnect() {
		return connect;
	}

	public boolean isDisconnect() {
		return disconnect;
	}

	public Exception getDisconnectCause() {
		return disconnectCause;
	}

	public boolean isKick() {
		return kick;
	}

	public boolean isBan() {
		return ban;
	}

	public String getKickReason() {
		return kickReason;
	}

	public boolean isServerLookupUpdate() {
		return serverLookupUpdate;
	}

	public List<? extends IUServerProperties> getServers() {
		return servers;
	}

}

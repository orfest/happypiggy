package ru.nsu.ccfit.pm.econ.mock.controller.clientnet;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gag.annotation.remark.Facepalm;
import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.clientnet.IClientNetworkController;
import ru.nsu.ccfit.pm.econ.common.net.IUServerProperties;
import ru.nsu.ccfit.pm.econ.common.view.client.INetworkEvents;

/**
 * Replacement for ClientNetworkController.
 * @author dragonfly
 */
@Facepalm("Though mock objects should be dumb and dirty")
public class ClientNetworkControllerNoNet implements IClientNetworkController {
	
	static final Logger logger = LoggerFactory.getLogger(ClientNetworkControllerNoNet.class);
	static final double CONNECTION_PROBABILITY = 0.8;
	static final int CONNECT_DELAY = 2000;
	static final int UPDATE_PERIOD = 3000;
	
	/*
	 * Injectables.
	 */
	private INetworkEvents client;
	
	private int maxAutoReconnectAttempts = 0;
	private String playerName;
	private String playerGroup;
	private InetSocketAddress serverAddress;
	private boolean connected = false;
	
	private Timer connectTimer = new Timer(true);
	private Timer updateTimer = new Timer(true);

	@Override
	public void connect(InetSocketAddress serverAddress, String playerName,	String playerGroup) {
		logger.debug("Connecting to {} with {}/{}", new Object[]{serverAddress, playerName, playerGroup});
		
		this.playerName = playerName;
		this.playerGroup = playerGroup;
		this.serverAddress = serverAddress;
		
		connectTimer.schedule(new ConnectResultTask(), CONNECT_DELAY);
	}

	@Override
	public void disconnect() {
		logger.debug("Disconnecting");

		connectTimer.cancel();
		connectTimer = new Timer(true);
		connected = false;
		client.onDisconnect(null);
	}

	@Override
	public int getMaxAutoReconnectAttempts() {
		return maxAutoReconnectAttempts;
	}

	@Override
	public void init(boolean searchForServers) {
		logger.debug("Init, searchForServers = {}", searchForServers);

		if (searchForServers) {
			updateTimer.schedule(new UpdateServerListTask(), 0, UPDATE_PERIOD);
			client.onServerLookupStart();
		}
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public void reconnect() {
		logger.debug("Reconnecting");

		if (isConnected()) {
			disconnect();
		}
		connect(serverAddress, playerName, playerGroup);
	}

	@Override
	public void reconnect(String playerName, String playerGroup) {
		this.playerName = playerName;
		this.playerGroup = playerGroup;
		reconnect();
	}

	@Override
	public void setMaxAutoReconnectAttempts(int maxNumberOfAttempts) {
		maxAutoReconnectAttempts = maxNumberOfAttempts;
	}
	
	@Inject
	public void setClientNetworkEvents(INetworkEvents networkEvents) {
		client = networkEvents;
	}
	
	private void doConnect() {
		if (updateTimer != null) {
			updateTimer.cancel();
			updateTimer = null;
			client.onServerLookupStop();
		}
		
		connected = true;
		client.onConnect();
	}
	
	private void doConnectFailed() {
		connected = false;
		client.onDisconnect(null);
	}
	
	private class ConnectResultTask extends TimerTask {
		@Override
		public void run() {
			if (Math.random() < CONNECTION_PROBABILITY) {
				doConnect();
			} else {
				doConnectFailed();
			}
		}
	}
	
	private class UpdateServerListTask extends TimerTask {
		private Vector<ServerProperties> serverList = new Vector<ServerProperties>();
		@Override
		public void run() {
			double rand = Math.random();
			
			try {
				if (rand < 0.5) {
					int pos = (int)Math.floor(Math.random() * (serverList.size() + 1));
					serverList.add(pos, randomServer());
				} else if (rand < 0.7) {
					int pos = (int)Math.floor(Math.random() * serverList.size());
					serverList.set(pos, randomServer());
				} else {
					int pos = (int)Math.floor(Math.random() * serverList.size());
					serverList.remove(pos);
				}
			} catch (Exception e) {
				logger.debug("UpdateServerListTask.run() exception: {}", e.getMessage());
			}
			
			client.onServerLookupUpdate(serverList);
		}
		
		private ServerProperties randomServer() {
			ServerProperties sp = new ServerProperties();
			sp.address = new InetSocketAddress(
					"10.3.61." + (int)Math.floor(Math.random() * 255), 
					(int)Math.floor(Math.random() * 65536));
			sp.gameSessionName = "Game server #" + sp.address.hashCode();
			return sp;
		}
	}
	
	private class ServerProperties implements IUServerProperties {
		
		InetSocketAddress address;
		String gameSessionName;

		@Override
		public InetSocketAddress getAddress() {
			return address;
		}

		@Override
		public String getGameSessionName() {
			return gameSessionName;
		}
		
	}

}

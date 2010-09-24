package ru.nsu.ccfit.pm.econ.controller.clientnet;

import java.net.InetSocketAddress;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.clientnet.IClientNetworkController;
import ru.nsu.ccfit.pm.econ.common.controller.clientnet.INetworkEvents;
import ru.nsu.ccfit.pm.econ.common.net.INetworkClient;
import ru.nsu.ccfit.pm.econ.common.net.IUServerProperties;

/**
 * @author orfest
 * 
 */
public class ClientNetworkController implements IClientNetworkController, INetworkEvents {

	private static Logger logger = LoggerFactory.getLogger(ClientNetworkController.class);
	private INetworkClient networkClient = null;

	private InetSocketAddress serverAddress = null;
	private PersonDescription personDescription = null;
	private int maxAutoReconnectAttempts = 0;
	//private int failedReconnectAttempts = 0;
	private boolean lookingForServers = false;
	private boolean shouldReconnect = false;
	private ru.nsu.ccfit.pm.econ.common.view.client.INetworkEvents viewNetworkEvents;

	@Override
	public void connect(InetSocketAddress serverAddress_, String playerName, String playerGroup) {
		serverAddress = serverAddress_;
		personDescription = new PersonDescription(playerName, playerGroup);
		networkClient.connect(serverAddress, personDescription);
	}

	@Override
	public void disconnect() {
		networkClient.disconnect();
	}

	@Override
	public int getMaxAutoReconnectAttempts() {
		return maxAutoReconnectAttempts;
	}

	@Override
	public void init(boolean searchForServers) {
		if (lookingForServers != searchForServers) {
			lookingForServers = searchForServers;
			if (lookingForServers) {
				viewNetworkEvents.onServerLookupStart();
				networkClient.startServerLookup();
			} else {
				viewNetworkEvents.onServerLookupStop();
				networkClient.stopServerLookup();
			}
		}
	}

	@Override
	public boolean isConnected() {
		return networkClient.isConnected();
	}

	@Override
	public void reconnect() {
		if (personDescription == null)
			return;
		reconnect(personDescription.getName(), personDescription.getGroup());
	}

	@Override
	public void reconnect(String playerName, String playerGroup) {
		shouldReconnect = true;
		PersonDescription givenPersonDescription = new PersonDescription(playerName, playerGroup);
		if (personDescription == null || !personDescription.equals(givenPersonDescription)) {
			personDescription = givenPersonDescription;
			networkClient.disconnect();
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			networkClient.connect(serverAddress, personDescription);
		} else {
			networkClient.reconnect();
		}
	}

	@Override
	public void setMaxAutoReconnectAttempts(int maxNumberOfAttempts) {
		maxAutoReconnectAttempts = maxNumberOfAttempts;
	}

	@Inject
	public void setNetworkClient(INetworkClient client) {
		networkClient = client;
	}

	@Override
	public void onConnect() {
		logger.info("Client Connected!!");
		shouldReconnect = true;
		viewNetworkEvents.onConnect();
	}

	@Override
	public void onDisconnect(Exception cause) {
		if (shouldReconnect){
			reconnect();
		}
		viewNetworkEvents.onDisconnect(cause);
	}

	@Override
	public void onKick(String reason, boolean isBanned) {
		shouldReconnect = false;
		if (isBanned){
			viewNetworkEvents.onBan(reason);
		} else {
			viewNetworkEvents.onKick(reason);
		}
	}

	@Override
	public void onServerLookupUpdate(List<? extends IUServerProperties> serverList) {
		viewNetworkEvents.onServerLookupUpdate(serverList);
	}

	@Inject
	public void setViewNetworkEvents(ru.nsu.ccfit.pm.econ.common.view.client.INetworkEvents viewNetworkEvents) {
		this.viewNetworkEvents = viewNetworkEvents;
	}
	
}

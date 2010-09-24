package ru.nsu.ccfit.pm.econ.controller.servernet;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

import ru.nsu.ccfit.pm.econ.common.controller.servernet.IPlayerNetworkPresence;
import ru.nsu.ccfit.pm.econ.common.controller.servernet.IServerNetworkController;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameProperties;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.net.INetworkServer;
import ru.nsu.ccfit.pm.econ.common.net.IPlayerNetworkOperations;
import ru.nsu.ccfit.pm.econ.common.view.server.IPlayerRoster;
import ru.nsu.ccfit.pm.econ.common.view.server.IServerLifecycleNetworkEvents;
import ru.nsu.ccfit.pm.econ.net.engine.roles.PersonDescription;

import com.google.gag.annotation.remark.FTW;
import com.google.inject.Inject;

/**
 * @author orfest
 * 
 */
public class ServerNetworkController implements IServerNetworkController, IPlayerNetworkPresence {

	/*
	 * Injectables.
	 */
	private IServerLifecycleNetworkEvents serverLifecycleNetworkEvents;
	private IPlayerNetworkOperations playerNetworkOperations;
	private IPlayerRoster playerRoster;
	private INetworkServer networkServer = null;

	// coz, it has == overrided
	@FTW
	private Set<PersonDescription> networkPresence = new HashSet<PersonDescription>();
	private boolean autolocateEnabled = true;
	private String gameSessionName = null;
	private boolean running = false;
	private boolean autolocateSupportRunning = false;

	private IUGameProperties gamePropeties;

	@Override
	public void startServer() {
		startServer(null);
	}

	@Override
	public void startServer(InetSocketAddress bindAddress) {
		autolocateSupportRunning = autolocateEnabled;
		running = false;
		ServerControllerStarterThread starter = new ServerControllerStarterThread(serverLifecycleNetworkEvents,
				gamePropeties, autolocateEnabled, bindAddress, networkServer, this);
		Thread t = new Thread(starter);
		t.setDaemon(true);
		t.start();
	}

	void setRunning() {
		running = true;
	}

	@Override
	public void terminateAutolocateSupport() {
		autolocateSupportRunning = false;
		networkServer.terminateAutolocateSupport();
	}

	@Override
	public void terminateServer() {
		networkServer.terminateServer();
		autolocateSupportRunning = false;
		if (!running) return;
		running = false;
		serverLifecycleNetworkEvents.onServerTerminate();
	}

	@Override
	public String getGameSessionName() {
		return gameSessionName;
	}

	@Override
	public void setAutolocateEnabled(boolean isEnabled) {
		autolocateEnabled = isEnabled;
	}

	@Override
	public void setGameProperties(IUGameProperties game) {
		gamePropeties = game;
	}

	@Override
	public boolean isAutolocateEnabled() {
		return autolocateEnabled;
	}

	@Override
	public boolean isAutolocationSupportRunning() {
		return autolocateSupportRunning;
	}

	@Override
	public boolean isServerRunning() {
		return running;
	}

	@Override
	public void banClient(IUPersonDescription playerData, String reason) {
		playerNetworkOperations.ban(playerData, reason);
	}

	@Override
	public void kickAllClients(String reason) {
		playerNetworkOperations.kickAll(reason);
	}

	@Override
	public void kickClient(IUPersonDescription playerData, String reason) {
		playerNetworkOperations.kick(playerData, reason);
	}

	@Override
	public void addPlayer(IUPersonDescription playerData) {
		networkPresence.add(new PersonDescription(playerData));
		playerRoster.addPlayer(playerData);
	}

	@Override
	public void removePlayer(IUPersonDescription playerData) {
		networkPresence.remove(new PersonDescription(playerData));
		playerRoster.removePlayer(playerData);
	}

	@Inject
	public void setNetworkServer(INetworkServer networkServer) {
		this.networkServer = networkServer;
	}

	@Inject
	public void setServerLifecycleNetworkEvents(IServerLifecycleNetworkEvents serverLifecycleNetworkEvents) {
		this.serverLifecycleNetworkEvents = serverLifecycleNetworkEvents;
	}

	@Inject
	public void setPlayerNetworkOperations(IPlayerNetworkOperations playerNetworkOperations) {
		this.playerNetworkOperations = playerNetworkOperations;
	}

	@Inject
	public void setPlayerRoster(IPlayerRoster playerRoster) {
		this.playerRoster = playerRoster;
	}

}

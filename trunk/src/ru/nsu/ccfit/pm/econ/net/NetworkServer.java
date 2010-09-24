/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.common.controller.servernet.IPlayerNetworkPresence;
import ru.nsu.ccfit.pm.econ.common.controller.servernet.ServerStartStatus;
import ru.nsu.ccfit.pm.econ.common.engine.IPlayerPresence;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameProperties;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.net.INetworkServer;
import ru.nsu.ccfit.pm.econ.common.net.IPlayerNetworkOperations;
import ru.nsu.ccfit.pm.econ.modules.names.InNetworking;
import ru.nsu.ccfit.pm.econ.net.engine.data.GameTime;
import ru.nsu.ccfit.pm.econ.net.engine.events.KickBanEvent;
import ru.nsu.ccfit.pm.econ.net.engine.roles.PersonDescription;
import ru.nsu.ccfit.pm.econ.net.engine.roles.Student;

import com.google.gag.annotation.disclaimer.BossMadeMeDoIt;
import com.google.inject.Inject;

/**
 * @author orfest
 * 
 */
public class NetworkServer implements INetworkServer, IGameEventHandler, IPlayerNetworkOperations, IServerCoordinator {

	private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

	private ServerBroadcaster broadcaster = null;
	private ServerAcceptor acceptor = null;

	private Map<Long, PersonDescription> personDescriptionById = new HashMap<Long, PersonDescription>();
	private Map<PersonDescription, Long> personIdByDescription = new HashMap<PersonDescription, Long>();

	private IUGameProperties gameProperties;

	private IPlayerPresence playerPresence;
	private IPlayerNetworkPresence playerNetworkPresence;
	private IGameEventHandler engine;
	// yes, there is concrete PersonDescription because it defines equality
	private Map<PersonDescription, ConnectionHandlerThread> handlerByDescription = new HashMap<PersonDescription, ConnectionHandlerThread>();

	private Set<PersonDescription> bannedPlayers = new HashSet<PersonDescription>();
	private boolean autolocateSupport;

	public NetworkServer() {
		broadcaster = new ServerBroadcaster();
		Thread tb = new Thread(broadcaster, "broadcaster");
		tb.setDaemon(true);
		tb.start();
		acceptor = new ServerAcceptor(this);
		Thread ta = new Thread(acceptor, "acceptor");
		ta.setDaemon(true);
		ta.start();
	}

	@Override
	public List<InetSocketAddress> startServer(IUGameProperties game, boolean supportAutolocate,
			InetSocketAddress bindAddr) throws SocketException {
		if (bindAddr == null) {
			try {
				//bindAddr = new InetSocketAddress(InetAddress.getByName("10.3.60.112"), 0);
				//bindAddr = new InetSocketAddress(InetAddress.getByName(InetAddress.getLocalHost().getHostName()), 0);
				bindAddr = new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		gameProperties = game;
		autolocateSupport = supportAutolocate;

		List<InetSocketAddress> addresses = acceptor.startServer(bindAddr);
		if (addresses == null)
			return null;
		broadcaster.setActive(autolocateSupport);
		return addresses;
	}

	@Override
	public void terminateAutolocateSupport() {
		broadcaster.setActive(false);
	}

	@Override
	public void terminateServer() {
		acceptor.terminate();
		broadcaster.terminate();
		personDescriptionById.clear();
		personIdByDescription.clear();
		for (ConnectionHandlerThread t : handlerByDescription.values()) {
			t.terminate();
		}
		handlerByDescription.clear();
		bannedPlayers.clear();
	}

	@Override
	@BossMadeMeDoIt
	public String shouldPlayerBeAdded(IUPersonDescription uPlayerDescription) {
		PersonDescription playerDescription = new PersonDescription(uPlayerDescription);
		synchronized (this) {
			if (bannedPlayers.contains(playerDescription)) {
				return "You are banned!";
			}
			if (!personIdByDescription.containsKey(playerDescription)) {
				Student player = new Student();
				player.setUnmodifiablePersonDescription(playerDescription);
				try {
					long id = playerPresence.registerPlayer(player);
					playerNetworkPresence.addPlayer(uPlayerDescription);
					player.setId(id);
					personDescriptionById.put(id, playerDescription);
					personIdByDescription.put(playerDescription, id);
					return null; // ok
				} catch (IllegalArgumentException e) {
					return "Cannot be registered";
				}
			}
			ConnectionHandlerThread handler = handlerByDescription.get(playerDescription);
			if (handler == null) {
				logger.info("Troubles with handler");
				return "Already Connected";
			}
			if (handler.isConnectionAlive()) {
				return "Already Connected";
			}
			return null; // ok
		}
	}

	@Override
	public void gameEventArrived(IUGameEvent gameEvent) {
		engine.handleEvent(gameEvent);
	}

	@Override
	public void handleEvent(IUGameEvent event) {
		for (Long receiverId : event.getReceiverIds()) {
			synchronized (this) {

				if (!event.isBroadcast() && !personDescriptionById.containsKey(receiverId)) {
					continue;
				}

				IUPersonDescription player = personDescriptionById.get(receiverId);
				if (player == null)
					continue;
				ConnectionHandlerThread handler = handlerByDescription.get(player);
				if (handler == null)
					continue;
				handler.addEvent(event);
			}
		}
	}

	@Override
	public void playerConnected(IUPersonDescription uPlayerDescription, Socket socket) {
		PersonDescription playerDescription = new PersonDescription(uPlayerDescription);
		playerPresence.setPlayerPresence(personIdByDescription.get(playerDescription), true);
		synchronized (this) {
			if (handlerByDescription.containsKey(playerDescription)) {
				ConnectionHandlerThread handler = handlerByDescription.get(playerDescription);
				handler.resurrectConnection(socket);
			} else {
				ConnectionHandlerThread handler = new ConnectionHandlerThread(socket, this, uPlayerDescription);
				handlerByDescription.put(playerDescription, handler);
				Thread th = new Thread(handler, "ConnectionHandler");
				th.setDaemon(true);
				th.start();
			}
		}
	}

	@Override
	public void ban(IUPersonDescription playerData, String reason) {
		synchronized (this) {
			bannedPlayers.add(new PersonDescription(playerData));
		}
		doKick(new PersonDescription(playerData), reason, true);
	}

	@Override
	public void kick(IUPersonDescription playerData, String reason) {
		doKick(new PersonDescription(playerData), reason, false);
	}

	@Override
	public void kickAll(String reason) {
		for (PersonDescription pd : handlerByDescription.keySet()) {
			doKick(pd, reason, false);
		}
	}

	private void doKick(PersonDescription playerData, String reason, boolean banned) {
		KickBanEvent event = new KickBanEvent();
		event.setReason(reason);
		event.setBanned(banned);
		event.setSenderId(-1L);
		event.setBroadcast(false);
		GameTime gt = new GameTime();
		gt.setTime(new Date());
		gt.setTurnFinished(false);
		gt.setTurnNumber(-1);
		event.setEventTime(gt);

		long id = Long.MAX_VALUE;
		ConnectionHandlerThread handler = null;
		synchronized (this) {
			if (!personIdByDescription.containsKey(playerData))
				return;
			if (!handlerByDescription.containsKey(playerData))
				return;
			id = personIdByDescription.get(playerData);
			handler = handlerByDescription.get(playerData);
		}
		event.setReceiverIds(Arrays.asList(id));

		this.handleEvent(event);
		handler.kick();
	}

	@Override
	public void bindResult(ServerStartStatus status, InetSocketAddress bindAddress) {
		if (status == ServerStartStatus.OK) {
			broadcaster.setServerData(bindAddress.getHostName(), bindAddress.getPort(), gameProperties
					.getGameSessionName());
			broadcaster.setActive(autolocateSupport);
		} else {
		}
	}

	@Override
	public void clientDisconnected(IUPersonDescription playerData) {
		if (!personIdByDescription.containsKey(buildPersonDescription(playerData)))
			return;
		playerPresence.setPlayerPresence(personIdByDescription.get(buildPersonDescription(playerData)), false);
	}

	private PersonDescription buildPersonDescription(IUPersonDescription playerData) {
		if (playerData == null)
			return null;
		return new PersonDescription(playerData);
	}

	@Inject
	public void setEngine(@InNetworking IGameEventHandler engine) {
		this.engine = engine;
	}

	@Inject
	public void setPlayerPresence(IPlayerPresence playerPresence) {
		this.playerPresence = playerPresence;
	}

	@Inject
	public void setPlayerNetworkPresence(IPlayerNetworkPresence presence) {
		playerNetworkPresence = presence;
	}
}

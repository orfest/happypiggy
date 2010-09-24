package ru.nsu.ccfit.pm.econ.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gag.annotation.remark.Facepalm;

import ru.nsu.ccfit.pm.econ.common.controller.clientnet.INetworkEvents;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.net.protos.ConnectionMessagesProtos.ConnectionResponseProto;
import ru.nsu.ccfit.pm.econ.net.protos.ConnectionMessagesProtos.PersonDescriptionProto;

/**
 * @author orfest
 * 
 */
public class ClientNetworkThread implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(ClientNetworkThread.class);

	private boolean running = true;
	private LinkedBlockingQueue<ClientNetworkJob> jobQueue = new LinkedBlockingQueue<ClientNetworkJob>();

	private InetSocketAddress serverAddress = null;
	private IUPersonDescription playerData = null;
	private Socket socket = null;
	private INetworkEvents networkEvents = null;

	private ClientNetworkReceiverThread receiverThread = null;
	private IClientCoordinator coordinator;

	/**
	 * @param networkEvents_
	 * 
	 */
	public ClientNetworkThread(IClientCoordinator coordinator_) {
		coordinator = coordinator_;
		receiverThread = new ClientNetworkReceiverThread(coordinator);
		Thread th = new Thread(receiverThread, "Client receiver");
		th.setDaemon(true);
		th.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		for (; running;) {
			try {
				ClientNetworkJob job = jobQueue.take();
				if (job == null) {
					continue;
				}
				ClientNetworkJobType type = job.getType();
				if (type == ClientNetworkJobType.CONNECT) {
					doConnect(job.getAddress(), job.getPlayer());
				} else if (type == ClientNetworkJobType.DISCONNECT) {
					doDisconnect();
				} else if (type == ClientNetworkJobType.RECONNECT) {
					doReconnect();
				} else if (type == ClientNetworkJobType.SEND_EVENT) {
					doSend(job.getGameEvent());
				}
			} catch (InterruptedException e) {
				running = false;
			}
		}
	}

	private void doSend(IUGameEvent gameEvent) {
		if (!isConnected())
			return;
		try {
			GameEventsSerializer.getInstance().serializeGameEvent(gameEvent).writeDelimitedTo(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			doDisconnect();
		}
	}

	private PersonDescriptionProto createPersonDescription(IUPersonDescription playerData) {
		PersonDescriptionProto.Builder personDescription = PersonDescriptionProto.newBuilder();
		personDescription.setName(playerData.getName());
		personDescription.setGroup(playerData.getGroup());
		return personDescription.build();
	}

	private void doConnect(InetSocketAddress address, IUPersonDescription player) {
		// If client is already connected, nothing happens.
		if (isConnected()) {
			return;
		}
		logger.info("do connect");

		try {
			serverAddress = address;
			playerData = player;
			logger.info("connecting");
			Socket tmpSocket = new Socket(serverAddress.getAddress(), serverAddress.getPort());
			logger.info("connecting - ok");

			PersonDescriptionProto personDescription = createPersonDescription(playerData);
			logger.info("sending person description");
			personDescription.writeDelimitedTo(tmpSocket.getOutputStream());
			logger.info("sending person description - ok");
			logger.info("getting response");
			ConnectionResponseProto response = getServerConnectionResponse(tmpSocket.getInputStream());
			if (response == null) {
				networkEvents.onDisconnect(null);
				return;
			}
			logger.info("getting response - ok");
			if (response.getIsConnectionAccepted() == true) {
				logger.info("connectino accepted");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized (this) {
					socket = tmpSocket;
				}
				receiverThread.ressurectConnection(socket);
				networkEvents.onConnect();
				logger.info("onConnect called");
			} else {
				networkEvents.onDisconnect(new IllegalArgumentException(response.getReason()));
			}
		} catch (IOException e) {
			networkEvents.onDisconnect(e);
		}
	}

	private ConnectionResponseProto getServerConnectionResponse(InputStream inputStream) throws IOException {
		try {
			ConnectionResponseProto.Builder response = ConnectionResponseProto.newBuilder();
			response.mergeDelimitedFrom(inputStream);
			ConnectionResponseProto responseProto = response.build();
			return responseProto;
		} catch (Exception e) {
			doDisconnect();
			return null;
		}
	}

	private void doDisconnect() {
		// is client is not connected nothing happens
		if (!isConnected())
			return;
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket = null;
		networkEvents.onDisconnect(null);
	}

	private void doReconnect() {
		doDisconnect();
		if (serverAddress != null && playerData != null) {
			doConnect(serverAddress, playerData);
		}
	}

	public boolean isConnected() {
		synchronized (this) {
			return socket != null;
		}
	}

	public void addJob(ClientNetworkJob job) throws InterruptedException {
		jobQueue.put(job);
	}

	public void ressurectConnection(Socket s) {
		synchronized (this) {
			socket = s;
			receiverThread.ressurectConnection(s);
		}
	}

	/**
	 * @author orfest
	 * 
	 */
	@Facepalm
	public class ClientNetworkJob {

		private ClientNetworkJobType type;
		private InetSocketAddress address = null;
		private IUPersonDescription player = null;
		private IUGameEvent gameEvent = null;

		/**
		 * 
		 */
		public ClientNetworkJob(ClientNetworkJobType type_) {
			type = type_;
		}

		public ClientNetworkJob(ClientNetworkJobType type_, IUGameEvent gameEvent_) {
			type = type_;
			gameEvent = gameEvent_;
		}

		public ClientNetworkJob(ClientNetworkJobType type_, InetSocketAddress address_, IUPersonDescription player_) {
			type = type_;
			address = address_;
			player = player_;
		}

		public ClientNetworkJobType getType() {
			return type;
		}

		public InetSocketAddress getAddress() {
			return address;
		}

		public IUPersonDescription getPlayer() {
			return player;
		}

		public IUGameEvent getGameEvent() {
			return gameEvent;
		}

	}

	/**
	 * @author orfest
	 * 
	 */
	@Facepalm
	public enum ClientNetworkJobType {
		CONNECT, DISCONNECT, RECONNECT, SEND_EVENT
	}

	public void setNetworkEvents(INetworkEvents networkEvents) {
		logger.info("network events set");
		this.networkEvents = networkEvents;
	}

}

package ru.nsu.ccfit.pm.econ.net;

import java.io.IOException;
import java.net.Socket;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.GameEventProto;

/**
 * @author orfest
 * 
 */
public class ConnectionHandlerThread implements Runnable {

	private Socket socket = null;
	private IServerCoordinator coordinator = null;
	private boolean running = true;
	private IUPersonDescription playerData;

	private ConnectionSenderHandlerThread sender;

	public ConnectionHandlerThread(Socket socket_, IServerCoordinator coordinator_, IUPersonDescription playerData_) {
		socket = socket_;
		coordinator = coordinator_;
		playerData = playerData_;
		sender = new ConnectionSenderHandlerThread(coordinator, socket, playerData);
		Thread th = new Thread(sender, "Server sender");
		th.setDaemon(true);
		th.start();
	}

	public void run() {
		try {
			for (;;) {
				Socket s = null;
				synchronized (this) {
					if (!running)
						break;
					if (socket == null) {
						wait();
					}
					if (socket == null || running == false) {
						continue;
					}
					s = socket;
				}
				if (s != null && (!s.isConnected() || s.isClosed())) {
					connectionDied();
					continue;
				}
				try {
					IUGameEvent gameEvent = readGameEvent(s);
					if (gameEvent != null) {
						coordinator.gameEventArrived(gameEvent);
					}
				} catch (IOException e) {
					connectionDied();
				}
			}
		} catch (InterruptedException e) {
			connectionDied();
		}
	}

	private void connectionDied() {
		synchronized (this) {
			socket = null;
			notify();
		}
		coordinator.clientDisconnected(playerData);
	}

	private IUGameEvent readGameEvent(Socket s) throws IOException {
		if (s == null)
			return null;
		GameEventProto event = getGameEvent(s);
		if (event == null)
			return null;
		return GameEventsSerializer.getInstance().deserializeProto(event);
	}

	private GameEventProto getGameEvent(Socket s) throws IOException {
		try {
			GameEventProto.Builder eventBuilder = GameEventProto.newBuilder();
			eventBuilder.mergeDelimitedFrom(s.getInputStream());
			GameEventProto proto = eventBuilder.build();
			return proto;
		} catch (Exception e) {
			connectionDied();
			return null;
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public boolean isConnectionAlive() {
		synchronized (this) {
			return (socket != null);
		}
	}

	public void resurrectConnection(Socket socket_) {
		synchronized (this) {
			socket = socket_;
			sender.ressurectConnection(socket);
			notify();
		}
	}

	public void addEvent(IUGameEvent event) {
		sender.addEvent(event);
	}

	public void terminate() {
		synchronized (this) {
			running = false;
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
			} finally {
				socket = null;
			}
			notify();
		}
		sender.terminate();
	}

	public void kick() {
		sender.kick();
	}

}

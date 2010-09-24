/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.GameEventProto;

/**
 * @author orfest
 * 
 */
public class ConnectionSenderHandlerThread implements Runnable {

	private Socket socket = null;
	private boolean running = true;
	private LinkedList<ConnectionSenderHandlerJob> eventQueue = new LinkedList<ConnectionSenderHandlerJob>();
	private IServerCoordinator coordinator = null;
	private IUPersonDescription playerData;

	/**
	 * @param connectionHandlerThread
	 * @param socket
	 * 
	 */
	public ConnectionSenderHandlerThread(IServerCoordinator coordinator_, Socket socket_, IUPersonDescription playerData_ ) {
		socket = socket_;
		coordinator = coordinator_;
		playerData = playerData_;
	}

	@Override
	public void run() {
		try {
			for (;;) {
				ConnectionSenderHandlerJob job = null;
				Socket s = null;
				synchronized (this) {
					if (!running) {
						break;
					}
					if (socket == null || eventQueue.isEmpty()) {
						wait();
					}
					if (eventQueue.isEmpty() || socket == null || !running) {
						continue;
					}
					job = eventQueue.pollFirst();
					s = socket;
				}
				if (job.getType() == ConnectionSenderHandlerJobType.KICK){
					doKick(s);
				} else if (job.getType() == ConnectionSenderHandlerJobType.EVENT){
					doSendEvent(job.getEvent(),s);
				}
			}
		} catch (InterruptedException e) {
		}
	}

	private void doSendEvent(IUGameEvent event, Socket s) {
		if (!sendEvent(event, s)) {
			synchronized (this) {
				eventQueue.addFirst(new ConnectionSenderHandlerJob(event));
			}
		}
	}

	private void doKick(Socket s) {
		if (s != null){
			try {
				s.close();
			} catch (IOException e) {
			}
		}
		connectionDied();
	}

	private boolean sendEvent(IUGameEvent event, Socket s) {
		if (s == null)
			return false;
		GameEventProto proto = getEventProto(event);
		try {
			proto.writeDelimitedTo(s.getOutputStream());
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private GameEventProto getEventProto(IUGameEvent event) {
		return GameEventsSerializer.getInstance().serializeGameEvent(event);
	}

	public void ressurectConnection(Socket socket_) {
		synchronized (this) {
			socket = socket_;
			notify();
		}
	}

	public void addEvent(IUGameEvent event) {
		synchronized (this) {
			eventQueue.add(new ConnectionSenderHandlerJob(event));
			notify();
		}
	}

	public void connectionDied() {
		synchronized (this) {
			socket = null;
			notify();
		}
		coordinator.clientDisconnected(playerData);
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
	}

	public void kick() {
		synchronized (this) {
			eventQueue.add(new ConnectionSenderHandlerJob(ConnectionSenderHandlerJobType.KICK));
		}
	}

	private enum ConnectionSenderHandlerJobType {
		EVENT, KICK
	}

	private class ConnectionSenderHandlerJob {
		private ConnectionSenderHandlerJobType type;
		private IUGameEvent event = null;

		public ConnectionSenderHandlerJob(IUGameEvent event_) {
			event = event_;
			type = ConnectionSenderHandlerJobType.EVENT;
		}

		public ConnectionSenderHandlerJob(ConnectionSenderHandlerJobType type_) {
			type = type_;
		}

		public ConnectionSenderHandlerJobType getType() {
			return type;
		}

		public IUGameEvent getEvent() {
			return event;
		}
	}
}

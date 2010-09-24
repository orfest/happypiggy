/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net;

import java.io.IOException;
import java.net.Socket;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.GameEventProto;

/**
 * @author orfest
 * 
 */
public class ClientNetworkReceiverThread implements Runnable {

	private Socket socket = null;
	private boolean running = true;
	private IClientCoordinator coordinator;

	/**
	 * @param clientNetworkThread
	 * 
	 */
	public ClientNetworkReceiverThread(IClientCoordinator coordinator_) {
		coordinator = coordinator_;
	}

	@Override
	public void run() {
		try {
			for (;;) {
				Socket s = null;
				synchronized (this) {
					if (!running)
						break;
					if (socket == null) {
						wait();
						continue;
					}
					s = socket;
				}
				try {
					IUGameEvent event = readGameEvent(s);
					if (event == null)
						continue;
					coordinator.eventArrived(event);
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
			if (socket != null){
				try {
					socket.close();
				} catch (IOException e) {
				}
				socket = null;
			}
			notify();			
		}

	}

	private IUGameEvent readGameEvent(Socket s) throws IOException {
		if (s == null) return null;
		GameEventProto event = getGameEvent(s);
		return GameEventsSerializer.getInstance().deserializeProto(event);
	}

	private GameEventProto getGameEvent(Socket s) throws IOException {
		GameEventProto.Builder eventBuilder = GameEventProto.newBuilder();
		eventBuilder.mergeDelimitedFrom(s.getInputStream());
		if (!eventBuilder.isInitialized()) return null;
		return eventBuilder.build();
	}
	
	public void ressurectConnection(Socket s){
		synchronized (this) {
			socket = s;
			notify();
		}
	}
}

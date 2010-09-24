/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net;

import java.io.IOException;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.controller.servernet.ServerStartStatus;

/**
 * @author orfest
 * 
 */
public class ServerAcceptor implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(ServerAcceptor.class);

	private static final int BACK_LOG = 1024;
	private boolean running = true;
	private boolean active = false;
	private ServerSocket serverSocket = null;
	private IServerCoordinator coordinator = null;

	/**
	 * 
	 */
	public ServerAcceptor(IServerCoordinator coordinator_) {
		coordinator = coordinator_;
	}

	public void run() {
		try {
			for (;;) {
				synchronized (this) {
					if (!running) {
						break;
					}
					if (!active) {
						wait();
						continue;
					}
				}
				logger.info("accepting connection");
				Socket socket = serverSocket.accept();
				logger.info("accepted connection");
				PlayerInfoReceiver playerInfoReceiver = new PlayerInfoReceiver(socket, coordinator);
				Thread receiverThread = new Thread(playerInfoReceiver, "PlayerInfoReceiver");
				receiverThread.setDaemon(true);
				receiverThread.start();

			}
		} catch (Exception e) {
			logger.info("serversocket closed unexpectedly");
			synchronized (this) {
				running = false;
			}
		}
	}

	public List<InetSocketAddress> startServer(InetSocketAddress bindAddr) throws SocketException {
		logger.info("starting");
		synchronized (this) {
			if (active) {
				doTerminate();
			}
			try {
				serverSocket = new ServerSocket(bindAddr.getPort(), BACK_LOG, bindAddr.getAddress());
				List<InetSocketAddress> addresses = new LinkedList<InetSocketAddress>();
				List<InetSocketAddress> p2pAddresses = new LinkedList<InetSocketAddress>();
				int port = serverSocket.getLocalPort();
				for (NetworkInterface interf : Collections.list(NetworkInterface.getNetworkInterfaces())) {
					if (interf.isLoopback())
						continue;
					if (!interf.isUp())
						continue;
					List<InetSocketAddress> list = (interf.isPointToPoint() ? p2pAddresses : addresses);
					for (InetAddress interfAddr : Collections.list(interf.getInetAddresses())) {
						if (interfAddr instanceof Inet4Address){
							list.add(new InetSocketAddress(interfAddr,port));
							break;
						}
					}
				}
				addresses.addAll(p2pAddresses);
				if (addresses.isEmpty()) {
					addresses.add(new InetSocketAddress(InetAddress.getLocalHost(), port));
				}
				active = true;
				notify();
				coordinator.bindResult(ServerStartStatus.OK, addresses.get(0));
				return addresses;
			} catch (IOException e) {
				logger.info("bind failed");
				coordinator.bindResult(ServerStartStatus.ACCESS_DENIED, null);
				throw new SocketException(e.getMessage());
			}
		}
	}

	public void terminate() {
		logger.info("terminate");
		synchronized (this) {
			doTerminate();
		}

	}

	// the monitor must be acquired
	private void doTerminate() {
		active = false;
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
		} finally {
			serverSocket = null;
		}
	}

}

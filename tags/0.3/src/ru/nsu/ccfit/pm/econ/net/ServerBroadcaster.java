/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

import ru.nsu.ccfit.pm.econ.net.protos.ConnectionMessagesProtos.BroadcastMessageProto;

/**
 * @author orfest
 * 
 */
public class ServerBroadcaster implements Runnable {

	private static final long DELAY = 200; // 1 sec
	private boolean running = true;
	private boolean active = false;
	private InetAddress group = null;
	private MulticastSocket socket = null;
	private String hostname = null;
	private int serverPort = Integer.MAX_VALUE;
	private String gameSessionName = null;

	public ServerBroadcaster() {
	}

	public void setActive(boolean active_) {
		synchronized (this) {
			active = active_;
			this.notifyAll();
		}
	}

	public void run() {
		try {
			group = InetAddress
					.getByName(AutolocationConstants.BROADCAST_GROUP);
			socket = new MulticastSocket(AutolocationConstants.BROADCAST_PORT);
			socket.joinGroup(group);

			for (;;) {
				synchronized (this) {
					if (!running)
						break;
					if (!active) {
						this.wait();
						continue;
					}
					sendOut();
				}
				Thread.sleep(DELAY);
			}
			socket.leaveGroup(group);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}
	}

	private void sendOut() throws IOException {
		if (serverPort == Integer.MAX_VALUE || gameSessionName == null)
			return;

		BroadcastMessageProto message = getBroadcastMessage();

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		message.writeTo(stream);
		byte[] buffer = stream.toByteArray();

		if (buffer.length > AutolocationConstants.BROADCAST_BUFFER_SIZE) {
			// too long, should be truncated
			byte[] tmpBuffer = new byte[AutolocationConstants.BROADCAST_BUFFER_SIZE];
			Arrays.copyOf(buffer, tmpBuffer.length);
			buffer = tmpBuffer;
		}

		DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
				group, AutolocationConstants.BROADCAST_PORT);

		socket.send(packet);
	}

	private BroadcastMessageProto getBroadcastMessage() {
		BroadcastMessageProto.Builder message = BroadcastMessageProto
				.newBuilder();
		message.setPort(serverPort);
		message.setHostname(hostname);
		message.setGameSessionName(gameSessionName);
		return message.build();
	}

	public void setServerData(String hostname_, int serverPort_, String gameSessionName_) {
		synchronized (this) {
			hostname = hostname_;
			serverPort = serverPort_;
			gameSessionName = gameSessionName_;
		}
	}

	public void terminate() {
		synchronized (this) {
			active = false;
			this.notifyAll();
		}
	}

}

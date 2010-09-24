package ru.nsu.ccfit.pm.econ.net;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ru.nsu.ccfit.pm.econ.common.controller.clientnet.INetworkEvents;
import ru.nsu.ccfit.pm.econ.common.net.IUServerProperties;
import ru.nsu.ccfit.pm.econ.net.protos.ConnectionMessagesProtos.BroadcastMessageProto;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * These objects live in a separate thread and listening to server broadcasts,
 * autolocate them all.
 * 
 * @author orfest
 */
public class ServerLookuper implements Runnable {

	/**
	 * @author orfest
	 *
	 */
	public class ServerLongPair {

		private Long time;
		private IUServerProperties properties;

		/**
		 * 
		 */
		public ServerLongPair() {
		}

		public ServerLongPair(IUServerProperties properties_, Long time_) {
			properties = properties_;
			time = time_;
		}

		public Long getTime() {
			return time;
		}

		public IUServerProperties getProperties() {
			return properties;
		}
	}

	private static final long FORGET_SERVER_THRESHOLD = 10000;

	private INetworkEvents networkEvents = null;
	private boolean running = true; 
	private boolean active = false;
	private MulticastSocket socket = null;
	private LinkedList<ServerLongPair> serversSeenLastTime = new LinkedList<ServerLongPair>();

	public ServerLookuper() {
	}

	@Override
	public void run() {
		try {
			byte[] buffer = new byte[AutolocationConstants.BROADCAST_BUFFER_SIZE];
			socket = new MulticastSocket(AutolocationConstants.BROADCAST_PORT);
			InetAddress group = InetAddress.getByName(AutolocationConstants.BROADCAST_GROUP);
			socket.joinGroup(group);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			for (;;) {
				synchronized (this) {
					if (!running) {
						break;
					}
					if (!active) {
						this.wait();
						continue;
					}
				}
				socket.receive(packet);
				IUServerProperties serverProperties = getServerProperties(packet);
				if (serverProperties == null) {
					continue;
				}
				synchronized (this) {
					if (!active) {
						continue;
					}
					boolean anythingChanged = updateFoundServers(serverProperties, System.currentTimeMillis());
					if (anythingChanged) {
						networkEvents.onServerLookupUpdate(getFoundServers());
					}
				}
			}
			socket.leaveGroup(group);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			socket.close();
			socket = null;
		}
	}

	private List<? extends IUServerProperties> getFoundServers() {
		List<IUServerProperties> list = new ArrayList<IUServerProperties>(serversSeenLastTime.size());
		for (ServerLongPair pair : serversSeenLastTime){
			list.add(pair.getProperties());
		}
		return list;
	}

	private boolean updateFoundServers(IUServerProperties serverProperties, long timeSeen) {
		
		boolean anythingChanged = true;
		for (ServerLongPair pair : serversSeenLastTime){
			if (pair.getProperties().equals(serverProperties)){
				anythingChanged = false;
			}
		}
		if (anythingChanged){
			serversSeenLastTime.add(new ServerLongPair(serverProperties, timeSeen));
		}
		Iterator<ServerLongPair> iterator = serversSeenLastTime.iterator();
		for (; iterator.hasNext();) {
			ServerLongPair pair = iterator.next();
			if (timeSeen - pair.getTime() > FORGET_SERVER_THRESHOLD) {
				iterator.remove();
				anythingChanged = true;
			}
		}
		return anythingChanged;
	}

	public void setActive(boolean active_) {
		synchronized (this) {
			if (active_ == active) {
				return;
			}
			active = active_;
			this.notifyAll();
		}
	}

	private IUServerProperties getServerProperties(DatagramPacket packet) throws UnknownHostException {
		BroadcastMessageProto message = getBroadcastMessage(packet);
		if (message == null)
			return null;
		return new ServerProperties(new InetSocketAddress(InetAddress.getByName(message.getHostname()) , message.getPort()), message.getGameSessionName());
	}

	private BroadcastMessageProto getBroadcastMessage(DatagramPacket packet) {
		try {
			BroadcastMessageProto.Builder message = BroadcastMessageProto.newBuilder();
			message.mergeFrom(getData(packet));
			return message.build();
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
			return null;
		}
	}

	private byte[] getData(DatagramPacket packet) {
		return Arrays.copyOfRange(packet.getData(), packet.getOffset(), packet.getOffset() + packet.getLength());
	}

	public void setNetworkEvents(INetworkEvents networkEvents) {
		this.networkEvents = networkEvents;
	}

}

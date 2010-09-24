package ru.nsu.ccfit.pm.econ.common.net;

import java.net.InetSocketAddress;

import ru.nsu.ccfit.pm.econ.common.controller.clientnet.INetworkEvents;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;

/**
 * Interface to control network client.
 * <p>Any events that occur during client operation are passed
 * via {@link INetworkEvents} interface.</p>
 * <p>Components that may use this interface:
 * <ul><li>ClientNetworkController</li></ul>
 * </p>
 * @author dragonfly
 */
public interface INetworkClient {
	
	/**
	 * Start search for servers in the local network. Any found servers are reported via
	 * {@link INetworkEvents#onServerLookupUpdate(java.util.List)}.
	 * <p>The search behaves as via UDP broadcast. If server doesn't support autolocate 
	 * feature, it will not be found using this method.</p>
	 * @see #stopServerLookup()
	 */
	void startServerLookup();
	
	/**
	 * Stop search for servers in the local network. 
	 * @see #startServerLookup()
	 */
	void stopServerLookup();
	
	/**
	 * Connect to the specified server.
	 * <p>If connection succeeded {@link INetworkEvents#onConnect()} is invoked. 
	 * Otherwise {@link INetworkEvents#onDisconnect(Exception)} is invoked.</p>
	 * <p>If client is already connected, nothing happens.</p>
	 * @param serverAddress Server address to connect to.
	 * @param playerData Description of the player that wants to connect. It is used 
	 * 						by server to distinguish between multiple clients.
	 * @see #disconnect()
	 * @see #reconnect()
	 * @see #isConnected()
	 */
	void connect(InetSocketAddress serverAddress, IUPersonDescription playerData);
	
	/**
	 * Disconnect from the server.
	 * <p>When client is actually disconnected 
	 * {@link INetworkEvents#onDisconnect(Exception)} is invoked.</p>
	 * <p>If client is not connected, nothing happens.</p>
	 * @see #connect(InetSocketAddress, IUPersonDescription)
	 */
	void disconnect();
	
	/**
	 * Reconnect to the most recent server.
	 * <p>Reconnect is effectively equivalent to first disconnecting and 
	 * then connecting to the most recent server with the same parameters 
	 * as upon initial connection.</p>
	 * <p>The most recent server is determined by the latest call to 
	 * {@link #connect(InetSocketAddress, IUPersonDescription)}.</p>
	 * @see #connect(InetSocketAddress, IUPersonDescription)
	 * @see #disconnect()
	 */
	void reconnect();
	
	/**
	 * @return <tt>true</tt> is client is currently connected to server,
	 * 			<tt>false</tt> otherwise.
	 */
	boolean isConnected();

}

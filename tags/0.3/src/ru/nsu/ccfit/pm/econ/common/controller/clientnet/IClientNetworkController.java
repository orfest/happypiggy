package ru.nsu.ccfit.pm.econ.common.controller.clientnet;

import java.net.InetSocketAddress;

import javax.annotation.Nullable;

import ru.nsu.ccfit.pm.econ.common.view.client.INetworkEvents;

/**
 * Primary interface of ClientNetworkController component. Used to control 
 * client network connections. Note that at any given time client may be 
 * connected to at most one game server.
 * <p>Components that may use this interface:
 * <ul><li>ClientUI</li></ul>
 * </p>
 * <p>This interface should be implemented by ClientNetworkController 
 * component.</p>
 * @see INetworkEvents
 * @author dragonfly
 */
public interface IClientNetworkController {
	
	/**
	 * Initialize client network subsystem. In particular, start automatic 
	 * server lookup.
	 * <p>Once automatic server lookup is started, it keeps running until
	 * client successfully connects to some server.</p>
	 * @param searchForServers <tt>true</tt> if automatic server lookup 
	 * 							should be started, <tt>false</tt> otherwise.
	 * @see INetworkEvents#onServerLookupStart()
	 * @see INetworkEvents#onServerLookupStop()
	 * @see INetworkEvents#onServerLookupUpdate(java.util.List) 
	 */
	void init(boolean searchForServers);
	
	/**
	 * Connect to selected server using certain login arguments (playerName, 
	 * playerGroup).
	 * <p>In case of successful connection automatic server lookup is 
	 * stopped (if there was any). In case connection fails disconnect 
	 * event is fired, without any attempts to reconnect. In case previously open 
	 * connection is terminated by calling {@link #disconnect()} on client 
	 * or as a result of kick or ban, disconnect, kick or ban event is fired 
	 * and no automatic attempts to reconnect are made. In other cases of 
	 * termination of previously open connection reconnects are attempted 
	 * automatically if appropriate value was set via 
	 * {@link #setMaxAutoReconnectAttempts(int)}, otherwise only disconnect event
	 * is fired. If client is already connected when calling this method,
	 * nothing happens (but log message would be useful).</p>
	 * @param serverAddress Server address to connect to.
	 * @param playerName Player name to pass to the server.
	 * @param playerGroup Optional player (student) group to pass to the server.
	 * @see INetworkEvents
	 * @see INetworkEvents#onConnect()
	 * @see INetworkEvents#onDisconnect(Exception)
	 * @see #reconnect()
	 */
	void connect(InetSocketAddress serverAddress, String playerName, @Nullable String playerGroup);
	
	/**
	 * Disconnect from server this client is currently connected to.
	 * <p>If client is not connected, nothing happens. Calling this 
	 * method effectively inhibits any automatic reconnect attempts 
	 * until next successful connection or call to {@link #reconnect()}.
	 * </p>
	 * <p>Upon disconnecting controller should fire disconnect event
	 * (see {@link INetworkEvents#onDisconnect(Exception)}).</p>
	 */
	void disconnect();
	
	/**
	 * Manually tells controller to reconnect to the latest server 
	 * using the same login arguments.
	 * @see #reconnect(String, String)
	 */
	void reconnect();
	
	/**
	 * Manually tells controller to reconnect to the latest server
	 * using new login arguments. If automatic reconnect is 
	 * enabled this call will also result in subsequent automatic 
	 * reconnect attempts, provided the need for them would arise.
	 * <p>If client is already connected, reconnect will first 
	 * disconnect it (firing reconnect event), and then connect 
	 * it to the same server with supplied login arguments. If 
	 * client is not connected, first reconnect attempt will behave 
	 * exactly as connect attempt. Further reconnect attempts will
	 * be preceded by firing of reconnect event until maximum 
	 * number of attempts is reached. When this happens and client 
	 * still fails to make a connection, a disconnect event is 
	 * fired with appropriate Exception argument.</p>
	 * @param playerName New player name.
	 * @param playerGroup New (optional) player (student) group.
	 * @see #connect(InetSocketAddress, String, String)
	 * @see INetworkEvents#onReconnectAttempt(int, Exception)
	 * @see INetworkEvents#onDisconnect(Exception)
	 */
	void reconnect(String playerName, @Nullable String playerGroup);
	
	/**
	 * Sets automatic reconnect policy (maximum number of automatic
	 * reconnect attempts).
	 * @param maxNumberOfAttempts Maximum number of automatic 
	 * 							reconnect attempts. If value n:
	 * 		<ul>
	 * 		<li><tt>n > 0</tt> - max number of attempts is <tt>n</tt>;</li>
	 * 		<li><tt>n == 0</tt> - number of attempts is not limited;</li>
	 * 		<li><tt>n < 0</tt> - automatic reconnect is disabled (default).</li>
	 * 		</ul>
	 */
	void setMaxAutoReconnectAttempts(int maxNumberOfAttempts);
	
	/**
	 * Returns automatic reconnect policy. See 
	 * {@link #setMaxAutoReconnectAttempts(int)} for details on return value
	 * meaning.
	 * @return Maximum number of automatic reconnect attempts, <tt>0</tt> if
	 * 			unlimited, or <tt>-1</tt> if automatic reconnect is disabled.
	 */
	int getMaxAutoReconnectAttempts();
	
	/**
	 * Tells whether client is connected to server or not.
	 * @return <tt>true</tt> if client is connected to server, <tt>false</tt>
	 * 			otherwise.
	 */
	boolean isConnected();

}

package ru.nsu.ccfit.pm.econ.common.controller.servernet;

import java.net.InetSocketAddress;

import javax.annotation.Nullable;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameProperties;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.net.INetworkServer;
import ru.nsu.ccfit.pm.econ.common.net.IPlayerNetworkOperations;
import ru.nsu.ccfit.pm.econ.common.view.server.IPlayerRoster;
import ru.nsu.ccfit.pm.econ.common.view.server.IServerLifecycleNetworkEvents;

/**
 * Primary interface of ServerNetworkController component. Used to control
 * server life cycle and client connections.
 * <p>Components that may use this interface:
 * <ul><li>ServerUI</li></ul>
 * </p>
 * <p>This interface should be implemented by ServerNetworkController 
 * component.</p> 
 * @see IPlayerRoster
 * @see IServerLifecycleNetworkEvents
 * @author dragonfly
 */
public interface IServerNetworkController {
	
	/**
	 * Enables or disables server autolocation support on server side.
	 * @param isEnabled <tt>true</tt> to enable autolocation support
	 * 					(default), <tt>false</tt> to disable it.
	 */
	void setAutolocateEnabled(boolean isEnabled);
	
	/**
	 * Server side server autolocation support status.
	 * @return <tt>true</tt> if autolocation is enabled, <tt>false</tt> 
	 * 			otherwise.
	 */
	boolean isAutolocateEnabled();
	
	/**
	 * Set game properties.
	 * @param game Game properties data object.
	 */
	void setGameProperties(IUGameProperties game);
	
	/**
	 * Game session name as defined by game properties.
	 * @return Short game name.
	 * @see IUGameProperties#getGameSessionName()
	 */
	String getGameSessionName();
	
	/**
	 * Initializes server start procedure. 
	 * @see #startServer(InetSocketAddress)
	 */
	void startServer();
	
	/**
	 * Initializes server start procedure. Caller may specify advised bind
	 * address to use for server.
	 * <p>Note that this method should not block and may return before 
	 * server is actually started.</p>
	 * <p>Before call to this method caller should set desired game properties
	 * and autolocation support status via {@link #setGameProperties(IUGameProperties)}
	 * and {@link #setAutolocateEnabled(boolean)}.</p>
	 * @param bindAddress Advised address to bind server socket to.
	 * @see INetworkServer#startServer(IUGameProperties, boolean, InetSocketAddress)
	 */
	void startServer(@Nullable InetSocketAddress bindAddress);
	
	/**
	 * Quits server side server autolocation support. Note that this does not
	 * change autolocation support status.
	 * <p>If server autolocation is not running, this method has no effect.</p>
	 */
	void terminateAutolocateSupport();
	
	/**
	 * Quits running server, including autolocation support.
	 * <p>If server in not running, this method has no effect.</p> 
	 */
	void terminateServer();
	
	/**
	 * Whether server is running or not.
	 * @return <tt>true</tt> if server is running, <tt>false</tt> otherwise.
	 */
	boolean isServerRunning();
	
	/**
	 * Whether server autolocation support is running or not.
	 * @return <tt>true</tt> if server autolocation is running, 
	 * 			<tt>false</tt> otherwise.
	 */
	boolean isAutolocationSupportRunning();
	
	/**
	 * Kicks specified client (identified by player data).
	 * <p>This method should not block.</p>
	 * @param playerData Player to kick.
	 * @param reason Text message explaining kick reason.
	 * @see IPlayerNetworkOperations#kick(IUPersonDescription, String)
	 */
	void kickClient(IUPersonDescription playerData, String reason);
	
	/**
	 * Kicks all currently connected clients.
	 * <p>This method should not block.</p>
	 * @param reason Text message explaining kick reason.
	 * @see IPlayerNetworkOperations#kickAll(String)
	 */
	void kickAllClients(String reason);
	
	/**
	 * Bans specified client (identified by player data).
	 * <p>This method should not block.</p>
	 * @param playerData Player to ban.
	 * @param reason Text message explaining kick reason.
	 * @see IPlayerNetworkOperations#ban(IUPersonDescription, String)
	 */
	void banClient(IUPersonDescription playerData, String reason);

}

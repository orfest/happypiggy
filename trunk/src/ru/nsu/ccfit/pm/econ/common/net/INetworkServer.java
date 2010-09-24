package ru.nsu.ccfit.pm.econ.common.net;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.List;

import javax.annotation.Nullable;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameProperties;

/**
 * Interface to control network server.
 * <p>Components that may use this interface:
 * <ul><li>ServerNetworkController</li></ul>
 * </p>
 * 
 * @author dragonfly
 */
public interface INetworkServer {
	
	/**
	 * Launches server.
	 * @param game Game properties. End players use this info to distinguish 
	 * 				between multiple servers or games.
	 * @param supportAutolocate Whether to support server autolocation feature.
	 * 				This is like enabling UDP broadcast. 
	 * @param bindAddr Advised address to bind to. May include ip or hostname and port.
	 * 				May be <tt>null</tt>. Note that server is not guaranteed to be 
	 * 				bound to the specified address even if it is valid. 
	 * @return List of addresses server is bound to. The first address is considered 
	 * 			to be usable by clients in local network (though it is not guaranteed 
	 * 			as well).
	 * @throws SocketException If server failed to launch due to socket error.
	 * @see IUGameProperties
	 */
	List<InetSocketAddress> startServer(IUGameProperties game, 
			boolean supportAutolocate, @Nullable InetSocketAddress bindAddr) throws SocketException;
	
	/**
	 * Terminates server-side server autolocation support. 
	 * <p>Normally this would be called when all clients are already connected, i.e. 
	 * upon game start.</p>
	 */
	void terminateAutolocateSupport();
	
	/**
	 * Terminates server. After this method is called server quits accepting new 
	 * connections and closes any open connections. Autolocation support is also
	 * terminated, if still active.
	 * <p>If server was not previously started, nothing happens.</p>
	 * <p>After completion of this method server should be able to start again,
	 * if requested via {@link #startServer(IUGameProperties, boolean, InetSocketAddress)}.</p>
	 */
	void terminateServer();

}

package ru.nsu.ccfit.pm.econ.common.controller.clientnet;

import java.util.List;

import javax.annotation.Nullable;

import ru.nsu.ccfit.pm.econ.common.net.INetworkClient;
import ru.nsu.ccfit.pm.econ.common.net.IUServerProperties;

/**
 * Interface with callbacks for network events.
 * <p>Components that may use this interface:
 * <ul><li>Networking</li></ul>
 * </p>
 * 
 * @see INetworkClient
 * @author dragonfly
 */
public interface INetworkEvents {
	
	/**
	 * Invoked when results of server lookup change. I.e. when some new server
	 * is found, or previously found server disappears. 
	 * @param serverList List of currently visible servers.
	 */
	void onServerLookupUpdate(List<? extends IUServerProperties> serverList);
	
	/**
	 * Invoked when client successfully connects or reconnects to the server.
	 */
	void onConnect();
	
	/**
	 * Invoked when client fails to connect to the server or connection to
	 * the server is lost. Also invoked when it is the client that initiates 
	 * disconnect. In the latter case <tt>cause</tt> is <tt>null</tt>. 
	 * @param cause Exception that caused the event. If no exception info is
	 * 				available or disconnect was initiated by client this 
	 * 				field is <tt>null</tt>.
	 */
	void onDisconnect(@Nullable Exception cause);
	
	/**
	 * Invoked when client is kicked or banned. In either case first 
	 * {@link #onKick(String, boolean)} is called, then 
	 * {@link #onDisconnect(Exception)} is called.
	 * @param reason Text message which explains kick or ban reason.
	 * @param isBanned Whether client was kicked or banned. In either 
	 * 					case controller should quit attempts to reconnect.
	 */
	void onKick(String reason, boolean isBanned);

}

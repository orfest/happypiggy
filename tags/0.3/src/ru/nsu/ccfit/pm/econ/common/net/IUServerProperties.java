package ru.nsu.ccfit.pm.econ.common.net;

import java.net.InetSocketAddress;

/**
 * Unmodifiable interface describing server (from the point of view of the client).
 * @author dragonfly
 */
public interface IUServerProperties {
	
	/**
	 * Server address (including ip or hostname and port number).
	 * @return Server address.
	 */
	InetSocketAddress getAddress();
	
	/**
	 * Short game description string.
	 * @return Game session name.
	 */
	String getGameSessionName();

}

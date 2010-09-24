package ru.nsu.ccfit.pm.econ.view.shared.data;

import java.net.InetSocketAddress;

import ru.nsu.ccfit.pm.econ.common.net.IUServerProperties;

/**
 * <tt>ServerProperties</tt> data object.
 * @see IUServerProperties
 * @author dragonfly
 */
public class ServerProperties implements IUServerProperties {
	
	private InetSocketAddress address;
	private String gameSessionName;
	
	public ServerProperties(IUServerProperties other) {
		this.address = other.getAddress();
		this.gameSessionName = other.getGameSessionName();
	}

	@Override
	public InetSocketAddress getAddress() {
		return address;
	}

	@Override
	public String getGameSessionName() {
		return gameSessionName;
	}

	public void setAddress(InetSocketAddress address) {
		this.address = address;
	}

	public void setGameSessionName(String gameSessionName) {
		this.gameSessionName = gameSessionName;
	}

	@Override
	public String toString() {
		return gameSessionName + " - " + address.toString();
	}

}

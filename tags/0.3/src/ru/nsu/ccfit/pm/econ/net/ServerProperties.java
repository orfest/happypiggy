package ru.nsu.ccfit.pm.econ.net;

import java.net.InetSocketAddress;

import ru.nsu.ccfit.pm.econ.common.net.IUServerProperties;

/**
 * @author orfest
 * 
 */
public class ServerProperties implements IUServerProperties {

	private InetSocketAddress serverAddress = null;
	private String gameSessionName = null;

	public ServerProperties(InetSocketAddress serverAddress_, String gameSessionName_) {
		serverAddress = serverAddress_;
		gameSessionName = gameSessionName_;
	}

	@Override
	public InetSocketAddress getAddress() {
		return serverAddress;
	}

	@Override
	public String getGameSessionName() {
		return gameSessionName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().isInstance(this)) {
			ServerProperties prop = (ServerProperties) obj;
			return getGameSessionName().equals(prop.getGameSessionName())
					&& getAddress().getAddress().equals(getAddress().getAddress())
					&& getAddress().getPort() == prop.getAddress().getPort();
		}
		return this.hashCode() == obj.hashCode();
	}
}

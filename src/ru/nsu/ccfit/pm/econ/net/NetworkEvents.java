package ru.nsu.ccfit.pm.econ.net;

import java.util.List;

import ru.nsu.ccfit.pm.econ.common.controller.clientnet.INetworkEvents;
import ru.nsu.ccfit.pm.econ.common.net.IUServerProperties;

/**
 * @author orfest
 * 
 */
public class NetworkEvents implements INetworkEvents {

	public NetworkEvents() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onConnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnect(Exception cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKick(String reason, boolean isBanned) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onServerLookupUpdate(
			List<? extends IUServerProperties> serverList) {
		// TODO Auto-generated method stub

	}

}

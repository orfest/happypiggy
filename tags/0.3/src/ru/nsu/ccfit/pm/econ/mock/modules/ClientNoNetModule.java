package ru.nsu.ccfit.pm.econ.mock.modules;

import ru.nsu.ccfit.pm.econ.common.controller.clientnet.IClientNetworkController;
import ru.nsu.ccfit.pm.econ.common.view.client.INetworkEvents;
import ru.nsu.ccfit.pm.econ.mock.controller.clientnet.ClientNetworkControllerNoNet;
import ru.nsu.ccfit.pm.econ.view.client.ClientNetworkControllerGateway;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * Substitutes client network subsystem with a weirdly acting mock stuff. 
 * @author dragonfly
 */
public class ClientNoNetModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IClientNetworkController.class).to(ClientNetworkControllerNoNet.class).in(Scopes.SINGLETON);
		bind(INetworkEvents.class).to(ClientNetworkControllerGateway.class).in(Scopes.SINGLETON);
	}

}

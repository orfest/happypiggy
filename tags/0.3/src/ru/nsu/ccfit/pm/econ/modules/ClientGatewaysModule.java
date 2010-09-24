package ru.nsu.ccfit.pm.econ.modules;

import ru.nsu.ccfit.pm.econ.view.client.ClientNetworkControllerGateway;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * Binds client views to it's gateway objects.
 * @author dragonfly
 */
public class ClientGatewaysModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new SharedGatewaysModule());
		
		bind(ClientNetworkControllerGateway.class).in(Scopes.SINGLETON);
	}

}

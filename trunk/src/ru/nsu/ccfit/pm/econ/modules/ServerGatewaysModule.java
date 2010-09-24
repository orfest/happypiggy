package ru.nsu.ccfit.pm.econ.modules;

import ru.nsu.ccfit.pm.econ.view.server.ServerNetworkControllerGateway;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * Binds server views to their's gateway objects.
 * @author dragonfly
 */
public class ServerGatewaysModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new SharedGatewaysModule());
		
		bind(ServerNetworkControllerGateway.class).in(Scopes.SINGLETON);
	}

}

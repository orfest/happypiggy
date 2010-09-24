package ru.nsu.ccfit.pm.econ.modules;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.common.controller.clientnet.IClientNetworkController;
import ru.nsu.ccfit.pm.econ.common.controller.clientnet.INetworkEvents;
import ru.nsu.ccfit.pm.econ.common.engine.IPlayerPresence;
import ru.nsu.ccfit.pm.econ.common.net.INetworkClient;
import ru.nsu.ccfit.pm.econ.controller.clientnet.ClientNetworkController;
import ru.nsu.ccfit.pm.econ.controller.player.GameEventGateway;
import ru.nsu.ccfit.pm.econ.modules.names.InNetworking;
import ru.nsu.ccfit.pm.econ.modules.names.InPlayerController;
import ru.nsu.ccfit.pm.econ.net.NetworkClient;
import ru.nsu.ccfit.pm.econ.view.client.ClientNetworkControllerGateway;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.util.Providers;

/**
 * Binds together client components.
 * @author dragonfly
 */
public class ClientModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new ClientGatewaysModule());
		install(new PlayerControllerModule(false));
		
		// PlayerController <-> Networking
		bind(IGameEventHandler.class)
				.annotatedWith(InPlayerController.class)
				.to(NetworkClient.class);
		bind(IGameEventHandler.class)
				.annotatedWith(InNetworking.class)
				.to(GameEventGateway.class);
		
		// ClientNetworkController <-> Networking
		bind(INetworkEvents.class)
				.annotatedWith(InNetworking.class)
				.to(ClientNetworkController.class);
		bind(INetworkClient.class)
				.to(NetworkClient.class);
		
		// ClientNetworkController <-> ClientUI
		bind(ru.nsu.ccfit.pm.econ.common.view.client.INetworkEvents.class)
				.to(ClientNetworkControllerGateway.class);
		bind(IClientNetworkController.class)
				.to(ClientNetworkController.class);
		
		// PlayerController <-> ClientUI
		// already configured in PlayerControllerModule.configureGateway()
		
		configureDummies();
		singletoniateEmUp();
	}
	
	private void configureDummies() {
		bind(IPlayerPresence.class)
				.toProvider(Providers.of((IPlayerPresence)null));
	}
	
	private void singletoniateEmUp() {
		bind(NetworkClient.class).in(Scopes.SINGLETON);
		bind(ClientNetworkController.class).in(Scopes.SINGLETON);
	}

}

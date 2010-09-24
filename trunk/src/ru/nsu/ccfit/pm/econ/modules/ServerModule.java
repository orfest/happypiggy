package ru.nsu.ccfit.pm.econ.modules;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.ICompanyMessagePublisher;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.IScenarioController;
import ru.nsu.ccfit.pm.econ.common.controller.servernet.IPlayerNetworkPresence;
import ru.nsu.ccfit.pm.econ.common.controller.servernet.IServerNetworkController;
import ru.nsu.ccfit.pm.econ.common.engine.IPlayerPresence;
import ru.nsu.ccfit.pm.econ.common.engine.IScenarioPort;
import ru.nsu.ccfit.pm.econ.common.net.INetworkServer;
import ru.nsu.ccfit.pm.econ.common.net.IPlayerNetworkOperations;
import ru.nsu.ccfit.pm.econ.common.view.server.IPlayerRoster;
import ru.nsu.ccfit.pm.econ.common.view.server.IServerLifecycleNetworkEvents;
import ru.nsu.ccfit.pm.econ.controller.player.GameEventGateway;
import ru.nsu.ccfit.pm.econ.controller.scenario.ScenarioController;
import ru.nsu.ccfit.pm.econ.controller.servernet.ServerNetworkController;
import ru.nsu.ccfit.pm.econ.engine.NetworkingPort;
import ru.nsu.ccfit.pm.econ.engine.PlayerControllerPort;
import ru.nsu.ccfit.pm.econ.engine.PlayerPresenceEngine;
import ru.nsu.ccfit.pm.econ.engine.ScenarioControllerPort;
import ru.nsu.ccfit.pm.econ.engine.ScenarioPortEngine;
import ru.nsu.ccfit.pm.econ.modules.names.InNetworking;
import ru.nsu.ccfit.pm.econ.modules.names.InPlayerController;
import ru.nsu.ccfit.pm.econ.modules.names.InScenarioController;
import ru.nsu.ccfit.pm.econ.modules.names.ToNetworking;
import ru.nsu.ccfit.pm.econ.modules.names.ToPlayerController;
import ru.nsu.ccfit.pm.econ.modules.names.ToScenarioController;
import ru.nsu.ccfit.pm.econ.net.NetworkServer;
import ru.nsu.ccfit.pm.econ.view.server.ServerNetworkControllerGateway;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * Binds together server components.
 * @author dragonfly
 */
public class ServerModule extends AbstractModule {
	
	@Override
	protected void configure() {
		install(new ServerGatewaysModule());
		install(new PlayerControllerModule(true));
		install(new EngineModule());
		
		// PlayerController <-> Engine
		bind(IGameEventHandler.class)
				.annotatedWith(ToPlayerController.class)
				.to(GameEventGateway.class);
		bind(IGameEventHandler.class)
				.annotatedWith(InPlayerController.class)
				.to(PlayerControllerPort.class);
		
		// ScenarioController <-> Engine
		bind(IGameEventHandler.class)
				.annotatedWith(ToScenarioController.class)
				.to(ScenarioController.class);
		bind(IScenarioPort.class)
				.to(ScenarioPortEngine.class);
		bind(IGameEventHandler.class)
				.annotatedWith(InScenarioController.class)
				.to(ScenarioControllerPort.class);
		
		// Networking <-> Engine
		bind(IGameEventHandler.class)
				.annotatedWith(ToNetworking.class)
				.to(NetworkServer.class);
		bind(IPlayerPresence.class)
				.to(PlayerPresenceEngine.class);
		bind(IGameEventHandler.class)
				.annotatedWith(InNetworking.class)
				.to(NetworkingPort.class);
		
		// ServerNetworkController <-> Networking
		bind(IPlayerNetworkPresence.class)
				.to(ServerNetworkController.class);
		bind(INetworkServer.class)
				.to(NetworkServer.class);
		bind(IPlayerNetworkOperations.class)
				.to(NetworkServer.class);
		
		// ServerNetworkController <-> ServerUI
		bind(IServerNetworkController.class)
				.to(ServerNetworkController.class);
		bind(IServerLifecycleNetworkEvents.class)
				.to(ServerNetworkControllerGateway.class);
		bind(IPlayerRoster.class)
				.to(ServerNetworkControllerGateway.class);
		
		// ScenarioController <-> ServerUI
		bind(IScenarioController.class)
				.to(ScenarioController.class);
		bind(ICompanyMessagePublisher.class)
				.to(ScenarioController.class);
		
		// PlayerController <-> ServerUI
		// already configured in PlayerControllerModule.configureGateway()
		
		singletoniateEmUp();
	}
	
	private void singletoniateEmUp() {
		bind(ScenarioController.class).in(Scopes.SINGLETON);
		bind(ScenarioPortEngine.class).in(Scopes.SINGLETON);
		bind(NetworkServer.class).in(Scopes.SINGLETON);
		bind(PlayerPresenceEngine.class).in(Scopes.SINGLETON);
		bind(ServerNetworkController.class).in(Scopes.SINGLETON);
	}

}

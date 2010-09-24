package ru.nsu.ccfit.pm.econ.view.server;

import java.net.InetSocketAddress;

import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.servernet.IServerNetworkController;
import ru.nsu.ccfit.pm.econ.common.controller.servernet.ServerStartStatus;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameProperties;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.view.server.IPlayerRoster;
import ru.nsu.ccfit.pm.econ.common.view.server.IServerLifecycleNetworkEvents;

/**
 * Gateway for view to interact with ServerNetworkController component.
 * @author dragonfly
 */
public class ServerNetworkControllerGateway 
		implements IPlayerRoster, IServerLifecycleNetworkEvents, IServerNetworkController {
	
	static final Logger logger = LoggerFactory.getLogger(ServerNetworkControllerGateway.class);
	
	protected PlayerConnectionListenerList pcListeners = new PlayerConnectionListenerList();
	protected ServerLifecycleListenerList slListeners = new ServerLifecycleListenerList();
	private IServerNetworkController controller;

	@Override
	public void addPlayer(final IUPersonDescription playerData) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				pcListeners.onPlayerConnect(playerData);
			}
		});
	}

	@Override
	public void removePlayer(final IUPersonDescription playerData) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				pcListeners.onPlayerDisconnect(playerData);
			}
		});
	}

	@Override
	public void onServerStart(final ServerStartStatus status, final InetSocketAddress bindAddress) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				slListeners.onServerStart(status, bindAddress);
			}
		});
	}

	@Override
	public void onServerStartFailed(final ServerStartStatus status) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				slListeners.onServerStartFailed(status);
			}
		});
	}

	@Override
	public void onServerTerminate() {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				slListeners.onServerTerminate();
			}
		});
	}

	@Override
	public void banClient(IUPersonDescription playerData, String reason) {
		controller.banClient(playerData, reason);
	}

	@Override
	public String getGameSessionName() {
		return controller.getGameSessionName();
	}

	@Override
	public boolean isAutolocateEnabled() {
		return controller.isAutolocateEnabled();
	}

	@Override
	public boolean isAutolocationSupportRunning() {
		return controller.isAutolocationSupportRunning();
	}

	@Override
	public boolean isServerRunning() {
		return controller.isServerRunning();
	}

	@Override
	public void kickAllClients(String reason) {
		controller.kickAllClients(reason);
	}

	@Override
	public void kickClient(IUPersonDescription playerData, String reason) {
		controller.kickClient(playerData, reason);
	}

	@Override
	public void setAutolocateEnabled(boolean isEnabled) {
		controller.setAutolocateEnabled(isEnabled);
	}

	@Override
	public void setGameProperties(IUGameProperties game) {
		controller.setGameProperties(game);
	}

	@Override
	public void startServer() {
		controller.startServer();
	}

	@Override
	public void startServer(InetSocketAddress bindAddress) {
		controller.startServer(bindAddress);
	}

	@Override
	public void terminateAutolocateSupport() {
		controller.terminateAutolocateSupport();
	}

	@Override
	public void terminateServer() {
		controller.terminateServer();
	}
	
	public ListenerList<IPlayerConnectionListener> getPlayerConnectionListeners() {
		return pcListeners;
	}

	public ListenerList<IServerLifecycleListener> getServerLifecycleListeners() {
		return slListeners;
	}

	@Inject
	public void setController(IServerNetworkController controller) {
		if (this.controller != null)
			logger.warn("Redefining controller");
		this.controller = controller;
	}
	
	protected static class PlayerConnectionListenerList
		extends ListenerList<IPlayerConnectionListener> implements IPlayerConnectionListener {

		@Override
		public void onPlayerConnect(IUPersonDescription playerData) {
			for (IPlayerConnectionListener listener : this)
				listener.onPlayerConnect(playerData);
		}

		@Override
		public void onPlayerDisconnect(IUPersonDescription playerData) {
			for (IPlayerConnectionListener listener : this)
				listener.onPlayerDisconnect(playerData);
		}
		
	}
	
	protected static class ServerLifecycleListenerList
		extends ListenerList<IServerLifecycleListener> implements IServerLifecycleListener {

		@Override
		public void onServerStart(ServerStartStatus status, InetSocketAddress bindAddress) {
			for (IServerLifecycleListener listener : this)
				listener.onServerStart(status, bindAddress);
		}

		@Override
		public void onServerStartFailed(ServerStartStatus status) {
			for (IServerLifecycleListener listener : this)
				listener.onServerStartFailed(status);
		}

		@Override
		public void onServerTerminate() {
			for (IServerLifecycleListener listener : this)
				listener.onServerTerminate();
		}
		
	}

}

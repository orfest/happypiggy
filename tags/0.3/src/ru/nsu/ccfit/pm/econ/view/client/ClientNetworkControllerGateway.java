package ru.nsu.ccfit.pm.econ.view.client;

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.clientnet.IClientNetworkController;
import ru.nsu.ccfit.pm.econ.common.net.IUServerProperties;
import ru.nsu.ccfit.pm.econ.common.view.client.INetworkEvents;

/**
 * Gateway for view to interact with ClientNetworkController component.
 * <p>The gateway allows to access to {@link IClientNetworkController}
 * interface. Also it "forks" {@link INetworkEvents} callback interface,
 * allowing multiple subscriptions to {@link INetworkEventsListener}.</p>
 * <p>For all subscribers listener methods would be executed on GUI 
 * event thread.</p>
 * @author dragonfly
 */
public class ClientNetworkControllerGateway implements INetworkEvents, IClientNetworkController {
	
	static final Logger logger = LoggerFactory.getLogger(ClientNetworkControllerGateway.class);
	
	protected NetworkEventsListenerList listeners = new NetworkEventsListenerList();
	private IClientNetworkController controller;

	@Override
	public void onBan(final String reason) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onBan(reason);
			}
		});
	}

	@Override
	public void onConnect() {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onConnect();
			}
		});
	}

	@Override
	public void onDisconnect(final Exception cause) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onDisconnect(cause);
			}
		});
	}

	@Override
	public void onKick(final String reason) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onKick(reason);
			}
		});
	}

	@Override
	public void onReconnectAttempt(final int numberOfAttempts, final Exception cause) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onReconnectAttempt(numberOfAttempts, cause);
			}
		});
	}

	@Override
	public void onServerLookupStart() {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onServerLookupStart();
			}
		});
	}

	@Override
	public void onServerLookupStop() {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onServerLookupStop();
			}
		});
	}

	@Override
	public void onServerLookupUpdate(final List<? extends IUServerProperties> serverList) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onServerLookupUpdate(serverList);
			}
		});
	}

	@Override
	public void connect(InetSocketAddress serverAddress, String playerName,	String playerGroup) {
		controller.connect(serverAddress, playerName, playerGroup);
	}

	@Override
	public void disconnect() {
		controller.disconnect();
	}

	@Override
	public int getMaxAutoReconnectAttempts() {
		return controller.getMaxAutoReconnectAttempts();
	}

	@Override
	public void init(boolean searchForServers) {
		controller.init(searchForServers);
	}

	@Override
	public boolean isConnected() {
		return controller.isConnected();
	}

	@Override
	public void reconnect() {
		controller.reconnect();
	}

	@Override
	public void reconnect(String playerName, String playerGroup) {
		controller.reconnect(playerName, playerGroup);
	}

	@Override
	public void setMaxAutoReconnectAttempts(int maxNumberOfAttempts) {
		controller.setMaxAutoReconnectAttempts(maxNumberOfAttempts);
	}
	
	public ListenerList<INetworkEventsListener> getNetworkEventsListeners() {
		return listeners;
	}
	
	@Inject
	public void setClientNetworkController(IClientNetworkController controller) {
		if (this.controller != null)
			logger.warn("Redefining controller");
		this.controller = controller;
	}
	
	protected static class NetworkEventsListenerList 
		extends ListenerList<INetworkEventsListener> implements INetworkEventsListener {

		@Override
		public void onBan(String reason) {
			for (INetworkEventsListener listener : this)
				listener.onBan(reason);
		}

		@Override
		public void onConnect() {
			for (INetworkEventsListener listener : this)
				listener.onConnect();
		}

		@Override
		public void onDisconnect(Exception cause) {
			for (INetworkEventsListener listener : this)
				listener.onDisconnect(cause);
		}

		@Override
		public void onKick(String reason) {
			for (INetworkEventsListener listener : this)
				listener.onKick(reason);
		}

		@Override
		public void onReconnectAttempt(int numberOfAttempts, Exception cause) {
			for (INetworkEventsListener listener : this)
				listener.onReconnectAttempt(numberOfAttempts, cause);
		}

		@Override
		public void onServerLookupStart() {
			for (INetworkEventsListener listener : this)
				listener.onServerLookupStart();
		}

		@Override
		public void onServerLookupStop() {
			for (INetworkEventsListener listener : this)
				listener.onServerLookupStop();
		}

		@Override
		public void onServerLookupUpdate(List<? extends IUServerProperties> serverList) {
			for (INetworkEventsListener listener : this)
				listener.onServerLookupUpdate(serverList);
		}
		
		
	}

}

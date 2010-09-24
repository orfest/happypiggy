package ru.nsu.ccfit.pm.econ.modules;

import ru.nsu.ccfit.pm.econ.common.controller.player.IBuySell;
import ru.nsu.ccfit.pm.econ.common.controller.player.IChat;
import ru.nsu.ccfit.pm.econ.common.controller.player.ICompanyRoster;
import ru.nsu.ccfit.pm.econ.common.controller.player.IEconomicActivities;
import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventGateway;
import ru.nsu.ccfit.pm.econ.common.controller.player.IGameTime;
import ru.nsu.ccfit.pm.econ.common.controller.player.IMineStatsAccessor;
import ru.nsu.ccfit.pm.econ.common.controller.player.IPlayerRoster;
import ru.nsu.ccfit.pm.econ.common.controller.player.ITurnControl;
import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.view.IBuyOfferListener;
import ru.nsu.ccfit.pm.econ.common.view.IChatListener;
import ru.nsu.ccfit.pm.econ.common.view.ICompanyMessageListener;
import ru.nsu.ccfit.pm.econ.common.view.IEconomicActivityListener;
import ru.nsu.ccfit.pm.econ.common.view.IGameClockListener;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerRatingListener;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerStatsUpdateListener;
import ru.nsu.ccfit.pm.econ.common.view.ITurnChangeListener;
import ru.nsu.ccfit.pm.econ.controller.player.BuySell;
import ru.nsu.ccfit.pm.econ.controller.player.Chat;
import ru.nsu.ccfit.pm.econ.controller.player.CompanyRoster;
import ru.nsu.ccfit.pm.econ.controller.player.EconomicActivities;
import ru.nsu.ccfit.pm.econ.controller.player.GameEventGateway;
import ru.nsu.ccfit.pm.econ.controller.player.GameTime;
import ru.nsu.ccfit.pm.econ.controller.player.IModifiableLocalState;
import ru.nsu.ccfit.pm.econ.controller.player.LocalState;
import ru.nsu.ccfit.pm.econ.controller.player.MineStatsAccessor;
import ru.nsu.ccfit.pm.econ.controller.player.PlayerRoster;
import ru.nsu.ccfit.pm.econ.controller.player.TurnControl;
import ru.nsu.ccfit.pm.econ.view.shared.BuySellGateway;
import ru.nsu.ccfit.pm.econ.view.shared.ChatGateway;
import ru.nsu.ccfit.pm.econ.view.shared.CompanyRosterGateway;
import ru.nsu.ccfit.pm.econ.view.shared.EconomicActivityGateway;
import ru.nsu.ccfit.pm.econ.view.shared.GameTimeGateway;
import ru.nsu.ccfit.pm.econ.view.shared.MineStatsGateway;
import ru.nsu.ccfit.pm.econ.view.shared.PlayerRosterGateway;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

/**
 * Binds classes inside PlayerController component.
 * @author dragonfly
 */
class PlayerControllerModule extends AbstractModule {
	
	private boolean isServer = false;
	
	/**
	 * @param isServer Whether instantiation takes place in server
	 * 					or in client.
	 */
	public PlayerControllerModule(boolean isServer) {
		this.isServer = isServer;
	}

	@Override
	protected void configure() {
		configureProperties();
		configureLocalState();
		configureConsumers();
		configureListeners();
		configureGateway();
		
		if (isServer) {
			configureServerOnlyConsumers();
		}
	}
	
	private void configureProperties() {
		bindConstant().annotatedWith(Names.named("isInsideServer")).to(isServer);
	}
	
	private void configureLocalState() {
		LocalState localState = new LocalState();
		
		bind(IULocalState.class).toInstance(localState);
		bind(IModifiableLocalState.class).toInstance(localState);
		bind(LocalState.class).toInstance(localState);
	}
	
	private void configureConsumers() {
		bind(IMineStatsAccessor.class).to(MineStatsAccessor.class);
		bind(IEconomicActivities.class).to(EconomicActivities.class);
		bind(IGameTime.class).to(GameTime.class);
		bind(IPlayerRoster.class).to(PlayerRoster.class);
		bind(IChat.class).to(Chat.class);
		bind(ICompanyRoster.class).to(CompanyRoster.class);
		bind(IBuySell.class).to(BuySell.class);
		
		bind(MineStatsAccessor.class).in(Scopes.SINGLETON);
		bind(EconomicActivities.class).in(Scopes.SINGLETON);
		bind(GameTime.class).in(Scopes.SINGLETON);
		bind(PlayerRoster.class).in(Scopes.SINGLETON);
		bind(Chat.class).in(Scopes.SINGLETON);
		bind(CompanyRoster.class).in(Scopes.SINGLETON);
		bind(BuySell.class).in(Scopes.SINGLETON);
	}
	
	private void configureListeners() {
		bind(IPlayerStatsUpdateListener.class).to(MineStatsGateway.class);
		bind(ITurnChangeListener.class).to(GameTimeGateway.class);
		bind(IGameClockListener.class).to(GameTimeGateway.class);
		bind(IEconomicActivityListener.class).to(EconomicActivityGateway.class);
		bind(IPlayerRatingListener.class).to(PlayerRosterGateway.class);
		bind(IChatListener.class).to(ChatGateway.class);
		bind(ICompanyMessageListener.class).to(CompanyRosterGateway.class);
		bind(IBuyOfferListener.class).to(BuySellGateway.class);
	}
	
	private void configureGateway() {
		bind(IGameEventGateway.class).to(GameEventGateway.class);
		
		bind(GameEventGateway.class).in(Scopes.SINGLETON);
	}
	
	private void configureServerOnlyConsumers() {
		bind(ITurnControl.class).to(TurnControl.class);
		
		bind(TurnControl.class).in(Scopes.SINGLETON);
	}

}

package ru.nsu.ccfit.pm.econ.controller.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventConsumer;
import ru.nsu.ccfit.pm.econ.common.controller.player.IPlayerRoster;
import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameSnapshotEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent.IUPlayerRatingValue;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerRatingListener;

public class PlayerRoster extends AbstractGameEventGatewayClient implements
		IGameEventConsumer, IPlayerRoster {
	
	static final Logger logger = LoggerFactory.getLogger(PlayerRoster.class);
	
	private List<IUPlayer> playerList = new ArrayList<IUPlayer>();
	private List<IUPlayer> playerRatingList = new ArrayList<IUPlayer>();
	
	/*
	 * Injectables.
	 */
	private IULocalState local;
	private IPlayerRatingListener listener;
	
	@Inject
	public void setLocalState(IULocalState local) {
		this.local = local;
	}
	
	@Inject
	public void setListener(IPlayerRatingListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean processEvent(IUGameEvent event) {
		if (event instanceof IUGameSnapshotEvent) {
			processGameSnapshotEvent((IUGameSnapshotEvent)event);
		} else if (event instanceof IUTurnEndEvent) {
			processTurnEndEvent((IUTurnEndEvent)event);
		} else {
			return false;
		}
		
		return true;
	}

	@Override
	public IUPlayer getMe() {
		return local.getPlayer();
	}

	@Override
	public IUPlayer getPlayerById(long playerId) {
		synchronized (playerList) {
			for (IUPlayer player : playerList) {
				if (player.getId() == playerId)
					return player;
			}
		}
		
		return null;
	}

	@Override
	public IUPlayer getPlayerByName(String playerName) {
		synchronized (playerList) {
			for (IUPlayer player : playerList) {
				if (playerName.equals( player.getUnmodifiablePersonDescription().getName() ))
					return player;
			}
		}
		
		return null;
	}

	@Override
	public List<? extends IUPlayer> getPlayerList() {
		synchronized (playerList) {
			return Collections.unmodifiableList(playerList);
		}
	}

	@Override
	public List<? extends IUPlayer> getPlayerRatingList() {
		synchronized (playerRatingList) {
			return Collections.unmodifiableList(playerRatingList);
		}
	}
	
	private void processGameSnapshotEvent(IUGameSnapshotEvent event) {
		synchronized (playerList) {
			playerList.clear();
			playerList.addAll(event.getPlayers());
		}
	}
	
	private void processTurnEndEvent(IUTurnEndEvent event) {
		synchronized (playerRatingList) {
			playerRatingList.clear();
			for (IUPlayerRatingValue ratingValue : event.getRatingList()) {
				IUPlayer player = getPlayerById(ratingValue.getPlayerId());
				if (player == null) {
					logger.warn("Player with id={} not found while updating playerRatingList", 
							ratingValue.getPlayerId());
					continue;
				}
				
				playerRatingList.add(player);
			}
		}
		
		listener.onPlayerRatingListUpdate(getPlayerRatingList());
	}

}

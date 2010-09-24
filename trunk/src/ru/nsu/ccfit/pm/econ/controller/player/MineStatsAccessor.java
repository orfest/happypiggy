package ru.nsu.ccfit.pm.econ.controller.player;

import java.util.Collection;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventConsumer;
import ru.nsu.ccfit.pm.econ.common.controller.player.IMineStatsAccessor;
import ru.nsu.ccfit.pm.econ.common.engine.IPlayerPresence;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameSnapshotEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUShareholder;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUStudent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUTeacher;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerStatsUpdateListener;
import ru.nsu.ccfit.pm.econ.controller.player.roles.PersonDescription;
import ru.nsu.ccfit.pm.econ.controller.player.roles.Player;
import ru.nsu.ccfit.pm.econ.controller.player.roles.Student;
import ru.nsu.ccfit.pm.econ.controller.player.roles.Teacher;

public class MineStatsAccessor extends AbstractGameEventGatewayClient implements
		IGameEventConsumer, IMineStatsAccessor {
	
	static final Logger logger = LoggerFactory.getLogger(MineStatsAccessor.class);
	
	private boolean firstSnapshotEvent = true;
	
	/*
	 * Injectables.
	 */
	private LocalState local;
	private IPlayerPresence enginePlayerPresence;
	private IPlayerStatsUpdateListener playerStatsListener;
	
	@Inject
	public void setLocal(LocalState local) {
		this.local = local;
	}
	
	@Inject
	public void setEnginePlayerPresence(@Nullable IPlayerPresence enginePlayerPresence) {
		this.enginePlayerPresence = enginePlayerPresence;
	}
	
	@Inject
	public void setPlayerStatsListener(IPlayerStatsUpdateListener listener) {
		this.playerStatsListener = listener;
	}

	@Override
	public boolean processEvent(IUGameEvent event) {
		if (event instanceof IUGameSnapshotEvent) {
			processGameSnapshotEvent((IUGameSnapshotEvent)event);
		} else {
			return false;
		}
		
		return true;
	}
	
	private void processGameSnapshotEvent(IUGameSnapshotEvent event) {
		if (firstSnapshotEvent) {
			processFirstGameSnapshotEvent(event);
			firstSnapshotEvent = false;
		}
	}
	
	private void processFirstGameSnapshotEvent(IUGameSnapshotEvent event) {
		local.setScenarioProperties(event.getScenario());
		
		// record player id
		PersonDescription me = local.getModifiableMineDescription();
		IUPlayer minePlayer = null;
		for (IUPlayer player : event.getPlayers()) {
			if (me.equals(player.getUnmodifiablePersonDescription())) {
				local.setMineId(player.getId());
				minePlayer = player;
				break;
			}
		}
		
		logger.debug("Player '{}' is assigned id={}", local.getMineDescription().getName(), local.getMineId());
		
		// store player role object
		assert minePlayer != null;
		
		if (minePlayer instanceof IUStudent) {
			local.setPlayer(new Student( (IUStudent)minePlayer ));
		} else if (minePlayer instanceof IUTeacher) {
			local.setPlayer(new Teacher( (IUTeacher)minePlayer ));
		} else {
			logger.error("minePlayer has unknown type: {}", minePlayer.getClass().getName());
			return;
		}
		
		playerStatsListener.onRoleDetermined(local.getPlayer());
		
		if (local.getPlayer() instanceof IUStudent) {
			double startingCash = local.getScenarioProperties().getStartingCashValue(); 
			local.getModifiablePlayerAsStudent().setCash(startingCash);
			playerStatsListener.onCashChange(local.getPlayerAsStudent(), 0, startingCash);
		}
	}

	@Override
	public IUPersonDescription getPersonDescription() {
		return local.getMineDescription();
	}

	@Override
	public long getPlayerId() {
		return local.getMineId();
	}

	@Override
	public void setPersonDescription(String name, String group) {
		local.setMineDescription(new PersonDescription(name, group));
	}

	@Override
	public void setPersonDescription(IUPersonDescription pd) {
		local.setMineDescription(new PersonDescription(pd));
	}

	@Override
	public void notifyEngineOfPlayerPresence() {
		if (local.isInsideServer()) {
			assert enginePlayerPresence != null;
			
			enginePlayerPresence.unregisterPlayer(getPlayerId());
			
			Player player = new Teacher();
			player.setPersonDescription(local.getModifiableMineDescription());
			local.setPlayer(player);
			
			long id = enginePlayerPresence.registerPlayer(local.getPlayer());
			local.getModifiablePlayer().setId(id);
			
			playerStatsListener.onRoleDetermined(local.getPlayer());
		}
	}

	@Override
	public Collection<? extends IUShareHolding> getShares() {
		IUPlayer player = local.getPlayer();
		if (player instanceof IUShareholder) {
			return ((IUShareholder)player).getUnmodifiableShareList();
		}
		
		return null;
	}

}

/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine;

import ru.nsu.ccfit.pm.econ.common.engine.IPlayerPresence;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

import com.google.inject.Inject;

/**
 * IPlayerPresence interface implementation
 * 
 * Reference to IEngineDispatcher is injected
 * 
 * @author pupatenko
 * 
 */
public class PlayerPresenceEngine implements IPlayerPresence {

	private IEngineDispatcher engineDispatcher;

	public IEngineDispatcher getEngineDispatcher() {
		return engineDispatcher;
	}

	@Inject
	public void setEngineDispatcher(IEngineDispatcher engineDispatcher) {
		this.engineDispatcher = engineDispatcher;
	}

	@Override
	public boolean isPlayerPresent(long playerId) {
		return engineDispatcher.getPlayerPresenceActualHandler()
				.isPlayerPresent(playerId);
	}

	@Override
	public long registerPlayer(IUPlayer player) throws IllegalArgumentException {
		return engineDispatcher.getPlayerPresenceActualHandler()
				.registerPlayer(player);
	}

	@Override
	public void setPlayerPresence(long playerId, boolean isPresent) {
		engineDispatcher.getPlayerPresenceActualHandler().setPlayerPresence(
				playerId, isPresent);
	}

	@Override
	public void unregisterPlayer(long playerId) {
		engineDispatcher.getPlayerPresenceActualHandler().unregisterPlayer(
				playerId);
	}

}

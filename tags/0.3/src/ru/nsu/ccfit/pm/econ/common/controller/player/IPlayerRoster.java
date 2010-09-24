package ru.nsu.ccfit.pm.econ.common.controller.player;

import java.util.List;

import javax.annotation.Nullable;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerRatingListener;

/**
 * Player roster interface. Provides access to player objects.
 * @see IUPlayer
 * @see IPlayerRatingListener
 * 
 * @author dragonfly
 */
public interface IPlayerRoster {
	
	List<? extends IUPlayer> getPlayerList();
	
	@Nullable
	IUPlayer getPlayerById(long playerId);
	
	@Nullable
	IUPlayer getPlayerByName(String playerName);
	
	List<? extends IUPlayer> getPlayerRatingList();
	
	@Nullable
	IUPlayer getMe();

}

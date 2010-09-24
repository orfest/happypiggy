package ru.nsu.ccfit.pm.econ.common.view;

import java.util.List;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

/**
 * Listener interface for player rating change notifications.
 * 
 * @author dragonfly
 */
public interface IPlayerRatingListener {
	
	void onPlayerRatingListUpdate(List<? extends IUPlayer> ratingList);

}

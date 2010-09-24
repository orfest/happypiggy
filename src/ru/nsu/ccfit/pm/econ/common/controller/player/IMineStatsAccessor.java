package ru.nsu.ccfit.pm.econ.common.controller.player;

import java.util.Collection;

import javax.annotation.Nullable;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerStatsUpdateListener;

/**
 * This interface provides access to player properties and statistics.
 * @see IPlayerStatsUpdateListener
 * 
 * @author dragonfly
 */
public interface IMineStatsAccessor {
	
	IUPersonDescription getPersonDescription();
	
	void setPersonDescription(String name, String group);
	
	void setPersonDescription(IUPersonDescription pd);
	
	long getPlayerId();
	
	void notifyEngineOfPlayerPresence();
	
	@Nullable
	Collection<? extends IUShareHolding> getShares();

}

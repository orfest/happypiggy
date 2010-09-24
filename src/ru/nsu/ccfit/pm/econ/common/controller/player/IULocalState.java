package ru.nsu.ccfit.pm.econ.common.controller.player;

import java.util.Date;

import javax.annotation.Nonnegative;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenarioProperties;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUStudent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUTeacher;
import ru.nsu.ccfit.pm.econ.controller.player.IModifiableLocalState;

/**
 * Unmodifiable local to PlayerController state object interface.
 * @see IModifiableLocalState
 * 
 * @author dragonfly
 */
public interface IULocalState {
	
	/**
	 * Whether current environment is server or client. 
	 * @return <tt>true</tt> if run in server, <tt>false</tt> otherwise.
	 */
	boolean isInsideServer();
	
	
	IUPersonDescription getMineDescription();
	
	long getMineId();
	
	
	IUPlayer getPlayer();
	
	IUTeacher getPlayerAsTeacher();
	
	IUStudent getPlayerAsStudent();
	
	
	@Nonnegative
	int getTurnNumber();
	
	Date getTimeSinceTurnStart();
	
	boolean isTurnFinished();
	
	IUGameTime getCurrentGameTime();
	
	
	IUScenarioProperties getScenarioProperties();

}

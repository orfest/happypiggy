package ru.nsu.ccfit.pm.econ.common.engine;

import javax.annotation.Nullable;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;

/**
 * Interface to load scenarios into game engine.
 * <p>Components that may use this interface:
 * <ul><li>ScenarioController</li></ul>
 * </p>
 * <p>Note: publishing company messages is done via 
 * {@link IGameEventHandler} interface of Engine component. 
 * But obviously before any publish attempt scenario should 
 * be set via {@link #setScenario(IUScenario)} method.</p>
 * @see IUScenario
 * @author dragonfly
 */
public interface IScenarioPort {
	
	/**
	 * Set new scenario. 
	 * @param scenario New scenario.
	 * @return <tt>true</tt> if scenario was successfully updated, or 
	 * 			<tt>false</tt> if current engine state doesn't permit 
	 * 			scenario change (e.g. game is already running).
	 */
	boolean setScenario(IUScenario scenario);
	
	/**
	 * Get current scenario.
	 * @return Current scenario or <tt>null</tt> if none is loaded yet.
	 */
	@Nullable
	IUScenario getScenario();

}

package ru.nsu.ccfit.pm.econ.common.controller.scenario;

import java.util.Collection;

import javax.annotation.Nullable;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.common.engine.IScenarioPort;
import ru.nsu.ccfit.pm.econ.common.engine.data.CompanyMessageType;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUCompanyMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

/**
 * Secondary interface of ScenarioController. Used to publish
 * company messages.
 * <p>Components that may use this interface:
 * <ul><li>ServerUI</li></ul>
 * </p>
 * <p>This interface should be implemented by ScenarioController 
 * component. Implementations should use {@link IGameEventHandler}
 * interface to send {@link IUCompanyMessageEvent} to engine.</p>
 * 
 * @author dragonfly
 */
public interface ICompanyMessagePublisher {
	
	/**
	 * Current game scenario. Implementations should provide 
	 * access to the scenario of current importance, i.e. the 
	 * one that is provided by engine.
	 * @see IScenarioPort#getScenario()
	 * @return Game scenario or <tt>null</tt> if scenario is 
	 * 			not yet available.
	 */
	@Nullable
	IUScenario getScenario();
	
	/**
	 * Publishes company message that has type 
	 * {@link CompanyMessageType#OFFICIAL} to all players.
	 * @param messageId Message identifier as specified by 
	 * 					scenario.
	 */
	void publishOfficialMessage(long messageId);
	
	/**
	 * Sends company message of type 
	 * {@link CompanyMessageType#RUMOR} to selected set of 
	 * players.
	 * @param messageId Message identifier as specified by 
	 * 					scenario.
	 * @param receivers Set of message receivers. If <tt>null</tt>,
	 * 					all players should receive the message.
	 */
	void publishRumorMessage(long messageId, @Nullable Collection<? extends IUPlayer> receivers);

}

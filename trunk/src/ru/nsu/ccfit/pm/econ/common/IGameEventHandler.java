package ru.nsu.ccfit.pm.econ.common;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUCompanyMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * Game event handler interface. All components capable of handling, 
 * dispatching or passing game events should implement this interface.
 * Normally if two components make use of each other and are able to 
 * generate, pass or absorb game events, they both would implement
 * this interface.
 * <p>Components that may implement this interface:
 * <ul>
 * <li>Engine</li>
 * <li>PlayerController</li>
 * <li>Networking (serves a "passthrough" job)</li>
 * <li>BotEngine</li>
 * </ul>
 * </p>
 * <p>Components that may use this interface:
 * <ul>
 * <li>Engine</li>
 * <li>PlayerController</li>
 * <li>Networking</li>
 * <li>ScenarioController and ScenarioEditorController 
 * (will pass only {@link IUCompanyMessageEvent}s)</li>
 * <li>BotEngine</li>
 * </ul>
 * </p>
 * @author dragonfly
 */
public interface IGameEventHandler {
	
	/**
	 * Takes appropriate action on event arrival. Actions may vary depending
	 * on event type (see subinterfaces of {@link IUGameEvent}), sender and  
	 * receivers.
	 * <p>Note that events are unmodifiable and should be either passed on
	 * to the next component in chain or should be cloned with appropriate 
	 * modifications and then passed further.</p>
	 * <p>This method may be called from arbitrary thread, so if 
	 * implementations rely on processing events in a specific thread context
	 * they should ensure it on their own. Also implementations should not 
	 * block.</p>
	 * @param event Event to handle.
	 */
	void handleEvent(IUGameEvent event);

}

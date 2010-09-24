/**
 * 
 */
package ru.nsu.ccfit.pm.econ.mock;

import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * @author orfest
 *
 */
public class GameEventHandlerHistory implements IGameEventHandler {

	private LinkedList<IUGameEvent> events = new LinkedList<IUGameEvent>();
	
	/**
	 * 
	 */
	public GameEventHandlerHistory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleEvent(IUGameEvent event) {
		events.add(event);
	}

	public LinkedList<IUGameEvent> getEvents() {
		return events;
	}

}

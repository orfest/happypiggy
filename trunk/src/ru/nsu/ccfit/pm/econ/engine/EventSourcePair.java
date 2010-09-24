/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * Simple class, containing game event and information about sender
 * 
 * @author pupatenko
 * 
 * @see EventSource
 * @see IUGameEvent
 */
public class EventSourcePair {
	private EventSource source;
	private IUGameEvent event;

	public EventSourcePair(EventSource source, IUGameEvent event) {
		this.source = source;
		this.event = event;
	}

	public EventSourcePair(EventSourcePair toCopy) {
		source = toCopy.source;
		event = toCopy.event;
	}

	public EventSource getSource() {
		return source;
	}

	public IUGameEvent getEvent() {
		return event;
	}

}

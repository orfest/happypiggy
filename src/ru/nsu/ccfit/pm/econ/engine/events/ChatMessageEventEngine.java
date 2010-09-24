/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUChatMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * {@link IUChatMessageEvent} interface implementation by engine
 * 
 * @author pupatenko
 * 
 */
public class ChatMessageEventEngine extends GameEventEngine implements
		IUChatMessageEvent {

	private String message;

	public ChatMessageEventEngine(String message, IUGameEvent event) {
		super(event);
		this.message = message;
	}

	public ChatMessageEventEngine(IUChatMessageEvent toCopy) {
		super(toCopy.getEventTime(), toCopy.getReceiverIds(), toCopy
				.getSenderId(), toCopy.isBroadcast());
		message = toCopy.getMessage();
	}

	@Override
	public String getMessage() {
		return message;
	}
}

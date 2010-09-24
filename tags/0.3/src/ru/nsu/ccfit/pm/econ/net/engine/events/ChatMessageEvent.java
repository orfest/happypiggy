package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUChatMessageEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;

/**
 * @author orfest
 * 
 */
public class ChatMessageEvent extends GameEvent implements IUChatMessageEvent {

	@SerializeThis
	private String message = "";

	public ChatMessageEvent(){
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

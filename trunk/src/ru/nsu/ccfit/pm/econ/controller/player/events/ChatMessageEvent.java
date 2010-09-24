package ru.nsu.ccfit.pm.econ.controller.player.events;

import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUChatMessageEvent;

public class ChatMessageEvent extends GameEvent implements IUChatMessageEvent {
	
	public String message;

	public ChatMessageEvent(IULocalState local, String message) {
		super(local);
		this.message = message;
	}
	
	public ChatMessageEvent(IULocalState local, String message, long receiverId) {
		super(local);
		this.message = message;
		setSingleReceiver(receiverId);
	}

	@Override
	public String getMessage() {
		return message;
	}

}

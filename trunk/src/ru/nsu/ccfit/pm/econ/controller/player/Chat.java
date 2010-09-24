package ru.nsu.ccfit.pm.econ.controller.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IChat;
import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventConsumer;
import ru.nsu.ccfit.pm.econ.common.controller.player.IPlayerRoster;
import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUChatMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameSnapshotEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.view.IChatListener;
import ru.nsu.ccfit.pm.econ.controller.player.data.GameTime;
import ru.nsu.ccfit.pm.econ.controller.player.events.ChatMessageEvent;

public class Chat extends AbstractGameEventGatewayClient implements
		IGameEventConsumer, IChat {
	
	static final Logger logger = LoggerFactory.getLogger(Chat.class);
	
	private List<ChatMessage> messagesList = new ArrayList<ChatMessage>();
	
	/*
	 * Injectables.
	 */
	private IPlayerRoster playerRoster;
	private IULocalState local;
	private IChatListener listener;
	
	@Inject
	public void setPlayerRoster(IPlayerRoster playerRoster) {
		this.playerRoster = playerRoster;
	}
	
	@Inject
	public void setLocalState(IULocalState local) {
		this.local = local;
	}
	
	@Inject
	public void setChatListener(IChatListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean processEvent(IUGameEvent event) {
		if (event instanceof IUChatMessageEvent) {
			processChatMessageEvent((IUChatMessageEvent)event);
		} else if (event instanceof IUGameSnapshotEvent) {
			processGameSnapshotEvent((IUGameSnapshotEvent)event);
		} else {
			return false;
		}
		
		return true;
	}

	@Override
	public List<? extends IChatMessage> getMessagesList() {
		return Collections.unmodifiableList(messagesList);
	}

	@Override
	public void sendPrivateMessage(IUPlayer receiver, String message) {
		sendPrivateMessage(receiver.getId(), message);
	}

	@Override
	public void sendPrivateMessage(long receiverId, String message) {
		ChatMessageEvent event = new ChatMessageEvent(local, message, receiverId);
		gateway.sendEvent(event);
	}

	@Override
	public void sendPublicMessage(String message) {
		ChatMessageEvent event = new ChatMessageEvent(local, message);
		gateway.sendEvent(event);
	}
	
	private void processChatMessageEvent(IUChatMessageEvent event) {
		if (logger.isDebugEnabled()) {
			if (!event.getReceiverIds().contains(local.getMineId())) {
				logger.warn("Received message not intended for this player");
				return;
			}
		}
		
		ChatMessage chatMesg = new ChatMessage(event);
		messagesList.add(chatMesg);
		
		if (chatMesg.isPrivate()) {
			listener.onPrivateMessageReceived(chatMesg.getSender(), chatMesg.getMessage(), chatMesg.getTime());
		} else {
			listener.onPublicMessageReceived(chatMesg.getSender(), chatMesg.getMessage(), chatMesg.getTime());
		}
	}
	
	private void processGameSnapshotEvent(IUGameSnapshotEvent event) {
		// This should be implemented if snapshots may arrive during the game.
		// That way we may call listener.onChatLogUpdate() here.
	}
	
	
	protected class ChatMessage implements IChatMessage {
		
		private String message;
		private long senderId;
		private boolean privateMesg;
		private GameTime time;
		
		public ChatMessage(long senderId, String message, boolean isPrivate, IUGameTime time) {
			this.senderId = senderId;
			this.message = message;
			this.privateMesg = isPrivate;
			this.time = new GameTime(time);
		}
		
		public ChatMessage(IUChatMessageEvent event) {
			this.senderId = event.getSenderId();
			this.message = event.getMessage();
			this.privateMesg = !event.isBroadcast();
			this.time = new GameTime(event.getEventTime());
		}

		@Override
		public String getMessage() {
			return message;
		}

		@Override
		public IUPlayer getSender() {
			return playerRoster.getPlayerById(senderId);
		}

		@Override
		public long getSenderId() {
			return senderId;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof IChatMessage) {
				IChatMessage mesg = (IChatMessage)obj;
				return getSenderId() == mesg.getSenderId() && getMessage().equals(mesg.getMessage());
			}
			
			return super.equals(obj);
		}

		@Override
		public String toString() {
			return getSender().getUnmodifiablePersonDescription().getName() 
					+ (isPrivate() ? "# " : ": ") 
					+ getMessage();
		}

		@Override
		public boolean isPrivate() {
			return privateMesg;
		}

		@Override
		public IUGameTime getTime() {
			return time;
		}
		
	}

}

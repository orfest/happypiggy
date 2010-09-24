package ru.nsu.ccfit.pm.econ.view.shared;

import java.util.List;

import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IChat;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.view.IChatListener;

/**
 * Gateway for views to send and receive chat messages 
 * via <tt>PlayerController</tt>.
 * @see IChat
 * @see IChatListener
 *  
 * @author dragonfly
 */
public class ChatGateway implements IChat, IChatListener {
	
	static final Logger logger = LoggerFactory.getLogger(ChatGateway.class);
	
	protected ChatListenerList listeners = new ChatListenerList();
	private IChat controller;

	@Override
	public List<? extends IChatMessage> getMessagesList() {
		return controller.getMessagesList();
	}

	@Override
	public void sendPrivateMessage(IUPlayer receiver, String message) {
		controller.sendPrivateMessage(receiver, message);
	}

	@Override
	public void sendPrivateMessage(long receiverId, String message) {
		controller.sendPrivateMessage(receiverId, message);
	}

	@Override
	public void sendPublicMessage(String message) {
		controller.sendPublicMessage(message);
	}

	@Override
	public void onChatLogUpdate(final List<? extends IChatMessage> messageLog) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onChatLogUpdate(messageLog);
			}
		});
	}

	@Override
	public void onPrivateMessageReceived(final IUPlayer sender, 
			final String message, final IUGameTime receiveTime) {
		
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onPrivateMessageReceived(sender, message, receiveTime);
			}
		});
	}

	@Override
	public void onPublicMessageReceived(final IUPlayer sender, 
			final String message, final IUGameTime receiveTime) {
		
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onPublicMessageReceived(sender, message, receiveTime);
			}
		});
	}
	
	public ListenerList<IChatListener> getChatListeners() {
		return listeners;
	}

	@Inject
	public void setChat(IChat controller) {
		if (this.controller != null)
			logger.warn("Redefining controller");
		this.controller = controller;
	}
	
	protected static class ChatListenerList 
		extends ListenerList<IChatListener> implements IChatListener {

		@Override
		public void onChatLogUpdate(List<? extends IChatMessage> messageLog) {
			for (IChatListener listener : this)
				listener.onChatLogUpdate(messageLog);
		}

		@Override
		public void onPrivateMessageReceived(IUPlayer sender, String message, IUGameTime receiveTime) {
			for (IChatListener listener : this)
				listener.onPrivateMessageReceived(sender, message, receiveTime);
		}

		@Override
		public void onPublicMessageReceived(IUPlayer sender, String message, IUGameTime receiveTime) {
			for (IChatListener listener : this)
				listener.onPublicMessageReceived(sender, message, receiveTime);
		}
		
	}

}

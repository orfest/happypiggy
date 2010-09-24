package ru.nsu.ccfit.pm.econ.common.controller.player;

import java.util.List;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

/**
 * Chat interface. Allows to send and receive chat messages.
 * 
 * @author dragonfly
 */
public interface IChat {
	
	void sendPublicMessage(String message);
	
	void sendPrivateMessage(IUPlayer receiver, String message);
	
	void sendPrivateMessage(long receiverId, String message);
	
	List<? extends IChatMessage> getMessagesList();
	
	/**
	 * Data object interface for incoming chat messages.
	 * 
	 * @author dragonfly
	 */
	public interface IChatMessage {
		
		public String getMessage();
		
		public long getSenderId();
		
		public IUPlayer getSender();
		
		public boolean isPrivate();
		
		public IUGameTime getTime();
		
	}

}

package ru.nsu.ccfit.pm.econ.common.view;

import java.util.List;

import ru.nsu.ccfit.pm.econ.common.controller.player.IChat.IChatMessage;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

/**
 * Listener interface for chat activities.
 * 
 * @author dragonfly
 */
public interface IChatListener {
	
	void onPublicMessageReceived(IUPlayer sender, String message, IUGameTime receiveTime);
	
	void onPrivateMessageReceived(IUPlayer sender, String message, IUGameTime receiveTime);
	
	void onChatLogUpdate(List<? extends IChatMessage> messageLog);

}

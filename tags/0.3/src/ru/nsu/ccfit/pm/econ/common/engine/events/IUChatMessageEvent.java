package ru.nsu.ccfit.pm.econ.common.engine.events;

/**
 * Unmodifiable interface for passing chat messages.
 * @author dragonfly
 */
public interface IUChatMessageEvent extends IUGameEvent {
	
	/**
	 * Chat message.
	 * <p>Message is considered private if and only if 
	 * {@link IUGameEvent#isBroadcast()} returns <tt>false</tt>.</p>
	 * @return Chat message.
	 */
	String getMessage();

}

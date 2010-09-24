package ru.nsu.ccfit.pm.econ.common.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.data.CompanyMessageType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;

/**
 * Unmodifiable interface for publishing company messages.
 * <p>Note that {@link IUGameEvent#getReceiverIds()} and 
 * {@link IUGameEvent#isBroadcast()} values may be ignored by 
 * implementations if message type is {@link CompanyMessageType#OFFICIAL}.</p>
 * @see IUTextOnlyCompanyMessage#getType()
 * @author dragonfly
 */
public interface IUCompanyMessageEvent extends IUGameEvent {
	
	/**
	 * Published message.
	 * @return Published company message.
	 */
	IUTextOnlyCompanyMessage getCompanyMessage();

}

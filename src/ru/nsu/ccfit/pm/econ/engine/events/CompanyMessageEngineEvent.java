package ru.nsu.ccfit.pm.econ.engine.events;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUCompanyMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.engine.data.TextOnlyCompanyMessageEngine;

/**
 * @author orfest
 * 
 */
public class CompanyMessageEngineEvent extends GameEventEngine implements
		IUCompanyMessageEvent {

	private TextOnlyCompanyMessageEngine textCompanyMessage;

	/**
	 * @param eventTime
	 * @param recieverIds
	 * @param senderId
	 */
	public CompanyMessageEngineEvent(IUTextOnlyCompanyMessage msg,
			IUGameEvent event_) {
		super(event_);
		textCompanyMessage = new TextOnlyCompanyMessageEngine(msg);
	}

	public CompanyMessageEngineEvent(IUGameTime eventTime,
			Collection<Long> recieverIds, long senderId, boolean broadcast,
			IUTextOnlyCompanyMessage msg) {
		super(eventTime, recieverIds, senderId, broadcast);
		textCompanyMessage = new TextOnlyCompanyMessageEngine(msg);
	}

	public CompanyMessageEngineEvent(IUCompanyMessageEvent toCopy) {
		super((IUGameEvent) toCopy);
		textCompanyMessage = new TextOnlyCompanyMessageEngine(toCopy
				.getCompanyMessage());
	}

	@Override
	public TextOnlyCompanyMessageEngine getCompanyMessage() {
		return textCompanyMessage;
	}

	public void setTextCompanyMessage(
			TextOnlyCompanyMessageEngine textCompanyMessage) {
		this.textCompanyMessage = textCompanyMessage;
	}

}

package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUCompanyMessageEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.TextOnlyCompanyMessage;

/**
 * @author orfest
 * 
 */
public class CompanyMessageEvent extends GameEvent implements
		IUCompanyMessageEvent {

	@SerializeThis
	private IUTextOnlyCompanyMessage companyMessage = new TextOnlyCompanyMessage();

	public CompanyMessageEvent(){
	}
	
	public IUTextOnlyCompanyMessage getCompanyMessage() {
		return companyMessage;
	}

	public void setCompanyMessage(IUTextOnlyCompanyMessage message) {
		this.companyMessage = message;
	}

}

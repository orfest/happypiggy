package ru.nsu.ccfit.pm.econ.controller.player.events;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUCompanyMessageEvent;

public class CompanyMessageEvent extends GameEvent implements IUCompanyMessageEvent {
	
	public IUTextOnlyCompanyMessage companyMessage;

	public CompanyMessageEvent(IULocalState local, IUTextOnlyCompanyMessage message) {
		super(local);
		this.companyMessage = message;
	}

	@Override
	public IUTextOnlyCompanyMessage getCompanyMessage() {
		return companyMessage;
	}
	
	public void setReceiverIds(Collection<Long> receiverIds) {
		super.setReceiverIds(receiverIds);
	}

}

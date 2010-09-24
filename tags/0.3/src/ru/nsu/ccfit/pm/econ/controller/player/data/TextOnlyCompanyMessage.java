package ru.nsu.ccfit.pm.econ.controller.player.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.CompanyMessageType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;

public class TextOnlyCompanyMessage implements IUTextOnlyCompanyMessage {
	
	public long companyId;
	public long id;
	public String message;
	public GameTime publishTime;
	public String title;
	public CompanyMessageType type;
	
	public TextOnlyCompanyMessage(IUTextOnlyCompanyMessage m) {
		this.companyId = m.getCompanyId();
		this.id = m.getId();
		this.message = m.getMessage();
		this.title = m.getTitle();
		this.type = m.getType();
		this.publishTime = new GameTime(m.getPublishTime()); 
	}

	@Override
	public long getCompanyId() {
		return companyId;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public IUGameTime getPublishTime() {
		return publishTime;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public CompanyMessageType getType() {
		return type;
	}

	public void setPublishTime(GameTime publishTime) {
		this.publishTime = publishTime;
	}

}

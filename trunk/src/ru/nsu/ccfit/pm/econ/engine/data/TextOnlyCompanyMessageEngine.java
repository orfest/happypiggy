package ru.nsu.ccfit.pm.econ.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.CompanyMessageType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;

/**
 * IUTextOnlyCompanyMessage interface implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUTextOnlyCompanyMessage
 */
public class TextOnlyCompanyMessageEngine implements IUTextOnlyCompanyMessage {
	private long companyId;
	private long id;
	private String message;
	private GameTimeEngine publishTime;
	private String title;
	private CompanyMessageType type;

	public TextOnlyCompanyMessageEngine(long companyId, long id,
			String message, GameTimeEngine publishTime, String title,
			CompanyMessageType type) {
		this.companyId = companyId;
		this.id = id;
		this.message = message;
		this.publishTime = publishTime;
		this.title = title;
		this.type = type;
	}

	public TextOnlyCompanyMessageEngine(IUTextOnlyCompanyMessage toCopy) {
		companyId = toCopy.getCompanyId();
		id = toCopy.getId();
		message = toCopy.getMessage();
		publishTime = new GameTimeEngine(toCopy.getPublishTime());
		title = toCopy.getTitle();
		type = toCopy.getType();
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

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPublishTime(GameTimeEngine publishTime) {
		this.publishTime = publishTime;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(CompanyMessageType type) {
		this.type = type;
	}

}

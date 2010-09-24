package ru.nsu.ccfit.pm.econ.net.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.CompanyMessageType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.TextOnlyCompanyMessageProto;

/**
 * @author orfest
 * 
 */
@ProtoClass(value=TextOnlyCompanyMessageProto.class)
public class TextOnlyCompanyMessage implements IUTextOnlyCompanyMessage {

	public void setType(CompanyMessageType type) {
		this.type = type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPublishTime(IUGameTime publishTime) {
		this.publishTime = publishTime;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@SerializeThis
	private CompanyMessageType type = CompanyMessageType.OFFICIAL;
	@SerializeThis
	private String title = "";
	@SerializeThis
	private IUGameTime publishTime = new GameTime();
	@SerializeThis
	private String message = "";
	@SerializeThis
	private long id = Long.MAX_VALUE;
	@SerializeThis
	private long companyId = Long.MAX_VALUE;

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

}

package ru.nsu.ccfit.pm.econ.engine.data;

import java.util.ArrayList;
import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.CompanyMessageType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.engine.roles.PlayerEngine;

/**
 * IUCompanyMessage interface implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUCompanyMessage
 */
public class CompanyMessageEngine implements IUCompanyMessage {

	@Override
	public double effectOnExpectedProfit(double oldExpectedProfit) {
		return oldExpectedProfit * coefficientK + coefficientC;
	}

	private double coefficientC;
	private double coefficientK;
	private Collection<PlayerEngine> receivers;
	private boolean published;
	private long companyId;
	private long id;
	private String message;
	private IUGameTime publishTime;
	private String title;
	private CompanyMessageType type;

	public CompanyMessageEngine(double coefficientC, double coefficientK,
			Collection<PlayerEngine> receivers, boolean published,
			long companyId, long id, String message, IUGameTime publishTime,
			String title, CompanyMessageType type) {
		this.coefficientC = coefficientC;
		this.coefficientK = coefficientK;
		this.receivers = new ArrayList<PlayerEngine>(receivers);
		this.published = published;
		this.companyId = companyId;
		this.id = id;
		this.message = message;
		this.publishTime = publishTime;
		this.title = title;
		this.type = type;
	}

	public CompanyMessageEngine(IUCompanyMessage toCopy) {
		coefficientC = toCopy.getCoefficientC();
		coefficientK = toCopy.getCoefficientK();
		receivers = new ArrayList<PlayerEngine>(toCopy.getReceivers().size());
		for (IUPlayer pl : toCopy.getReceivers())
			receivers.add(new PlayerEngine(pl));
		published = toCopy.isPublished();
		companyId = toCopy.getCompanyId();
		id = toCopy.getId();
		message = toCopy.getMessage();
		publishTime = toCopy.getPublishTime();
		title = toCopy.getTitle();
		type = toCopy.getType();
	}

	@Override
	public double getCoefficientC() {
		return coefficientC;
	}

	@Override
	public double getCoefficientK() {
		return coefficientK;
	}

	@Override
	public Collection<? extends IUPlayer> getReceivers() {
		return receivers;
	}

	@Override
	public boolean isPublished() {
		return published;
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

	public void setCoefficientC(double coefficientC) {
		this.coefficientC = coefficientC;
	}

	public void setCoefficientK(double coefficientK) {
		this.coefficientK = coefficientK;
	}

	public void setReceivers(Collection<PlayerEngine> receivers) {
		this.receivers = receivers;
	}

	public void setPublished(boolean published) {
		this.published = published;
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

	public void setPublishTime(IUGameTime publishTime) {
		this.publishTime = publishTime;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(CompanyMessageType type) {
		this.type = type;
	}

}

package ru.nsu.ccfit.pm.econ.controller.player.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ru.nsu.ccfit.pm.econ.common.controller.player.IPlayerRoster;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

public class CompanyMessage extends TextOnlyCompanyMessage implements IUCompanyMessage {
	
	public double coefficientC;
	public double coefficientK;
	public List<IUPlayer> receivers;
	public boolean published;

	public CompanyMessage(IUCompanyMessage m, IPlayerRoster playerRoster) {
		super(m);
		
		this.coefficientC = m.getCoefficientC();
		this.coefficientK = m.getCoefficientK();
		this.published = m.isPublished();
		
		this.receivers = new ArrayList<IUPlayer>();
		for (IUPlayer player : m.getReceivers()) {
			this.receivers.add(playerRoster.getPlayerById(player.getId()));
		}
	}

	@Override
	public double effectOnExpectedProfit(double oldExpectedProfit) {
		return oldExpectedProfit * coefficientK + coefficientC;
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
		return Collections.unmodifiableCollection(receivers);
	}

	@Override
	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public void setReceivers(List<IUPlayer> receivers) {
		this.receivers = receivers;
	}
	
	public void publishTo(List<IUPlayer> receivers) {
		setPublished(true);
		setReceivers(receivers);
	}
	
	public void publishTo(Collection<Long> receiverIds, IPlayerRoster playerRoster) {
		setPublished(true);
		
		receivers.clear();
		for (long id : receiverIds) {
			this.receivers.add(playerRoster.getPlayerById(id));
		}
	}
	
	public void publishToAll(IPlayerRoster playerRoster) {
		setPublished(true);
		
		receivers.clear();
		receivers.addAll(playerRoster.getPlayerList());
	}

}

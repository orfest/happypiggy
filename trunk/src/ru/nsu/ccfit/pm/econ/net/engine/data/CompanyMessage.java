package ru.nsu.ccfit.pm.econ.net.engine.data;

import java.util.Collection;
import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.roles.Player;

/**
 * @author orfest
 * 
 */
public class CompanyMessage extends TextOnlyCompanyMessage implements IUCompanyMessage {

	@SerializeThis
	private double coefficientC = Double.MAX_VALUE;
	@SerializeThis
	private double coefficientK = Double.MAX_VALUE;
	@SerializeThis
	@BewareCollectionOf(value = Player.class)
	private Collection<? extends IUPlayer> receivers = new LinkedList<IUPlayer>();
	@SerializeThis(get = "isPublished")
	private boolean published = false;

	@Override
	public double effectOnExpectedProfit(double oldExpectedProfit) {
		// TODO Auto-generated method stub
		return 0;
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

	public void setCoefficientC(double coefficientC) {
		this.coefficientC = coefficientC;
	}

	public void setCoefficientK(double coefficientK) {
		this.coefficientK = coefficientK;
	}

	public void setReceivers(Collection<? extends IUPlayer> receivers) {
		this.receivers = receivers;
	}

	public void setPublished(boolean isPublished) {
		published = isPublished;
	}

}

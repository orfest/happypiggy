package ru.nsu.ccfit.pm.econ.net.engine.events;

import java.util.List;
import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUDividendVotingProposalEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;

/**
 * @author orfest
 * 
 */
public class DividendVotingProposalEvent extends GameEvent implements
		IUDividendVotingProposalEvent {

	@SerializeThis
	private long companyId = Long.MAX_VALUE;
	@SerializeThis
	private double companyProfit = Double.MAX_VALUE;
	@SerializeThis
	@BewareCollectionOf(Double.class)
	private List<Double> possibleDRPs = new LinkedList<Double>();

	public DividendVotingProposalEvent(){
	}

	public long getCompanyId() {
		return companyId;
	}

	public double getCompanyProfit() {
		return companyProfit;
	}

	public List<Double> getPossibleDRPs() {
		return possibleDRPs;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setCompanyProfit(double companyProfit) {
		this.companyProfit = companyProfit;
	}

	public void setPossibleDRPs(List<Double> possibleDRPs) {
		this.possibleDRPs = possibleDRPs;
	}

}

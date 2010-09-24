package ru.nsu.ccfit.pm.econ.net.engine.events;

import java.util.LinkedList;

import java.util.List;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUDividendVotingEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.VoteSet;

/**
 * @author orfest
 * 
 */
public class DividendVotingEvent extends DividendVotingProposalEvent implements
		IUDividendVotingEvent {

	@SerializeThis
	private double resultingDPR = Double.MAX_VALUE;
	@SerializeThis
	@BewareCollectionOf(VoteSet.class)
	private List<IUVoteSet> votes = new LinkedList<IUVoteSet>();

	public DividendVotingEvent(){
	}

	public double getResultingDPR() {
		return resultingDPR;
	}

	public void setResultingDPR(double resultingDPR) {
		this.resultingDPR = resultingDPR;
	}

	public List<IUVoteSet> getVotes() {
		return votes;
	}

	public void setVotes(List<IUVoteSet> votes) {
		this.votes = votes;
	}
	

}

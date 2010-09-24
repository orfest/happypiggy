/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUDividendVotingEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.VoteSetProto;

/**
 * @author orfest
 *
 */
/**
 * @author orfest
 */
@ProtoClass(value=VoteSetProto.class)
public class VoteSet implements IUDividendVotingEvent.IUVoteSet {

	@SerializeThis
	private int numberOfVotes = Integer.MAX_VALUE;
	@SerializeThis
	private double dpr = Double.MAX_VALUE;

	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	public double getDPR() {
		return dpr;
	}

	public double getDpr() {
		return dpr;
	}

	public void setDpr(double dpr) {
		this.dpr = dpr;
	}

	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

}


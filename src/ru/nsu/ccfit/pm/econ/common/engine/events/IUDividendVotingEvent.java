package ru.nsu.ccfit.pm.econ.common.engine.events;

import java.util.List;

import javax.annotation.Nonnegative;

/**
 * Unmodifiable interface that represents voting results of one voting 
 * for one company.
 * <p>This interface should be used only within server or scenario
 * editor.</p>
 * @see IUDividendVotingProposalEvent
 * @see IUDividendVoteEvent
 * @see IUDividendPayoutEvent
 * @author dragonfly
 */
public interface IUDividendVotingEvent extends IUDividendVotingProposalEvent {
	
	/**
	 * List of groups of votes sorted by {@link IUVoteSet#getDPR()} value. 
	 * @return List of sets of votes. Vote sets are defined according to
	 * 			{@link IUDividendVotingProposalEvent#getPossibleDRPs()}
	 * 			conditions. 
	 */
	List<IUVoteSet> getVotes();
	
	/**
	 * Result of this voting. If this voting doesn't have result (e.g. it is
	 * not final) this value should be negative.
	 * @return Result of this voting (nonnegative value), or negative value
	 * 			if result is not available.
	 */
	double getResultingDPR();
	
	/**
	 * Unmodifiable interface which represents vote set. Votes in a vote set
	 * are grouped by the same DPR value.
	 * @author dragonfly
	 */
	public interface IUVoteSet {
		
		/**
		 * Number of votes in a set.
		 * @return Number of votes for this DPR value.
		 */
		@Nonnegative
		int getNumberOfVotes();
		
		/**
		 * Group DPR value.
		 * @return Selected DPR value for this vote set.
		 */
		@Nonnegative
		double getDPR();
		
	}

}

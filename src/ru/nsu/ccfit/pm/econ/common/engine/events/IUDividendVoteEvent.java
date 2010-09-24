package ru.nsu.ccfit.pm.econ.common.engine.events;

import javax.annotation.Nonnegative;

/**
 * Unmodifiable interface for transferring player vote.
 * @see IUDividendVotingProposalEvent
 * @author dragonfly
 */
public interface IUDividendVoteEvent extends IUGameEvent {
	
	/**
	 * Company to vote for.
	 * @return Company identifier.
	 */
	long getCompanyId();
	
	/**
	 * Player's vote.
	 * <p>Player's vote should conform to 
	 * {@link IUDividendVotingProposalEvent#getPossibleDRPs()}
	 * conditions.</p>
	 * @return Player's vote (selected dividend payout ratio).
	 */
	@Nonnegative
	double getSelectedDPR();

}

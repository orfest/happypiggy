package ru.nsu.ccfit.pm.econ.common.engine.events;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Unmodifiable interface for proposing company dividend payout
 * ratio voting conditions. The conditions are usually proposed by
 * Teacher, automatically or manually. Voting usually has two rounds.
 * Each round should be preceded by voting proposal.
 * <p>The voting is usually performed at the end of accounting period,
 * so voting proposal means that company accounting period has come 
 * to its end.</p> 
 * @see IUDividendVoteEvent
 * @see IUDividendVotingEvent
 * @author dragonfly
 */
public interface IUDividendVotingProposalEvent extends IUGameEvent {
	
	/**
	 * Company voting is related to.
	 * @return Company identifier.
	 */
	long getCompanyId();
	
	/**
	 * Company profit by the end of the accounting period.
	 * <p>Note that dividend voting always happens at the end of 
	 * the company accounting period when profit value is known.
	 * If voting has more then one round this value stays the same 
	 * between voting proposals for the same accounting period.</p>
	 * @return Company profit during the passed accounting period.
	 */
	double getCompanyProfit();
	
	/**
	 * List of possible dividend payout ratios (DPRs) for this company.
	 * The list should be ordered by DPR increase. All DPRs should be 
	 * in range [0; 1]. If this list is <tt>null</tt> any DPR is permitted.
	 * @return List of permitted dividend payout ratio values, or 
	 * 			<tt>null</tt> if there are no restrictions on selected
	 * 			dividend payout ratio.
	 */
	@Nullable
	List<Double> getPossibleDRPs();

}

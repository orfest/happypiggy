package ru.nsu.ccfit.pm.econ.common.engine.events;

import javax.annotation.Nonnegative;

/**
 * Unmodifiable interface for assigning dividend payouts to players.
 * @see IUDividendVotingProposalEvent
 * @see IUDividendVotingEvent
 * @author dragonfly
 */
public interface IUDividendPayoutEvent extends IUGameEvent {
	
	/**
	 * Company this dividend payout is related to.
	 * @return Company identifier.
	 */
	long getCompanyId();
	
	/**
	 * Dividend payout value for this recipient. 
	 * <p>Upon event arrival this sum of money should be added to 
	 * recipient player's cash. Suggested way to calculate this value
	 * is to use the following formula:
	 * <listing>
	 * 	share_fraction = number_of_shares_recipient_has / total_number_of_shares;	// for this company
	 * 	dividend_payout_value = share_fraction * max(0, company_profit) * dividend_payout_ratio; 
	 * </listing>
	 * </p>
	 * @return Dividend payout value.
	 */
	@Nonnegative
	double getDividendPayoutValue();
	
	/**
	 * Total company profit after this turn.
	 * @return Total company profit.
	 */
	double getCompanyProfit();
	
	/**
	 * Dividend payout ratio for this company. Usually this value 
	 * is determined via DPR voting procedure.
	 * @see IUDividendVotingEvent
	 * @return Dividend payout ratio.
	 */
	@Nonnegative
	double getDividendPayoutRatio();

}

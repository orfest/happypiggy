package ru.nsu.ccfit.pm.econ.common.engine.events;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Unmodifiable interface for signaling turn end event.
 * <p>Normally game engine would fire this event on its
 * own. That way rating list ({@link #getRatingList()})
 * would be filled. But Teacher also can end current 
 * turn prematurely. That way rating list should be 
 * ignored by engine. When engine receives such event 
 * from Teacher it resends it to all players (including 
 * Teacher) with rating list filled out.</p>
 * @author dragonfly
 */
public interface IUTurnEndEvent extends IUGameEvent {
	
	/**
	 * List of all player rating values. All players must 
	 * be present in the list (one and only one time). List 
	 * is ordered by rating value in descending order. Rating
	 * values may not be actual ones, it is the ordering that 
	 * is important.
	 * @return List of all player rating values by the end of 
	 * 			the turn. Or <tt>null</tt> if event is not 
	 * 			sent by engine.
	 */
	@Nullable
	List<IUPlayerRatingValue> getRatingList();
	
	/**
	 * Unmodifiable interface to hold player rating value.
	 * @author dragonfly
	 */
	public interface IUPlayerRatingValue {
		
		/**
		 * Player in question.
		 * @return Player identifier.
		 */
		long getPlayerId();
		
		/**
		 * Player rating value. 
		 * <p>Rating may be calculated via following formula:
		 * <listing>
		 * 	// kinda Python
		 * 	share_value = sum(company.market_share_value * player.share_holding(company).shares_amount for company in companies);
		 * 	loan_value = sum(loan.initial_value for loan in player.loans);
		 * 	deposit_value = sum(deposit.initial_value for deposit in player.deposits);
		 * 	rating = cash + share_value + loan_value - deposit_value;
		 * </listing>
		 * </p>
		 * @return Player rating value.
		 */
		double getRatingValue();
		
	}

}

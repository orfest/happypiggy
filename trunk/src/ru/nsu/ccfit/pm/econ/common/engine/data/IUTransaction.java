package ru.nsu.ccfit.pm.econ.common.engine.data;

/**
 * Unmodifiable interface for share holding transaction data objects.
 * @author dragonfly
 */
public interface IUTransaction {
	
	/**
	 * Transaction time. 
	 * <p>Since transactions always take place during the turn 
	 * {@link IUGameTime#isTurnFinished()} should be <tt>false</tt>.</p>
	 * @return Transaction game time.
	 */
	IUGameTime getTime();
	
	/**
	 * Transaction object - the share holding.
	 * @return Share holding to pass.
	 */
	IUShareHolding getShareHolding();
	
	/**
	 * Seller.
	 * @return Seller player identifier.
	 */
	long getSellerId();
	
	/**
	 * Buyer.
	 * @return Buyer player identifier.
	 */
	long getBuyerId();
	
	/**
	 * Transaction value (i.e. sum of money seller gets and buyer pays 
	 * for the transaction).
	 * @return Transaction value (cost).
	 */
	double getValue();

}

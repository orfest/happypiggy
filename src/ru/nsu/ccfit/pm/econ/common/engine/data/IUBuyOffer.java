package ru.nsu.ccfit.pm.econ.common.engine.data;

import javax.annotation.Nonnegative;

/**
 * Unmodifiable interface for buy offer data objects.
 * @author dragonfly
 */
public interface IUBuyOffer {
	
	/**
	 * Share holding for sale.
	 * @return Share holding.
	 */
	IUShareHolding getShareHolding();
	
	/**
	 * Seller identifier. This value should match {@link IUShareHolding#getOwnerId()}.
	 * @return Seller player identifier.
	 */
	long getSellerId();
	
	/**
	 * Suggested price for share holding.
	 * @return Suggested price.
	 */
	@Nonnegative
	double getSuggestedValue();

}

package ru.nsu.ccfit.pm.econ.common.engine.events;

import java.util.Collection;

import javax.annotation.Nonnegative;

/**
 * Unmodifiable interface for passing share allocation requests
 * during initial allocation (before game start).
 * @see IUTransactionEvent
 * @author dragonfly
 */
public interface IUShareAllocationRequestEvent extends IUGameEvent {
	
	/**
	 * Share requests from sender player. Not all companies must 
	 * be present among requests.
	 * @return Collection of share requests.
	 */
	Collection<IUShareAllocationRequest> getRequests();
	
	/**
	 * Unmodifiable interface which represents share allocation
	 * request for one company from single player.
	 * @author dragonfly
	 */
	public interface IUShareAllocationRequest {
		
		/**
		 * Company, share request is related to.
		 * @return Company identifier.
		 */
		long getCompanyId();
		
		/**
		 * Requested amount of shares. Should not exceed total 
		 * amount of shares for given company. 
		 * @return Requested amount of shares.
		 */
		@Nonnegative
		int getRequestedShareAmount();
		
	}

}

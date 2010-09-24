package ru.nsu.ccfit.pm.econ.common.engine.roles;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUDeposit;

/**
 * Unmodifiable interface denoting players that are able to make deposits.
 * @author dragonfly
 */
public interface IUDepositMaker extends IUPlayer {
	
	/**
	 * Unexpired player deposits.
	 * @return Collection of player deposits.
	 */
	Collection<? extends IUDeposit> getUnmodifiableDepositList();

}

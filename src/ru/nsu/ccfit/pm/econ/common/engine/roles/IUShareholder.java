package ru.nsu.ccfit.pm.econ.common.engine.roles;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;

/**
 * Unmodifiable interface denoting players that may have some shares in their
 * possession.
 * 
 * @author dragonfly
 */
public interface IUShareholder extends IUPlayer {

	/**
	 * List of shares this player owns. For each company player has at most one
	 * share holding (i.e. share holdings are not partitioned for a given
	 * company).
	 * 
	 * @return Collection of share holdings this player owns.
	 */
	Collection<? extends IUShareHolding> getUnmodifiableShareList();

}

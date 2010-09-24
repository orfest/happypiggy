/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.roles;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

/**
 * IUPlayer implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUPlayer
 */
public class PlayerEngine implements IUPlayer {

	private long id;
	private PersonDescriptionEngine unmodifiablePersonDescription;

	public PlayerEngine(long id,
			PersonDescriptionEngine unmodifiablePersonDescription) {
		this.id = id;
		this.unmodifiablePersonDescription = unmodifiablePersonDescription;
	}

	public PlayerEngine(IUPlayer toCopy) {
		id = toCopy.getId();
		unmodifiablePersonDescription = new PersonDescriptionEngine(toCopy
				.getUnmodifiablePersonDescription());
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public IUPersonDescription getUnmodifiablePersonDescription() {
		return unmodifiablePersonDescription;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUnmodifiablePersonDescription(
			PersonDescriptionEngine unmodifiablePersonDescription) {
		this.unmodifiablePersonDescription = unmodifiablePersonDescription;
	}

}

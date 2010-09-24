package ru.nsu.ccfit.pm.econ.engine.roles;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IULimitedBudgetPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;

/**
 * IULimitedBudgetPlayer interface implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IULimitedBudgetPlayer
 */
public class LimitedBudgetPlayerEngine implements IULimitedBudgetPlayer {

	private double cash;
	private long id;
	private PersonDescriptionEngine personDescription;

	public LimitedBudgetPlayerEngine(double cash, long id,
			PersonDescriptionEngine personDescription) {
		this.cash = cash;
		this.id = id;
		this.personDescription = personDescription;
	}

	public LimitedBudgetPlayerEngine(IULimitedBudgetPlayer toCopy) {
		cash = toCopy.getCash();
		id = toCopy.getId();
		personDescription = new PersonDescriptionEngine(toCopy
				.getUnmodifiablePersonDescription());
	}

	@Override
	public double getCash() {
		return cash;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public IUPersonDescription getUnmodifiablePersonDescription() {
		return (IUPersonDescription) personDescription;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PersonDescriptionEngine getPersonDescription() {
		return personDescription;
	}

	public void setPersonDescription(PersonDescriptionEngine personDescription) {
		this.personDescription = personDescription;
	}

}

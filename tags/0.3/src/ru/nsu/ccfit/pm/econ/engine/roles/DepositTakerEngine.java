/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUDeposit;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUDepositTaker;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.engine.data.DepositEngine;

/**
 * IUDepositTaker implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUDepositTaker
 */
public class DepositTakerEngine implements IUDepositTaker {

	private double depositInterestRate;
	private Collection<DepositEngine> takenDepositsList;
	private long id;
	private PersonDescriptionEngine personDescription;

	public DepositTakerEngine(double depositInterestRate,
			Collection<DepositEngine> takenDepositsList, long id,
			PersonDescriptionEngine personDescription) {
		this.depositInterestRate = depositInterestRate;
		this.takenDepositsList = takenDepositsList;
		this.id = id;
		this.personDescription = personDescription;
	}

	public DepositTakerEngine(IUDepositTaker toCopy) {
		depositInterestRate = toCopy.getDepositInterestRate();
		takenDepositsList = new ArrayList<DepositEngine>(toCopy
				.getUnmodifiableTakenDepositsList().size());
		for (IUDeposit d : toCopy.getUnmodifiableTakenDepositsList())
			takenDepositsList.add(new DepositEngine(d));
		id = toCopy.getId();
		personDescription = new PersonDescriptionEngine(toCopy
				.getUnmodifiablePersonDescription());
	}

	@Override
	public double getDepositInterestRate() {
		return depositInterestRate;
	}

	@Override
	public Collection<? extends IUDeposit> getUnmodifiableTakenDepositsList() {
		return Collections.unmodifiableCollection(takenDepositsList);
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public IUPersonDescription getUnmodifiablePersonDescription() {
		return (IUPersonDescription) personDescription;
	}

	public void setDepositInterestRate(double depositInterestRate) {
		this.depositInterestRate = depositInterestRate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<DepositEngine> getTakenDepositsList() {
		return takenDepositsList;
	}

	public PersonDescriptionEngine getPersonDescription() {
		return personDescription;
	}

	public void setTakenDepositsList(Collection<DepositEngine> takenDepositsList) {
		this.takenDepositsList = takenDepositsList;
	}

	public void setPersonDescription(PersonDescriptionEngine personDescription) {
		this.personDescription = personDescription;
	}

}

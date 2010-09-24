package ru.nsu.ccfit.pm.econ.engine.roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUDeposit;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUDepositMaker;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.engine.data.DepositEngine;

/**
 * IUDepositMaker implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUDepositMaker
 */
public class DepositMakerEngine implements IUDepositMaker {

	private Collection<DepositEngine> depositList;
	private long id;
	private PersonDescriptionEngine personDescription;

	public DepositMakerEngine(Collection<DepositEngine> depositList, long id,
			PersonDescriptionEngine personDescription) {
		this.depositList = depositList;
		this.id = id;
		this.personDescription = personDescription;
	}

	public DepositMakerEngine(IUDepositMaker toCopy) {
		depositList = new ArrayList<DepositEngine>(toCopy
				.getUnmodifiableDepositList().size());
		for (IUDeposit d : toCopy.getUnmodifiableDepositList())
			depositList.add(new DepositEngine(d));
		id = toCopy.getId();
		personDescription = new PersonDescriptionEngine(toCopy
				.getUnmodifiablePersonDescription());
	}

	@Override
	public Collection<? extends IUDeposit> getUnmodifiableDepositList() {
		return Collections.unmodifiableCollection(depositList);
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public IUPersonDescription getUnmodifiablePersonDescription() {
		return (IUPersonDescription) personDescription;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<DepositEngine> getDepositList() {
		return depositList;
	}

	public PersonDescriptionEngine getPersonDescription() {
		return personDescription;
	}

	public void setDepositList(Collection<DepositEngine> depositList) {
		this.depositList = depositList;
	}

	public void setPersonDescription(PersonDescriptionEngine personDescription) {
		this.personDescription = personDescription;
	}

}

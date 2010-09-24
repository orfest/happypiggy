package ru.nsu.ccfit.pm.econ.engine.roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IULoanTaker;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.engine.data.LoanEngine;

/**
 * IULoanTaker implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IULoanTaker
 */
public class LoanTakerEngine implements IULoanTaker {

	private double loanInterestRate;
	private Collection<LoanEngine> takenLoansList;
	private long id;
	private PersonDescriptionEngine personDescription;

	public LoanTakerEngine(double loanInterestRate,
			Collection<LoanEngine> takenLoansList, long id,
			PersonDescriptionEngine personDescription) {
		this.loanInterestRate = loanInterestRate;
		this.takenLoansList = takenLoansList;
		this.id = id;
		this.personDescription = personDescription;
	}

	public LoanTakerEngine(IULoanTaker toCopy) {
		loanInterestRate = toCopy.getLoanInterestRate();
		takenLoansList = new ArrayList<LoanEngine>(toCopy
				.getUnmodifiableTakenLoansList().size());
		for (IULoan l : toCopy.getUnmodifiableTakenLoansList())
			takenLoansList.add(new LoanEngine(l));
		id = toCopy.getId();
		personDescription = new PersonDescriptionEngine(toCopy
				.getUnmodifiablePersonDescription());
	}

	@Override
	public double getLoanInterestRate() {
		return loanInterestRate;
	}

	@Override
	public Collection<? extends IULoan> getUnmodifiableTakenLoansList() {
		return Collections.unmodifiableCollection(takenLoansList);
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public IUPersonDescription getUnmodifiablePersonDescription() {
		return (IUPersonDescription) personDescription;
	}

	public void setLoanInterestRate(double loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<LoanEngine> getTakenLoansList() {
		return takenLoansList;
	}

	public PersonDescriptionEngine getPersonDescription() {
		return personDescription;
	}

	public void setTakenLoansList(Collection<LoanEngine> takenLoansList) {
		this.takenLoansList = takenLoansList;
	}

	public void setPersonDescription(PersonDescriptionEngine personDescription) {
		this.personDescription = personDescription;
	}

}

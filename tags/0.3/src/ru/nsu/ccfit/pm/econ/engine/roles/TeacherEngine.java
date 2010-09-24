package ru.nsu.ccfit.pm.econ.engine.roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUTeacher;
import ru.nsu.ccfit.pm.econ.engine.data.LoanEngine;

/**
 * IUTeacher implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUTeacher
 */
public class TeacherEngine implements IUTeacher {

	private long id;
	private PersonDescriptionEngine personDescription;
	private double loanInterestRate;
	private Collection<LoanEngine> takenLoansList;

	public TeacherEngine(long id, PersonDescriptionEngine personDescription,
			double loanInterestRate, Collection<LoanEngine> takenLoansList) {
		this.id = id;
		this.personDescription = personDescription;
		this.loanInterestRate = loanInterestRate;
		this.takenLoansList = takenLoansList;
	}

	public TeacherEngine(IUTeacher toCopy) {
		id = toCopy.getId();
		personDescription = new PersonDescriptionEngine(toCopy
				.getUnmodifiablePersonDescription());
		loanInterestRate = toCopy.getLoanInterestRate();
		takenLoansList = new ArrayList<LoanEngine>(toCopy
				.getUnmodifiableTakenLoansList().size());
		for (IULoan l : toCopy.getUnmodifiableTakenLoansList())
			takenLoansList.add(new LoanEngine(l));
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public IUPersonDescription getUnmodifiablePersonDescription() {
		return (IUPersonDescription) personDescription;
	}

	@Override
	public double getLoanInterestRate() {
		return loanInterestRate;
	}

	@Override
	public Collection<? extends IULoan> getUnmodifiableTakenLoansList() {
		return Collections.unmodifiableCollection(takenLoansList);
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLoanInterestRate(double loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

	public PersonDescriptionEngine getPersonDescription() {
		return personDescription;
	}

	public Collection<LoanEngine> getTakenLoansList() {
		return takenLoansList;
	}

	public void setPersonDescription(PersonDescriptionEngine personDescription) {
		this.personDescription = personDescription;
	}

	public void setTakenLoansList(Collection<LoanEngine> takenLoansList) {
		this.takenLoansList = takenLoansList;
	}

}

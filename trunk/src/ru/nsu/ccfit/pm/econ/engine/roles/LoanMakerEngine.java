/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IULoanMaker;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.engine.data.LoanEngine;

/**
 * IULoanMaker implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IULoanMaker
 */
public class LoanMakerEngine implements IULoanMaker {

	private Collection<LoanEngine> loanList;
	private long id;
	private PersonDescriptionEngine personDescription;

	public LoanMakerEngine(Collection<LoanEngine> loanList, long id,
			PersonDescriptionEngine personDescription) {
		this.loanList = loanList;
		this.id = id;
		this.personDescription = personDescription;
	}

	public LoanMakerEngine(IULoanMaker toCopy) {
		loanList = new ArrayList<LoanEngine>(toCopy.getUnmodifiableLoanList()
				.size());
		for (IULoan l : toCopy.getUnmodifiableLoanList())
			loanList.add(new LoanEngine(l));
		id = toCopy.getId();
		personDescription = new PersonDescriptionEngine(toCopy
				.getUnmodifiablePersonDescription());
	}

	@Override
	public Collection<? extends IULoan> getUnmodifiableLoanList() {
		return Collections.unmodifiableCollection(loanList);
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

	public Collection<LoanEngine> getLoanList() {
		return loanList;
	}

	public PersonDescriptionEngine getPersonDescription() {
		return personDescription;
	}

	public void setLoanList(Collection<LoanEngine> loanList) {
		this.loanList = loanList;
	}

	public void setPersonDescription(PersonDescriptionEngine personDescription) {
		this.personDescription = personDescription;
	}

}

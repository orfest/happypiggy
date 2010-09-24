package ru.nsu.ccfit.pm.econ.engine.roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUDeposit;
import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUStudent;
import ru.nsu.ccfit.pm.econ.engine.data.DepositEngine;
import ru.nsu.ccfit.pm.econ.engine.data.LoanEngine;

/**
 * IUStudent implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUStudent
 */
public class StudentEngine implements IUStudent {

	private long id;
	private PersonDescriptionEngine personDescription;
	private double cash;
	private Collection<LoanEngine> loanList;
	private Collection<DepositEngine> depositList;

	public StudentEngine(long id, PersonDescriptionEngine personDescription,
			double cash, Collection<LoanEngine> loanList,
			Collection<DepositEngine> depositList) {
		this.id = id;
		this.personDescription = personDescription;
		this.cash = cash;
		this.loanList = loanList;
		this.depositList = depositList;
	}

	public StudentEngine(IUStudent toCopy) {
		id = toCopy.getId();
		personDescription = new PersonDescriptionEngine(toCopy
				.getUnmodifiablePersonDescription());
		cash = toCopy.getCash();
		loanList = new ArrayList<LoanEngine>(toCopy.getUnmodifiableLoanList()
				.size());
		for (IULoan l : toCopy.getUnmodifiableLoanList())
			loanList.add(new LoanEngine(l));
		depositList = new ArrayList<DepositEngine>(toCopy
				.getUnmodifiableDepositList().size());
		for (IUDeposit d : toCopy.getUnmodifiableDepositList())
			depositList.add(new DepositEngine(d));
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
	public double getCash() {
		return cash;
	}

	@Override
	public Collection<? extends IULoan> getUnmodifiableLoanList() {
		return Collections.unmodifiableCollection(loanList);
	}

	@Override
	public Collection<? extends IUDeposit> getUnmodifiableDepositList() {
		return Collections.unmodifiableCollection(depositList);
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public PersonDescriptionEngine getPersonDescription() {
		return personDescription;
	}

	public Collection<LoanEngine> getLoanList() {
		return loanList;
	}

	public Collection<DepositEngine> getDepositList() {
		return depositList;
	}

	public void setPersonDescription(PersonDescriptionEngine personDescription) {
		this.personDescription = personDescription;
	}

	public void setLoanList(Collection<LoanEngine> loanList) {
		this.loanList = loanList;
	}

	public void setDepositList(Collection<DepositEngine> depositList) {
		this.depositList = depositList;
	}

}

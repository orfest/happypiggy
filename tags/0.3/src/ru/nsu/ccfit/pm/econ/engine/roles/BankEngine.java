package ru.nsu.ccfit.pm.econ.engine.roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUDeposit;
import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUBank;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.engine.data.DepositEngine;
import ru.nsu.ccfit.pm.econ.engine.data.LoanEngine;

/**
 * IUBank interface implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUBank
 */
public class BankEngine implements IUBank {

	private String bankName;
	private long id;
	private PersonDescriptionEngine personDescription;
	private double cash;
	private Collection<LoanEngine> loanList;
	private double loanInterestRate;
	private Collection<LoanEngine> takenLoansList;
	private double depositInterestRate;
	private Collection<DepositEngine> takenDepositsList;

	public BankEngine(String bankName, long id,
			PersonDescriptionEngine personDescription, double cash,
			Collection<LoanEngine> loanList, double loanInterestRate,
			Collection<LoanEngine> takenLoansList, double depositInterestRate,
			Collection<DepositEngine> takenDepositsList) {
		this.bankName = bankName;
		this.id = id;
		this.personDescription = personDescription;
		this.cash = cash;
		this.loanList = loanList;
		this.loanInterestRate = loanInterestRate;
		this.takenLoansList = takenLoansList;
		this.depositInterestRate = depositInterestRate;
		this.takenDepositsList = takenDepositsList;
	}

	public BankEngine(IUBank toCopy) {
		bankName = toCopy.getBankName();
		id = toCopy.getId();
		personDescription = new PersonDescriptionEngine(toCopy
				.getUnmodifiablePersonDescription());
		cash = toCopy.getCash();
		loanList = new ArrayList<LoanEngine>(toCopy.getUnmodifiableLoanList()
				.size());
		for (IULoan l : toCopy.getUnmodifiableLoanList())
			loanList.add(new LoanEngine(l));
		loanInterestRate = toCopy.getLoanInterestRate();
		takenLoansList = new ArrayList<LoanEngine>(toCopy
				.getUnmodifiableTakenLoansList().size());
		for (IULoan l : toCopy.getUnmodifiableTakenLoansList())
			takenLoansList.add(new LoanEngine(l));
		depositInterestRate = toCopy.getDepositInterestRate();
		takenDepositsList = new ArrayList<DepositEngine>(toCopy
				.getUnmodifiableTakenDepositsList().size());
		for (IUDeposit d : toCopy.getUnmodifiableTakenDepositsList())
			takenDepositsList.add(new DepositEngine(d));
	}

	@Override
	public String getBankName() {
		return bankName;
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
	public double getLoanInterestRate() {
		return loanInterestRate;
	}

	@Override
	public Collection<? extends IULoan> getUnmodifiableTakenLoansList() {
		return Collections.unmodifiableCollection(takenLoansList);
	}

	@Override
	public double getDepositInterestRate() {
		return depositInterestRate;
	}

	@Override
	public Collection<? extends IUDeposit> getUnmodifiableTakenDepositsList() {
		return Collections.unmodifiableCollection(takenDepositsList);
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public void setLoanInterestRate(double loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

	public void setDepositInterestRate(double depositInterestRate) {
		this.depositInterestRate = depositInterestRate;
	}

	public PersonDescriptionEngine getPersonDescription() {
		return personDescription;
	}

	public Collection<LoanEngine> getLoanList() {
		return loanList;
	}

	public Collection<LoanEngine> getTakenLoansList() {
		return takenLoansList;
	}

	public Collection<DepositEngine> getTakenDepositsList() {
		return takenDepositsList;
	}

	public void setPersonDescription(PersonDescriptionEngine personDescription) {
		this.personDescription = personDescription;
	}

	public void setLoanList(Collection<LoanEngine> loanList) {
		this.loanList = loanList;
	}

	public void setTakenLoansList(Collection<LoanEngine> takenLoansList) {
		this.takenLoansList = takenLoansList;
	}

	public void setTakenDepositsList(Collection<DepositEngine> takenDepositsList) {
		this.takenDepositsList = takenDepositsList;
	}

}

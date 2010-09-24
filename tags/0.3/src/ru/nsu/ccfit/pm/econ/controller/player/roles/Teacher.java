package ru.nsu.ccfit.pm.econ.controller.player.roles;

import java.util.ArrayList;
import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUTeacher;

public class Teacher extends Shareholder implements IUTeacher {
	
	private double loanInterestRate = 0;
	
	public Teacher() {
	}
	
	public Teacher(IUTeacher other) {
		super((IUPlayer)other);
		this.loanInterestRate = other.getLoanInterestRate();
	}

	@Override
	public double getLoanInterestRate() {
		return loanInterestRate;
	}

	@Override
	public Collection<? extends IULoan> getUnmodifiableTakenLoansList() {
		// TODO stub, until bank support arrives
		return new ArrayList<IULoan>();
	}

	public void setLoanInterestRate(double loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

}

package ru.nsu.ccfit.pm.econ.controller.player.roles;

import java.util.ArrayList;
import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUDeposit;
import ru.nsu.ccfit.pm.econ.common.engine.data.IULoan;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUStudent;

public class Student extends Shareholder implements IUStudent {
	
	private double cash = 0;
	
	public Student() {
	}
	
	public Student(IUStudent other) {
		super((IUPlayer)other);
		this.cash = other.getCash();
	}

	@Override
	public double getCash() {
		return cash;
	}

	@Override
	public Collection<? extends IULoan> getUnmodifiableLoanList() {
		// TODO stub, until bank support arrives
		return new ArrayList<IULoan>();
	}

	@Override
	public Collection<? extends IUDeposit> getUnmodifiableDepositList() {
		// TODO stub, until bank support arrives
		return new ArrayList<IUDeposit>();
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

}

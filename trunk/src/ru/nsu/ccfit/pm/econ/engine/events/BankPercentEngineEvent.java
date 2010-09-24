package ru.nsu.ccfit.pm.econ.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankPercentEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

/**
 * {@link IUBankPercentEvent} interface implementation for engine
 * 
 * @author pupatenko
 * 
 */
public class BankPercentEngineEvent extends GameEventEngine implements
		IUBankPercentEvent {

	private double depositInterestRate;
	private double loanInterestRate;

	public BankPercentEngineEvent(IUGameEvent event,
			double depositInterestRate, double loanInterestRate) {
		super(event);
		this.depositInterestRate = depositInterestRate;
		this.loanInterestRate = loanInterestRate;
	}

	public BankPercentEngineEvent(IUBankPercentEvent toCopy) {
		super(toCopy.getEventTime(), toCopy.getReceiverIds(), toCopy
				.getSenderId(), toCopy.isBroadcast());
		depositInterestRate = toCopy.getDepositInterestRate();
		loanInterestRate = toCopy.getLoanInterestRate();
	}

	@Override
	public double getDepositInterestRate() {
		return depositInterestRate;
	}

	@Override
	public double getLoanInterestRate() {
		return loanInterestRate;
	}
}

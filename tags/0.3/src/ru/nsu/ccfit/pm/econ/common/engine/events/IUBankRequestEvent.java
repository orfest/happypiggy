package ru.nsu.ccfit.pm.econ.common.engine.events;

import javax.annotation.Nonnegative;

import ru.nsu.ccfit.pm.econ.common.engine.data.BankTransactionType;

/**
 * Unmodifiable interface for transferring transaction requests 
 * to banks. Players use this interface to loan/deposit money 
 * in banks. Banks may use this interface to loan money from 
 * Teacher.
 * @author dragonfly
 */
public interface IUBankRequestEvent extends IUGameEvent {
	
	/**
	 * Desired transaction type. One of {@link BankTransactionType#LOAN}
	 * or {@link BankTransactionType#DEPOSIT}.
	 * @return Requested transaction type.
	 */
	BankTransactionType getTransactionType();
	
	/**
	 * Initial sum of money for this transaction. E.g. if you are willing
	 * to loan 10$ for 3 turns under 10%, this value would be 10$.
	 * @return Initial sum of money for the transaction.
	 */
	@Nonnegative
	double getInitialValue();
	
	/**
	 * Period in turns before transaction (loan or deposit) repayment.
	 * @return Transaction period in turns.
	 */
	@Nonnegative
	int getPeriod();

}

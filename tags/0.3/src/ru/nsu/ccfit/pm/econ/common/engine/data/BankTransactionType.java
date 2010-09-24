package ru.nsu.ccfit.pm.econ.common.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankRequestEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankTransactionEvent;

/**
 * Possible types of transactions with banks.
 * @see IUBankRequestEvent
 * @see IUBankTransaction
 * @see IUBankTransactionEvent
 * @author dragonfly
 */
public enum BankTransactionType {
	
	/**
	 * The transaction is making a loan in bank.
	 */
	LOAN,
	
	/**
	 * The transaction is making a deposit in bank.
	 */
	DEPOSIT,
	
	/**
	 * The transaction is repaying a loan to bank.
	 */
	LOAN_REPAY,
	
	/**
	 * The transaction is repaying a deposit to player. 
	 */
	DEPOSIT_REPAY,

}

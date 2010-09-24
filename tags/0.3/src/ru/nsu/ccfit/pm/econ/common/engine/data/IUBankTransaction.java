package ru.nsu.ccfit.pm.econ.common.engine.data;

/**
 * Unmodifiable interface for bank transaction data objects.
 * @author dragonfly
 */
public interface IUBankTransaction {
	
	/**
	 * Transaction type. Either {@link BankTransactionType#LOAN} or
	 * {@link BankTransactionType#DEPOSIT}.
	 * @return Bank transaction type.
	 */
	BankTransactionType getType();
	
	/**
	 * Issue turn number. Money were issued just before this turn.
	 * @return Issue turn number.
	 */
	int getIssueTurn();
	
	/**
	 * Repay turn number. Money would be returned just before this turn.
	 * @return Repay turn number.
	 */
	int getRepayTurn();
	
	/**
	 * Bank.
	 * @return Bank (bank or Teacher) player identifier.
	 */
	long getBankId();
	
	/**
	 * Bank client. The one who requested this transaction.
	 * @return Bank client (player or bank) player identifier.
	 */
	long getClientId();
	
	/**
	 * Initial sum of money for this transaction. I.e. sum of
	 * money that was issued.
	 * @return Initial sum of money.
	 */
	double getInitialValue();
	
	/**
	 * Sum of money that would be repayed.
	 * @return Repay sum of money.
	 */
	double getRepayValue();
	
	/**
	 * Bank interest rate for specified transaction type on 
	 * this transaction (i.e. during the turn before issue turn).
	 * @return Bank interest rate for this transaction.
	 */
	double getInterestRate();

}

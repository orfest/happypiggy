package ru.nsu.ccfit.pm.econ.common.engine.data;

/**
 * Unmodifiable interface for loan data objects.
 * @author dragonfly
 */
public interface IULoan extends IUBankTransaction {
	
	/**
	 * Transaction type. Must be {@link BankTransactionType#LOAN}.
	 * @return Bank transaction type.
	 */
	@Override
	BankTransactionType getType();

}

package ru.nsu.ccfit.pm.econ.common.engine.data;

/**
 * Unmodifiable interface for deposit data objects.
 * @author dragonfly
 */
public interface IUDeposit extends IUBankTransaction {
	
	/**
	 * Transaction type. Must be {@link BankTransactionType#DEPOSIT}.
	 * @return Bank transaction type.
	 */
	@Override
	BankTransactionType getType();

}

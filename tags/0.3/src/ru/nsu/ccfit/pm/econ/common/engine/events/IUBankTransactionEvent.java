package ru.nsu.ccfit.pm.econ.common.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.data.BankTransactionType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBankTransaction;

/**
 * Unmodifiable interface for making bank transactions.
 * <p>For any transaction type issuing bank should be event
 * initiator, i.e. be event sender.</p>
 * @author dragonfly
 */
public interface IUBankTransactionEvent extends IUGameEvent {
	
	/**
	 * Transaction type.
	 * <p>Transaction type may be one of the following:
	 * <ul>
	 * <li>{@link BankTransactionType#LOAN}</li>
	 * <li>{@link BankTransactionType#DEPOSIT}</li>
	 * <li>{@link BankTransactionType#LOAN_REPAY}</li>
	 * <li>{@link BankTransactionType#DEPOSIT_REPAY}</li>
	 * </ul>
	 * </p>
	 * {@link IUBankTransaction#getType()} value should correspond to
	 * this value. E.g. if one is <tt>LOAN</tt> the other may be either
	 * <tt>LOAN</tt> or <tt>LOAN_REPAY</tt>.
	 * @return Transaction type.
	 */
	BankTransactionType getTransactionType();
	
	/**
	 * The underlying transaction that caused this event.
	 * @return Transaction object.
	 */
	IUBankTransaction getTransaction();

}

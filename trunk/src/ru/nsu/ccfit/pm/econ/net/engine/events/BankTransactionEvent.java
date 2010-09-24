package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.data.BankTransactionType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBankTransaction;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankTransactionEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.BankTransaction;

/**
 * @author orfest
 * 
 */
public class BankTransactionEvent extends GameEvent implements
		IUBankTransactionEvent {

	@SerializeThis
	private BankTransactionType transactionType = BankTransactionType.DEPOSIT;
	@SerializeThis
	private IUBankTransaction bankTransaction = new BankTransaction();

	public BankTransactionEvent(){
	}

	public BankTransactionType getTransactionType() {
		return transactionType;
	}

	public IUBankTransaction getTransaction() {
		return bankTransaction;
	}

	public IUBankTransaction getBankTransaction() {
		return bankTransaction;
	}

	public void setTransactionType(BankTransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public void setBankTransaction(IUBankTransaction transaction) {
		this.bankTransaction = transaction;
	}

}

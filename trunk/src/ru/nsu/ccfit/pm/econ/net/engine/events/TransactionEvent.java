package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUTransaction;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTransactionEvent;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.Transaction;

/**
 * @author orfest
 * 
 */
public class TransactionEvent extends GameEvent implements IUTransactionEvent {
	
	@SerializeThis
	private IUTransaction transaction = new Transaction();

	public TransactionEvent(){
	}

	public IUTransaction getTransaction(){
		return transaction;
	}

	public void setTransaction(IUTransaction transaction) {
		this.transaction = transaction;
	}

}

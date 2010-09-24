package ru.nsu.ccfit.pm.econ.engine.events;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTransaction;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTransactionEvent;
import ru.nsu.ccfit.pm.econ.engine.data.TransactionEngine;

/**
 * {@link IUTransactionEvent} interface implementation for engine
 * 
 * @author pupatenko
 * 
 */
public class TransactionEventEngine extends GameEventEngine implements
		IUTransactionEvent {

	private TransactionEngine transaction;

	public TransactionEventEngine(IUTransactionEvent other) {
		super((IUGameEvent) other);

		transaction = new TransactionEngine(other.getTransaction());
	}

	public TransactionEventEngine(IUGameTime eventTime,
			Collection<Long> recieverIds, long senderId,
			IUTransaction transaction, boolean broadcast) {
		super(eventTime, recieverIds, senderId, broadcast);
		this.transaction = new TransactionEngine(transaction);
	}

	@Override
	public IUTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(IUTransaction tr) {
		transaction = new TransactionEngine(tr);
	}

}

package ru.nsu.ccfit.pm.econ.net.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTransaction;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.TransactionProto;

/**
 * @author orfest
 * 
 */
@ProtoClass(value = TransactionProto.class)
public class Transaction implements IUTransaction {

	@SerializeThis
	private long buyerId = Long.MAX_VALUE;
	@SerializeThis
	private long sellerId = Long.MAX_VALUE;
	@SerializeThis
	private IUShareHolding shareHolding = new ShareHolding();
	@SerializeThis
	private IUGameTime time = new GameTime();
	@SerializeThis
	private double value = Double.MAX_VALUE;

	public Transaction() {
	}

	@Override
	public long getBuyerId() {
		return buyerId;
	}

	@Override
	public long getSellerId() {
		return sellerId;
	}

	@Override
	public IUShareHolding getShareHolding() {
		return shareHolding;
	}

	@Override
	public IUGameTime getTime() {
		return time;
	}

	@Override
	public double getValue() {
		return value;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public void setShareHolding(IUShareHolding shareHolding) {
		this.shareHolding = shareHolding;
	}

	public void setTime(IUGameTime time) {
		this.time = time;
	}

	public void setValue(double value) {
		this.value = value;
	}

}

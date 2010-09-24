/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTransaction;

/**
 * IUTransaction implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUTransaction
 */
public class TransactionEngine implements IUTransaction {

	private long buyerId;
	private long sellerId;
	private ShareHoldingEngine shareHolding;
	private GameTimeEngine time;
	private double value;

	public TransactionEngine(long buyerId, long sellerId,
			ShareHoldingEngine shareHolding, GameTimeEngine time, double value) {
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.shareHolding = new ShareHoldingEngine(shareHolding);
		this.time = new GameTimeEngine(time);
		this.value = value;
	}

	public TransactionEngine(IUTransaction toCopy) {
		this.buyerId = toCopy.getBuyerId();
		this.sellerId = toCopy.getSellerId();
		this.shareHolding = new ShareHoldingEngine(toCopy.getShareHolding());
		this.time = new GameTimeEngine(toCopy.getTime());
		this.value = toCopy.getValue();
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

	public void setShareHolding(ShareHoldingEngine shareHolding) {
		this.shareHolding = shareHolding;
	}

	public void setTime(GameTimeEngine time) {
		this.time = time;
	}

	public void setValue(double value) {
		this.value = value;
	}

}

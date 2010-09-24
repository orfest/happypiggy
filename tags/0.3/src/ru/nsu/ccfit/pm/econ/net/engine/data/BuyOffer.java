package ru.nsu.ccfit.pm.econ.net.engine.data;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.BuyOfferProto;

/**
 * @author orfest
 * 
 */
@ProtoClass(value = BuyOfferProto.class)
public class BuyOffer implements IUBuyOffer {

	@SerializeThis
	private double suggestedValue = Double.MAX_VALUE;
	@SerializeThis
	private IUShareHolding shareHolding = new ShareHolding();
	@SerializeThis
	private long sellerId = Long.MAX_VALUE;

	@Override
	public long getSellerId() {
		return sellerId;
	}

	@Override
	public IUShareHolding getShareHolding() {
		return shareHolding;
	}

	@Override
	public double getSuggestedValue() {
		return suggestedValue;
	}

	public void setSellerId(long selledId) {
		this.sellerId = selledId;
	}

	public void setSuggestedValue(double suggestedValue) {
		this.suggestedValue = suggestedValue;
	}

	public void setShareHolding(IUShareHolding shareHolding) {
		this.shareHolding = shareHolding;
	}

}

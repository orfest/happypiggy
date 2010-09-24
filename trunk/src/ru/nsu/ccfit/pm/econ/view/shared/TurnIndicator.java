package ru.nsu.ccfit.pm.econ.view.shared;

import java.util.Date;

import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IULimitedBudgetPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUShareholder;
import ru.nsu.ccfit.pm.econ.common.view.IGameTimeListener;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerStatsUpdateListener;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.IFormatter;
import ru.nsu.ccfit.pm.econ.view.shared.wtkx.BooleanValue;
import ru.nsu.ccfit.pm.econ.view.shared.wtkx.DateValue;
import ru.nsu.ccfit.pm.econ.view.shared.wtkx.DoubleValue;
import ru.nsu.ccfit.pm.econ.view.shared.wtkx.IntegerValue;

public class TurnIndicator extends BoxPane 
		implements Bindable, InjectionVisitable, IGameTimeListener, IPlayerStatsUpdateListener {
	
	static final Logger logger = LoggerFactory.getLogger(TurnIndicator.class);
	
	/*
	 * WTKX data objects.
	 */
	@WTKX private DateValue time;
	@WTKX private BooleanValue turnFinished;
	@WTKX private IntegerValue turnNumber;
	@WTKX private DoubleValue moneyAmount;
	@WTKX private BooleanValue moneyVisible;
	
	/*
	 * Injectables.
	 */
	private GameTimeGateway gameTimeGateway;
	private MineStatsGateway playerGateway;
	private IFormatter formatter;

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");
		
		time.setValue(new Date(0));
	}
	
	@Inject
	public void setGameTimeGateway(GameTimeGateway gateway) {
		if (this.gameTimeGateway != null) {
			logger.warn("Redefining cncGateway");
			this.gameTimeGateway.getTurnChangeListeners().remove(this);
			this.gameTimeGateway.getGameClockListeners().remove(this);
		}
		
		this.gameTimeGateway = gateway;
		this.gameTimeGateway.getTurnChangeListeners().add(this);
		this.gameTimeGateway.getGameClockListeners().add(this);
	}
	
	@Inject
	public void setPlayerGateway(MineStatsGateway playerGateway) {
		if (this.playerGateway != null) {
			logger.warn("Redefining playerGateway");
			this.playerGateway.getPlayerStatsUpdateListeners().remove(this);
		}
		
		this.playerGateway = playerGateway;
		this.playerGateway.getPlayerStatsUpdateListeners().add(this);
	}
	
	@Inject
	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}
	
	public IFormatter getFormatter() {
		return this.formatter;
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
		// no children
	}

	@Override
	public void onGameStart() {
		// no action required
	}

	@Override
	public void onTickTock(Date curTime) {
		time.setValue(curTime);
	}

	@Override
	public void onTurnNumberChange(int newTurnNumber) {
		turnNumber.setVal(newTurnNumber);
	}

	@Override
	public void onTurnStateChange(boolean isFinished) {
		turnFinished.setVal(isFinished);
	}
	
	/**
	 * Whether timer countdown is normal (from 00:00) or reverse (to 00:00).
	 * @param isReverse <tt>true</tt> if should be reverse, 
	 * 					<tt>false</tt> otherwise.
	 */
	public void setReverseCountdown(boolean isReverse) {
		throw new UnsupportedOperationException("not implemented");
	}
	
	public boolean isReverseCountdown() {
		return false;
	}

	@Override
	public void onCashChange(IULimitedBudgetPlayer player, double oldCashValue, double cashValue) {
		moneyAmount.setVal(cashValue);
	}

	@Override
	public void onRoleDetermined(IUPlayer player) {
		moneyVisible.setVal(player instanceof IULimitedBudgetPlayer);
	}

	@Override
	public void onSharesChange(IUShareholder player, IUShareHolding updatedShareHolding, 
			int addedAmount, double subtractedMoney) {
		// no action required
	}

}

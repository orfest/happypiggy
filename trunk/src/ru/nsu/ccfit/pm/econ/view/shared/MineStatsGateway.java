package ru.nsu.ccfit.pm.econ.view.shared;

import java.util.Collection;

import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IMineStatsAccessor;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IULimitedBudgetPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUShareholder;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerStatsUpdateListener;

/**
 * Gateway for views to get access to player properties.
 * @see IMineStatsAccessor
 * @see IPlayerStatsUpdateListener
 * 
 * @author dragonfly
 */
public class MineStatsGateway implements IMineStatsAccessor, IPlayerStatsUpdateListener {
	
	static final Logger logger = LoggerFactory.getLogger(EconomicActivityGateway.class);
	
	protected PlayerStatsUpdateListenerList listeners = new PlayerStatsUpdateListenerList();
	private IMineStatsAccessor controller;

	@Override
	public IUPersonDescription getPersonDescription() {
		return controller.getPersonDescription();
	}

	@Override
	public long getPlayerId() {
		return controller.getPlayerId();
	}

	@Override
	public void setPersonDescription(String name, String group) {
		controller.setPersonDescription(name, group);
	}

	@Override
	public void setPersonDescription(IUPersonDescription pd) {
		controller.setPersonDescription(pd);
	}

	@Override
	public void notifyEngineOfPlayerPresence() {
		controller.notifyEngineOfPlayerPresence();
	}
	
	@Override
	public Collection<? extends IUShareHolding> getShares() {
		return controller.getShares();
	}

	@Override
	public void onCashChange(final IULimitedBudgetPlayer player, 
			final double oldCashValue, final double cashValue) {
		
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onCashChange(player, oldCashValue, cashValue);
			}
		});
	}

	@Override
	public void onRoleDetermined(final IUPlayer player) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onRoleDetermined(player);
			}
		});
	}

	@Override
	public void onSharesChange(final IUShareholder player, 
			final IUShareHolding updatedShareHolding, 
			final int addedAmount, final double subtractedMoney) {
		
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onSharesChange(player, updatedShareHolding, addedAmount, subtractedMoney);
			}
		});
	}
	
	public ListenerList<IPlayerStatsUpdateListener> getPlayerStatsUpdateListeners() {
		return listeners;
	}
	
	@Inject
	public void setMineStatsAcessor(IMineStatsAccessor controller) {
		if (this.controller != null)
			logger.warn("Redefining controller");
		this.controller = controller;
	}
	
	protected static class PlayerStatsUpdateListenerList 
		extends ListenerList<IPlayerStatsUpdateListener> implements IPlayerStatsUpdateListener {

		@Override
		public void onCashChange(IULimitedBudgetPlayer player, double oldCashValue, double cashValue) {
			for (IPlayerStatsUpdateListener listener : this)
				listener.onCashChange(player, oldCashValue, cashValue);
		}

		@Override
		public void onRoleDetermined(IUPlayer player) {
			for (IPlayerStatsUpdateListener listener : this)
				listener.onRoleDetermined(player);
		}

		@Override
		public void onSharesChange(IUShareholder player, IUShareHolding updatedShareHolding, 
				int addedAmount, double subtractedMoney) {
			
			for (IPlayerStatsUpdateListener listener : this)
				listener.onSharesChange(player, updatedShareHolding, addedAmount, subtractedMoney);
		}
		
	}

}

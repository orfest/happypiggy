package ru.nsu.ccfit.pm.econ.view.shared;

import java.util.Date;

import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IGameTime;
import ru.nsu.ccfit.pm.econ.common.view.IGameClockListener;
import ru.nsu.ccfit.pm.econ.common.view.ITurnChangeListener;

/**
 * Gateway for views to interact with <tt>PlayerController</tt>
 * "game time" features.
 * @see IGameTime
 * @see ITurnChangeListener
 * 
 * @author dragonfly
 */
public class GameTimeGateway implements IGameTime, ITurnChangeListener, IGameClockListener {
	
	static final Logger logger = LoggerFactory.getLogger(GameTimeGateway.class);
	
	private static final long ON_GAME_START_DELAY = 300;
	
	protected TurnChangeListenerList turnChangeListeners = new TurnChangeListenerList();
	protected GameClockListenerList gameClockListeners = new GameClockListenerList();
	private IGameTime controller;

	@Override
	public Date getTime() {
		return controller.getTime();
	}

	@Override
	public Date getTimeElapsedFromGameStart() {
		return controller.getTimeElapsedFromGameStart();
	}

	@Override
	public Date getTurnLength() {
		return controller.getTurnLength();
	}

	@Override
	public int getTurnNumber() {
		return controller.getTurnNumber();
	}

	@Override
	public boolean isGameStarted() {
		return controller.isGameStarted();
	}

	@Override
	public boolean isTurnFinished() {
		return controller.isTurnFinished();
	}

	@Override
	public void onGameStart() {
		DesktopApplicationContext.scheduleCallback(new Runnable() {
			@Override
			public void run() {
				turnChangeListeners.onGameStart();
			}
		}, ON_GAME_START_DELAY);
	}

	@Override
	public void onTickTock(final Date time) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				gameClockListeners.onTickTock(time);
			}
		});
	}

	@Override
	public void onTurnNumberChange(final int newTurnNumber) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				turnChangeListeners.onTurnNumberChange(newTurnNumber);
			}
		});
	}

	@Override
	public void onTurnStateChange(final boolean isFinished) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				turnChangeListeners.onTurnStateChange(isFinished);
			}
		});
	}
	
	public ListenerList<ITurnChangeListener> getTurnChangeListeners() {
		return turnChangeListeners;
	}
	
	public ListenerList<IGameClockListener> getGameClockListeners() {
		return gameClockListeners;
	}
	
	@Inject
	public void setGameTime(IGameTime controller) {
		if (this.controller != null)
			logger.warn("Redefining controller");
		this.controller = controller;
	}
	
	protected static class TurnChangeListenerList 
		extends ListenerList<ITurnChangeListener> implements ITurnChangeListener {

		@Override
		public void onGameStart() {
			for (ITurnChangeListener listener : this) 
				listener.onGameStart();
		}

		@Override
		public void onTurnNumberChange(int newTurnNumber) {
			for (ITurnChangeListener listener : this) 
				listener.onTurnNumberChange(newTurnNumber);
		}

		@Override
		public void onTurnStateChange(boolean isFinished) {
			for (ITurnChangeListener listener : this) 
				listener.onTurnStateChange(isFinished);
		}
		
	}
	
	protected static class GameClockListenerList
		extends ListenerList<IGameClockListener> implements IGameClockListener {
		
		@Override
		public void onTickTock(Date time) {
			for (IGameClockListener listener : this) 
				listener.onTickTock(time);
		}
		
	}

}

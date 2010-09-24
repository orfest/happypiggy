package ru.nsu.ccfit.pm.econ.controller.player;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IGameEventConsumer;
import ru.nsu.ccfit.pm.econ.common.controller.player.IGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnStartEvent;
import ru.nsu.ccfit.pm.econ.common.view.IGameClockListener;
import ru.nsu.ccfit.pm.econ.common.view.ITurnChangeListener;

import static com.google.common.base.Preconditions.checkNotNull;

public class GameTime extends AbstractGameEventGatewayClient implements
		IGameTime, IGameEventConsumer {
	
	static final Logger logger = LoggerFactory.getLogger(GameTime.class);
	
	/*
	 * Injectables.
	 */
	private ITurnChangeListener turnChangeListener;
	private IGameClockListener clockListener;
	private LocalState local;
	
	private Date gameStartTime = new Date();
	private Timer timer = new Timer("GameTime timer", true);
	
	@Inject
	public void setTurnChangeListener(ITurnChangeListener listener) {
		this.turnChangeListener = listener;
	}
	
	@Inject
	public void setGameClockListener(IGameClockListener listener) {
		this.clockListener = listener;
	}
	
	@Inject
	public void setLocal(LocalState local) {
		this.local = local;
	}

	@Override
	public Date getTime() {
		return local.getTimeSinceTurnStart();
	}

	@Override
	public Date getTimeElapsedFromGameStart() {
		Date now = new Date();
		long diff = now.getTime() - gameStartTime.getTime();
		return new Date(diff);
	}

	@Override
	public Date getTurnLength() {
		int minutes = checkNotNull(local.getScenarioProperties()).getTurnLengthMinutes();
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.MINUTE, minutes);
		return cal.getTime();
		
	}

	@Override
	public int getTurnNumber() {
		return local.getTurnNumber();
	}

	@Override
	public boolean isGameStarted() {
		return local.getTurnNumber() > 0;
	}

	@Override
	public boolean isTurnFinished() {
		return local.isTurnFinished();
	}

	@Override
	public boolean processEvent(IUGameEvent event) {
		if (event instanceof IUTurnStartEvent) {
			assert local.isTurnFinished();
			
			boolean gameJustStarted = false;
			if (local.getTurnNumber() == 0) {
				gameStartTime = new Date();
				gameJustStarted = true;
			}
			
			IUTurnStartEvent e = (IUTurnStartEvent)event;
			local.setTurnNumber(e.getNewTurnNumber());
			local.setTurnFinished(false);
			
			if (gameJustStarted) {
				turnChangeListener.onGameStart();
				startTickTock();
			}
			turnChangeListener.onTurnNumberChange(local.getTurnNumber());
			turnChangeListener.onTurnStateChange(local.isTurnFinished());
			
		} else if (event instanceof IUTurnEndEvent) {
			assert !local.isTurnFinished();
			
			local.setTurnFinished(true);
			turnChangeListener.onTurnStateChange(local.isTurnFinished());
			
		} else {
			return false;
		}
		
		return true;
	}
	
	private void startTickTock() {
		logger.debug("starting tick-tock clock");
		timer.schedule(new TickTockTimerTask(), 0, 1000);
	}
	
	protected class TickTockTimerTask extends TimerTask {

		@Override
		public void run() {
			clockListener.onTickTock(getTime());
		}
		
	}

}

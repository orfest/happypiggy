package ru.nsu.ccfit.pm.econ.controller.player.data;

import java.util.Date;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;

public class GameTime implements IUGameTime {
	
	public Date time;
	public int turnNumber;
	public boolean turnFinished;
	
	public GameTime(Date time, int turnNumber, boolean turnFinished) {
		this.time = time;
		this.turnNumber = turnNumber;
		this.turnFinished = turnFinished;
	}
	
	public GameTime(IUGameTime t) {
		this.time = (Date)t.getTime().clone();
		this.turnNumber = t.getTurnNumber();
		this.turnFinished = t.isTurnFinished();
	}

	@Override
	public Date getTime() {
		return time;
	}

	@Override
	public int getTurnNumber() {
		return turnNumber;
	}

	@Override
	public boolean isTurnFinished() {
		return turnFinished;
	}

}

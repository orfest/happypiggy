package ru.nsu.ccfit.pm.econ.common.controller.player;

import javax.annotation.Nonnegative;

public interface ITurnControl {
	
	@Nonnegative
	int getTurnNumber();
	
	boolean isTurnFinished();
	
	void finishTurn();
	
	void startTurn();

}

package ru.nsu.ccfit.pm.econ.controller.player;

import java.util.Date;
import java.util.TimeZone;

import com.google.gag.annotation.remark.Magic;
import com.google.gag.enumeration.MagicType;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenarioProperties;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUStudent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUTeacher;
import ru.nsu.ccfit.pm.econ.controller.player.data.GameTime;
import ru.nsu.ccfit.pm.econ.controller.player.data.ScenarioProperties;
import ru.nsu.ccfit.pm.econ.controller.player.roles.PersonDescription;
import ru.nsu.ccfit.pm.econ.controller.player.roles.Player;
import ru.nsu.ccfit.pm.econ.controller.player.roles.Student;
import ru.nsu.ccfit.pm.econ.controller.player.roles.Teacher;

/**
 * Holds local to PlayerController state.
 * @see IULocalState
 * @see IModifiableLocalState
 * 
 * @author dragonfly
 */
public class LocalState implements IULocalState, IModifiableLocalState {
	
	private boolean insideServer = false;
	private Player player = new Player();
	
	private int turnNumber = 0;
	private boolean turnFinished = true;
	private Date turnStartTime = new Date();
	
	private ScenarioProperties scenarioProperties;

	@Inject
	@Override
	public void setInsideServer(@Named("isInsideServer") boolean isServer) {
		insideServer = isServer;		
	}

	@Override
	public boolean isInsideServer() {
		return insideServer;
	}

	@Override
	public PersonDescription getModifiableMineDescription() {
		return player.getPersonDescription();
	}

	@Override
	public void setMineDescription(PersonDescription pd) {
		player.setPersonDescription(pd);
	}

	@Override
	public IUPersonDescription getMineDescription() {
		return player.getUnmodifiablePersonDescription();
	}

	@Override
	public void setMineId(long newId) {
		player.setId(newId);
	}

	@Override
	public long getMineId() {
		return player.getId();
	}

	@Override
	public IUPlayer getPlayer() {
		return (IUPlayer)player;
	}

	@Override
	public IUStudent getPlayerAsStudent() {
		return (IUStudent)player;
	}

	@Override
	public IUTeacher getPlayerAsTeacher() {
		return (IUTeacher)player;
	}

	@Override
	public Player getModifiablePlayer() {
		return player;
	}

	@Override
	public Student getModifiablePlayerAsStudent() {
		return (Student)player;
	}

	@Override
	public Teacher getModifiablePlayerAsTeacher() {
		return (Teacher)player;
	}

	@Override
	public void setPlayer(Player player) {
		this.player = player;	
	}

	@Override
	@Magic(type=MagicType.VOODOO)
	// OK, using Date objects for time offsets turned out 
	// to be quite a wrong idea...
	public Date getTimeSinceTurnStart() {
		// Beware: weird things may happen if game is played 
		// at night during DST transitions.
		Date now = new Date();
		long nt = now.getTime();
		long st = turnStartTime.getTime();
		long diff = nt - st;
		int offset = TimeZone.getDefault().getOffset(st);
		return new Date(diff - offset);
	}

	@Override
	public int getTurnNumber() {
		return turnNumber;
	}

	@Override
	public boolean isTurnFinished() {
		return turnFinished;
	}

	@Override
	public void setTurnFinished(boolean isFinished) {
		this.turnFinished = isFinished;
	}

	@Override
	public void setTurnNumber(int newTurnNumber) {
		if (this.turnNumber != newTurnNumber) {
			turnStartTime = new Date();
		}
		
		this.turnNumber = newTurnNumber;
	}

	@Override
	public IUGameTime getCurrentGameTime() {
		return new GameTime(getTimeSinceTurnStart(), getTurnNumber(), isTurnFinished());
	}

	@Override
	public IUScenarioProperties getScenarioProperties() {
		return scenarioProperties;
	}

	@Override
	public void setScenarioProperties(IUScenarioProperties scenarioProperties) {
		this.scenarioProperties = new ScenarioProperties(scenarioProperties);
	}

}

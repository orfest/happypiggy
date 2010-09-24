package ru.nsu.ccfit.pm.econ.controller.player;

import javax.annotation.Nonnegative;

import ru.nsu.ccfit.pm.econ.common.controller.player.IULocalState;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenarioProperties;
import ru.nsu.ccfit.pm.econ.controller.player.roles.PersonDescription;
import ru.nsu.ccfit.pm.econ.controller.player.roles.Player;
import ru.nsu.ccfit.pm.econ.controller.player.roles.Student;
import ru.nsu.ccfit.pm.econ.controller.player.roles.Teacher;

/**
 * Modifiable local to PlayerController state object interface.
 * <p>If local state modification is not required, use 
 * {@link IULocalState} interface instead.</p>
 * 
 * @author dragonfly
 */
public interface IModifiableLocalState {
	
	/**
	 * @see IULocalState#isInsideServer()
	 * @param isServer Whether current environment is server or client.
	 */
	void setInsideServer(boolean isServer);
	
	PersonDescription getModifiableMineDescription();
	
	void setMineDescription(PersonDescription pd);
	
	void setMineId(long newId);
	
	Player getModifiablePlayer();
	
	Teacher getModifiablePlayerAsTeacher();
	
	Student getModifiablePlayerAsStudent();
	
	void setPlayer(Player player);
	
	void setTurnNumber(@Nonnegative int newTurnNumber);
	
	void setTurnFinished(boolean isFinished);
	
	void setScenarioProperties(IUScenarioProperties scenarioProperties);

}

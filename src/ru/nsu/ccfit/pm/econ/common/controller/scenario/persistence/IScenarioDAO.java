package ru.nsu.ccfit.pm.econ.common.controller.scenario.persistence;

import java.io.File;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;

/**
 * Interface to load and store scenarios on disk.
 * <p>Components that may use this interface:
 * <ul><li>ScenarioController</li></ul>
 * </p>
 * @author dragonfly
 */
public interface IScenarioDAO {
	
	/**
	 * Load scenario from file.
	 * @param file Scenario file.
	 * @return Loaded scenario object.
	 * @throws Exception If error occurred while loading or parsing scenario.
	 */
	IUScenario loadScenario(File file) throws Exception;
	
	/**
	 * Store scenario to file.
	 * @param scenario Scenario object to store.
	 * @param file Scenario file to store to.
	 * @throws Exception If error occurred while encoding or saving scenario.
	 */
	void storeScenario(IUScenario scenario, File file) throws Exception;

}

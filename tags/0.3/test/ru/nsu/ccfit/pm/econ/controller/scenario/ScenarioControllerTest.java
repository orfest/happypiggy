/**
 * 
 */
package ru.nsu.ccfit.pm.econ.controller.scenario;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IScenarioController.ScenarioLoadStatus;
import ru.nsu.ccfit.pm.econ.engine.roles.PersonDescriptionEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.PlayerEngine;
import ru.nsu.ccfit.pm.econ.mock.GameEventHandlerHistory;
import ru.nsu.ccfit.pm.econ.mock.engine.ScenarioPortHistory;

/**
 * @author orfest
 * 
 */
public class ScenarioControllerTest {

	private ScenarioPortHistory scenarioPort = new ScenarioPortHistory();
	private ScenarioController controller = new ScenarioController();
	private GameEventHandlerHistory gameEventHandler = new GameEventHandlerHistory();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		controller.setScenarioPort(scenarioPort);
		controller.setGameEventHandler(gameEventHandler);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link ru.nsu.ccfit.pm.econ.controller.scenario.ScenarioController#loadScenario(java.lang.String)}
	 * .
	 */
	@Test
	public void testLoadScenarioString() {
		ScenarioLoadStatus status = controller.loadScenario("Scenario.SimpleExample.ssml");
		assertEquals(ScenarioLoadStatus.OK, status);
		assertNotNull(scenarioPort.getScenario());
	}

	@Test
	public void testLoadScenarioStringFail() {
		ScenarioLoadStatus status = controller.loadScenario("you will never have this file.ssml");
		assertEquals(ScenarioLoadStatus.FILE_NOT_FOUND, status);
		assertNull(scenarioPort.getScenario());
	}

	/**
	 * Test method for
	 * {@link ru.nsu.ccfit.pm.econ.controller.scenario.ScenarioController#loadScenario(java.io.File)}
	 * .
	 */
	@Test
	public void testLoadScenarioFile() {
		File f = new File("Scenario.SimpleExample.ssml");
		ScenarioLoadStatus status = controller.loadScenario(f);
		assertEquals(ScenarioLoadStatus.OK, status);
		assertNotNull(scenarioPort.getScenario());
	}

	/**
	 * Test method for
	 * {@link ru.nsu.ccfit.pm.econ.controller.scenario.ScenarioController#publishOfficialMessage(long)}
	 * .
	 */
	@Test
	public void testPublishOfficialMessage() {
		ScenarioLoadStatus status = controller.loadScenario("Scenario.SimpleExample.ssml");
		assertEquals(ScenarioLoadStatus.OK, status);
		assertNotNull(scenarioPort.getScenario());
		controller.publishOfficialMessage(0L);
		assertEquals(1, gameEventHandler.getEvents().size());
	}

	/**
	 * Test method for
	 * {@link ru.nsu.ccfit.pm.econ.controller.scenario.ScenarioController#publishRumorMessage(long, java.util.Collection)}
	 * .
	 */
	@Test
	public void testPublishRumorMessage() {
		ScenarioLoadStatus status = controller.loadScenario("Scenario.SimpleExample.ssml");
		assertEquals(ScenarioLoadStatus.OK, status);
		assertNotNull(scenarioPort.getScenario());
		controller.publishRumorMessage(0L, Arrays.asList(new PlayerEngine(1L, new PersonDescriptionEngine("a", "b"))));
		assertEquals(1, gameEventHandler.getEvents().size());
	}

}

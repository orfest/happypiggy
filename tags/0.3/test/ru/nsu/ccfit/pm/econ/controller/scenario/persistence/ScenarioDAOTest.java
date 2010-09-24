package ru.nsu.ccfit.pm.econ.controller.scenario.persistence;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;

public class ScenarioDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadScenario() throws Exception {
		File f = new File("Scenario.SimpleExample.ssml");
		IUScenario scenario = new ScenarioDAO().loadScenario(f);
		assertNotNull(scenario);
		assertEquals(2,scenario.getCompanies().size());
		assertFalse(Long.valueOf(Long.MAX_VALUE).equals(scenario.getCompanies().iterator().next().getId()));
		assertFalse(Double.valueOf(Double.MAX_VALUE).equals(scenario.getCompanies().iterator().next().getExpectedProfit()));
		assertFalse(Double.valueOf(Double.MAX_VALUE).equals(scenario.getCompanies().iterator().next().getProfitForPreviousPeriod()));
		assertFalse(Double.valueOf(Double.MAX_VALUE).equals(scenario.getCompanies().iterator().next().getProfitBeforeGameStart()));
		assertEquals(scenario.getCompanies().iterator().next().getProfitBeforeGameStart(), scenario.getCompanies().iterator().next().getProfitForPreviousPeriod(),1e-5);
		assertFalse(Long.valueOf(Long.MAX_VALUE).equals(scenario.getCompanies().iterator().next().getAllMessages().iterator().next().getCompanyId()));
	}

}

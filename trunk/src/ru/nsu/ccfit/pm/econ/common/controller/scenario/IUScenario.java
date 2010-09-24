package ru.nsu.ccfit.pm.econ.common.controller.scenario;

import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;

/**
 * Unmodifiable interface that represents game scenario.
 * @see IUScenarioProperties
 * @author dragonfly
 */
public interface IUScenario extends IUScenarioProperties {
	
	/**
	 * Companies listed in this scenario.
	 * @return Companies list.
	 */
	Collection<? extends IUCompany> getCompanies();

}

package ru.nsu.ccfit.pm.econ.controller.scenario;


import java.util.Collection;
import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;



/**
 * @author orfest
 *
 */
public class Scenario extends ScenarioProperties implements IUScenario {

	private LinkedList<? extends IUCompany> companies = new LinkedList<IUCompany>();
	
	/**
	 * 
	 */
	public Scenario() {
		// TODO Auto-generated constructor stub
	}

	public void setCompanies(LinkedList<? extends IUCompany> companies) {
		this.companies = companies;
	}

	@Override
	public Collection<? extends IUCompany> getCompanies() {
		return companies;
	}

}

package ru.nsu.ccfit.pm.econ.net.controller.scenario;

import java.util.Collection;
import java.util.LinkedList;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.Company;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.ScenarioProto;

@ProtoClass(value=ScenarioProto.class)
public class Scenario extends ScenarioProperties implements IUScenario {

	@SerializeThis
	@BewareCollectionOf(Company.class)
	private Collection<? extends IUCompany> companies = new LinkedList<IUCompany>();

	@Override
	public Collection<? extends IUCompany> getCompanies() {
		return companies;
	}

	public void setCompanies(Collection<? extends IUCompany> companies) {
		this.companies = companies;
	}

}

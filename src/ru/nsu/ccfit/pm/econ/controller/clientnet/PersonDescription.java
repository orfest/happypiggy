package ru.nsu.ccfit.pm.econ.controller.clientnet;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;

/**
 * @author orfest
 *
 */
public class PersonDescription implements IUPersonDescription {
	private String name = null;
	private String group = null;
	
	public PersonDescription(String name_, String group_) {
		name = name_;
		group = group_;
	}
	
	public PersonDescription() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public String getName() {
		return name;
	}

}

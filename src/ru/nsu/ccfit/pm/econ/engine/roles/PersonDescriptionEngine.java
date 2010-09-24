package ru.nsu.ccfit.pm.econ.engine.roles;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;

/**
 * IUPersonDescription implementation for engine
 * 
 * @author pupatenko
 * 
 * @see IUPersonDescription
 */
public class PersonDescriptionEngine implements IUPersonDescription {
	private String group;
	private String name;

	public PersonDescriptionEngine(String group, String name) {
		this.group = group;
		this.name = name;
	}

	public PersonDescriptionEngine(IUPersonDescription toCopy) {
		group = toCopy.getGroup();
		name = toCopy.getName();
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setName(String name) {
		this.name = name;
	}

}

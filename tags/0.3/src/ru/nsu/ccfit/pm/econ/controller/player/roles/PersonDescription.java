package ru.nsu.ccfit.pm.econ.controller.player.roles;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;

public class PersonDescription implements IUPersonDescription {
	
	private String group;
	private String name = "";
	
	public PersonDescription() {
	}
	
	public PersonDescription(String name, String group) {
		this.name = name;
		this.group = group;
	}
	
	public PersonDescription(IUPersonDescription pd) {
		this.name = pd.getName();
		this.group = pd.getGroup();
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IUPersonDescription) {
			IUPersonDescription pd = (IUPersonDescription)obj;
			boolean nameEq = name.equals(pd.getName());
			boolean groupEq = 
				group == null ? 
						(pd.getGroup() == null || pd.getGroup().length() == 0) : 
							group.equals(pd.getGroup());
			return nameEq && groupEq;
		}
		
		return super.equals(obj);
	}

	@Override
	public String toString() {
		String res = name;
		if (group != null && group.length() > 0) 
			res += "  [ " + group + " ]";
		return res;
	}

}

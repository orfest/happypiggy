package ru.nsu.ccfit.pm.econ.net.engine.roles;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;

import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.PersonDescriptionProto;

/**
 * @author orfest
 * 
 */
@ProtoClass(value=PersonDescriptionProto.class)
public class PersonDescription implements IUPersonDescription {
	@SerializeThis
	private String name = "";
	@SerializeThis
	private String group = "";
	
	public PersonDescription(String name_, String group_) {
		name = name_;
		group = group_;
	}
	
	public PersonDescription() {
	}

	public PersonDescription(IUPersonDescription playerData) {
		name = playerData.getName();
		group = playerData.getGroup();
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public int hashCode(){
		return name.hashCode()^group.hashCode();
	}
	
	@Override
	public boolean equals(Object t){
		if (t.getClass().isInstance(this)){
			PersonDescription pd = (PersonDescription)t;
			if (name != null && pd.getName() == null) return false;
			if (name == null && pd.getName() != null) return false;
			if (group != null && pd.getGroup() == null) return false;
			if (group == null && pd.getGroup() != null) return false;
			return name.equals(pd.getName()) && group.equals(pd.getGroup());
		}
		return hashCode() == t.hashCode();
	}
}

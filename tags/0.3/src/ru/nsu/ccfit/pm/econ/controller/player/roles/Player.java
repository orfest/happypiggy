package ru.nsu.ccfit.pm.econ.controller.player.roles;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

public class Player implements IUPlayer {
	
	private long id = -1;
	private PersonDescription pd = new PersonDescription();
	
	public Player() {
	}
	
	public Player(IUPlayer other) {
		this.id = other.getId();
		this.pd = new PersonDescription(other.getUnmodifiablePersonDescription());
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public IUPersonDescription getUnmodifiablePersonDescription() {
		return (IUPersonDescription)pd;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPersonDescription(PersonDescription pd) {
		this.pd = pd;
	}
	
	public PersonDescription getPersonDescription() {
		return pd;
	}

}

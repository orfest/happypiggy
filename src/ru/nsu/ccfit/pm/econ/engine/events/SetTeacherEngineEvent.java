package ru.nsu.ccfit.pm.econ.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.engine.roles.TeacherEngine;

/**
 * Internal engine event, used when teacher is set
 * 
 * @author pupatenko
 * 
 */
public class SetTeacherEngineEvent extends GameEventEngine implements
		IUGameEvent {

	private TeacherEngine teacher;

	public SetTeacherEngineEvent(TeacherEngine teacher) {
		super();
		this.teacher = teacher;
	}

	public TeacherEngine getTeacher() {
		return teacher;
	}

}

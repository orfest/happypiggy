package ru.nsu.ccfit.pm.econ.net.engine.events;

import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;

public interface IUKickBanEvent extends IUGameEvent {

	public boolean getBanned();

	public String getReason();

}

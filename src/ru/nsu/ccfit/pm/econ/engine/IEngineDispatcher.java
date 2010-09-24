package ru.nsu.ccfit.pm.econ.engine;

import ru.nsu.ccfit.pm.econ.common.engine.IScenarioPort;

/**
 * Engine dispatcher interface. Implementations create engine and allow it to
 * "communicate" with input and output ports.
 * 
 * @author pupatenko
 * 
 */
public interface IEngineDispatcher {

	public void handleEvent(EventSourcePair eventSourcePair);

	public PlayerPresence getPlayerPresenceActualHandler();

	public IAnswerSender getAnswerSender();

	public void setAnswerSender(IAnswerSender answerSender);

	public IScenarioPort getScenarioPort();

	public EngineWorkerThread getWorker();

}

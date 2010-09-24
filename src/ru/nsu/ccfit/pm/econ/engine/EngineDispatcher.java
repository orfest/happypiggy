/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine;

import ru.nsu.ccfit.pm.econ.common.engine.IScenarioPort;

import com.google.inject.Inject;

/**
 * @author pupatenko
 * 
 *         answerSender is @injected
 *         scenarioPort is @injected
 * 
 */
public class EngineDispatcher implements IEngineDispatcher {

	private IAnswerSender answerSender;
	private PlayerPresence playerPresence;
	private EngineWorkerThread workerThread;
	private ActualEventHandler actualEventHandler;
	private IScenarioPort scenarioPort;

	public EngineDispatcher() {
		playerPresence = new PlayerPresence(this);
		actualEventHandler = new ActualEventHandler(this);
		workerThread = new EngineWorkerThread(actualEventHandler);
	}

	@Override
	public IAnswerSender getAnswerSender() {
		return answerSender;
	}

	@Override
	public PlayerPresence getPlayerPresenceActualHandler() {
		return playerPresence;
	}

	@Override
	public void handleEvent(EventSourcePair eventSourcePair) {
		workerThread.AddNewTask(eventSourcePair);
	}

	@Override
	@Inject
	public void setAnswerSender(IAnswerSender answerSender) {
		this.answerSender = answerSender;
	}

	@Override
	public IScenarioPort getScenarioPort() {
		return scenarioPort;
	}

	@Override
	public EngineWorkerThread getWorker() {
		return workerThread;
	}

	@Inject
	public void setScenarioPort(IScenarioPort scenarioPort) {
		this.scenarioPort = scenarioPort;
	}

}

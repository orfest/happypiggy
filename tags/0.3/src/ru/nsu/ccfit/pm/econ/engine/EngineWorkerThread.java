package ru.nsu.ccfit.pm.econ.engine;

import java.util.concurrent.LinkedBlockingDeque;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.engine.events.GetScenarioEngine;

import com.google.inject.Singleton;

/**
 * This class describes engine worker thread that performs game event handling
 * operations. Add new task operation never blocks (well, in fact it will not
 * block while number of active tack is less then {@link Integer#MAX_VALUE}).
 * This helps {@link GameEventHandler#handleEvent} not to block.
 * 
 * @see GameEventHandlerEngine
 * @see IGameEventHandler
 * 
 * @author pupatenko
 */

@Singleton
class EngineWorkerThread implements Runnable {

	private LinkedBlockingDeque<EventSourcePair> taskQueue;
	private ActualEventHandler actualEventHandler;

	public EngineWorkerThread(ActualEventHandler actualEventHandler) {
		this.actualEventHandler = actualEventHandler;
		taskQueue = new LinkedBlockingDeque<EventSourcePair>();
		new Thread(this).start();

	}

	/**
	 * Add new task for worker thread to fulfill.
	 * 
	 * @param task
	 *            {@link IUGameEvent} instance to handle
	 */
	public void AddNewTask(EventSourcePair task) {
		try {
			PushTaskList(task);
		} catch (InterruptedException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IUScenario AddGetScenarioTask() throws InterruptedException {
		GetScenarioEngine sc = new GetScenarioEngine();
		PushBeginTaskList(new EventSourcePair(EventSource.Engine, sc));
		return sc.waitForScenario();
	}

	@Override
	public void run() {
		while (true) {
			try {
				actualEventHandler.HandleEvent(PopTaskList());
			} catch (Exception e) {
				// TODO log
				e.printStackTrace();
			}
		}
	}

	private void PushTaskList(EventSourcePair task) throws InterruptedException {
		taskQueue.putLast(task);
	}

	private void PushBeginTaskList(EventSourcePair task)
			throws InterruptedException {
		taskQueue.putFirst(task);
	}

	private EventSourcePair PopTaskList() throws InterruptedException {
		return taskQueue.takeFirst();
	}

}

package ru.nsu.ccfit.pm.econ.controller.scenario;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.IGameEventHandler;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.ICompanyMessagePublisher;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.IScenarioController;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.IScenarioPort;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.controller.scenario.persistence.ScenarioDAO;
import ru.nsu.ccfit.pm.econ.engine.data.GameTimeEngine;
import ru.nsu.ccfit.pm.econ.engine.data.TextOnlyCompanyMessageEngine;
import ru.nsu.ccfit.pm.econ.engine.events.CompanyMessageEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.GameEventEngine;
import ru.nsu.ccfit.pm.econ.modules.names.InScenarioController;

import com.google.gag.annotation.remark.WTF;
import com.google.inject.Inject;

/**
 * @author orfest
 * 
 */
public class ScenarioController implements IScenarioController, ICompanyMessagePublisher, IGameEventHandler {
	
	static final Logger logger = LoggerFactory.getLogger(ScenarioController.class);

	private IScenarioPort scenarioPort;
	private IGameEventHandler gameEventHandler;

	/**
	 * 
	 */
	public ScenarioController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ScenarioLoadStatus loadScenario(String filename) {
		return loadScenario(new File(filename));
	}

	@Override
	public ScenarioLoadStatus loadScenario(File file) {
		if (file == null) {
			return ScenarioLoadStatus.GENERIC_FAILURE;
		} else if (!file.exists()) {
			return ScenarioLoadStatus.FILE_NOT_FOUND;
		} else if (!file.canRead()) {
			return ScenarioLoadStatus.ACCESS_DENIED;
		}
		try {
			IUScenario scenario = new ScenarioDAO().loadScenario(file);
			if (!scenarioPort.setScenario(scenario)) {
				return ScenarioLoadStatus.INVALID_STATE;
			}
			return ScenarioLoadStatus.OK;
		} catch (Exception e) {
			logger.warn("Exception occured while loading scenario: {}", e.getMessage());
			e.printStackTrace();
		}
		return ScenarioLoadStatus.INVALID_FILE_FORMAT;
	}

	@Override
	public IUScenario getScenario() {
		return scenarioPort.getScenario();
	}

	@Override
	public void publishOfficialMessage(long messageId) {
		publishMessage(getMessage(messageId), true, null);
	}

	@Override
	public void publishRumorMessage(long messageId, Collection<? extends IUPlayer> receivers) {
		publishMessage(getMessage(messageId), false, toIdsCollection(receivers));
	}

	private Collection<Long> toIdsCollection(Collection<? extends IUPlayer> receivers) {
		LinkedList<Long> ids = new LinkedList<Long>();
		for (IUPlayer p : receivers) {
			ids.add(p.getId());
		}
		return ids;
	}

	IUCompanyMessage getMessage(long messageId) {
		IUScenario scenario = scenarioPort.getScenario();
		for (IUCompany company : scenario.getCompanies()) {
			for (IUCompanyMessage msg : company.getAllMessages()) {
				if (msg.getId() == messageId) {
					return msg;
				}
			}
		}
		return null;
	}

	@WTF
	// !!REDO
	private void publishMessage(IUCompanyMessage message, boolean broadcast, Collection<Long> receiverIds) {
		if (receiverIds == null) {
			receiverIds = Arrays.asList(0L, 1L, 2L, 3L);
		}
		IUGameEvent baseEvent = new GameEventEngine(message.getPublishTime(), receiverIds, -1L, broadcast);
		IUGameEvent event = new CompanyMessageEngineEvent(new TextOnlyCompanyMessageEngine(message.getCompanyId(),
				message.getId(), message.getMessage(), new GameTimeEngine(message.getPublishTime()),
				message.getTitle(), message.getType()), baseEvent);
		gameEventHandler.handleEvent(event);
	}

	@Inject
	public void setScenarioPort(IScenarioPort scenarioPort) {
		this.scenarioPort = scenarioPort;
	}

	@Inject
	public void setGameEventHandler(@InScenarioController IGameEventHandler gameEventHandler) {
		this.gameEventHandler = gameEventHandler;
	}

	@Override
	public void handleEvent(IUGameEvent event) {
		logger.error("Don't know how to handle event: {}", event);
	}

}

package ru.nsu.ccfit.pm.econ.view.server;

import java.io.File;
import java.net.InetSocketAddress;

import org.apache.pivot.collections.List;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.CardPane;
import org.apache.pivot.wtk.CardPaneListener;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetStateListener;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.TextInputTextListener;
import org.apache.pivot.wtk.FileBrowserSheet.Mode;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IMineStatsAccessor;
import ru.nsu.ccfit.pm.econ.common.controller.player.ITurnControl;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.IScenarioController;
import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.controller.servernet.ServerStartStatus;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.controller.player.data.GameProperties;
import ru.nsu.ccfit.pm.econ.controller.player.roles.PersonDescription;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.Localization;
import ru.nsu.ccfit.pm.econ.view.shared.wtkx.BooleanValue;

import static com.google.common.base.Preconditions.checkNotNull;

public class StartSheet extends Sheet 
		implements Bindable, InjectionVisitable, IPlayerConnectionListener, IServerLifecycleListener {
	
	static final Logger logger = LoggerFactory.getLogger(StartSheet.class);
	
	/*
	 * Start stages card pane.
	 */
	@WTKX private CardPane stagesCP;
	private final static int STAGE_SET_NAME_AND_FILE = 0;
	private final static int STAGE_WAIT_FOR_PLAYERS = 1;
	private final static int STAGE_FINAL = STAGE_WAIT_FOR_PLAYERS;
	
	/*
	 * Set name and scenario file stage controls.
	 */
	@WTKX private TextInput loginNameTI;
	@WTKX private TextInput filePathTI;
	@WTKX private PushButton openFilePB;
	@WTKX private PushButton startServerPB;
	
	/*
	 * Wait for players stage controls.
	 */
	@WTKX private TextInput serverIpTI;
	@WTKX private TextInput serverPortTI;
	@WTKX private ListView clientList;
	@WTKX private PushButton startGamePB;
	
	/*
	 * Data objects.
	 */
	@WTKX private BooleanValue isClosable;
	
	/*
	 * Auxiliary WTKX components.
	 */
	private FileBrowserSheet browserSheet;
	
	/*
	 * Injectables.
	 */
	private ServerNetworkControllerGateway gateway;
	private IMineStatsAccessor playerProps;
	private IScenarioController scenarioController;
	private ITurnControl turnControl;
	private Localization localization;
	private Resources translations;

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");
		
		browserSheet = new FileBrowserSheet(Mode.OPEN);
		
		installListeners();
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
	}
	
	@Inject
	public void setGateway(ServerNetworkControllerGateway gateway) {
		if (this.gateway != null) {
			logger.warn("Redefining gateway");
			this.gateway.getPlayerConnectionListeners().remove(this);
			this.gateway.getServerLifecycleListeners().remove(this);
		}
		
		this.gateway = gateway;
		this.gateway.getPlayerConnectionListeners().add(this);
		this.gateway.getServerLifecycleListeners().add(this);
	}
	
	@Inject
	public void setPlayerProps(IMineStatsAccessor playerProps) {
		this.playerProps = playerProps;
	}
	
	@Inject
	public void setScenarioController(IScenarioController scenarioController) {
		this.scenarioController = scenarioController;
	}
	
	@Inject
	public void setTurnControl(ITurnControl turnControl) {
		this.turnControl = turnControl;
	}
	
	@Inject
	public void setLocalization(Localization localization) {
		this.localization = localization;
		this.translations = this.localization.getResources();
	}
	
	private void installListeners() {
		installGeneralListneres();
		installNameAndScenarioListeners();
		installWaitPlayersListeners();
	}
	
	private void installGeneralListneres() {
		stagesCP.getCardPaneListeners().add(new CardPaneListener.Adapter() {
			@Override
			public void selectedIndexChanged(CardPane cardPane, int previousSelectedIndex) {
				assert cardPane.getSelectedIndex() <= STAGE_FINAL;
				
				switch (cardPane.getSelectedIndex()) {
				case STAGE_SET_NAME_AND_FILE:
					updateStartServerButtonState(checkNameAndScenarioFields());
					break;
				case STAGE_WAIT_FOR_PLAYERS:
					break;
				}
			}
		});
	}
	
	private void installNameAndScenarioListeners() {
		loginNameTI.getTextInputTextListeners().add(new TextInputTextListener() {
			@Override
			public void textChanged(TextInput textInput) {
				updateStartServerButtonState(checkNameAndScenarioFields());
			}
		});
		
		browserSheet.getSheetStateListeners().add(new SheetStateListener.Adapter(){
			@Override
			public void sheetClosed(Sheet sheet) {
				filePathTI.setText(getScenarioFilePathFromBrowser());
				updateStartServerButtonState(checkNameAndScenarioFields());
			}
		});
		
		openFilePB.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				browserSheet.open(getWindow());
			}
		});
		
		filePathTI.getTextInputTextListeners().add(new TextInputTextListener() {
			@Override
			public void textChanged(TextInput textInput) {
				String path = getScenarioFilePathFromBrowser();
				if (!filePathTI.getText().equals(path))
					filePathTI.setText(path);
			}
		});
		
		startServerPB.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				startServerPB.setEnabled(false);
				if (!checkNameAndScenarioFields())
					return;
				
				startServer(loginNameTI.getText(), browserSheet.getSelectedFile());
			}
		});
	}
	
	private void installWaitPlayersListeners() {
		startGamePB.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				turnControl.startTurn();
				closeStartSheet();
			}
		});
	}
	
	private boolean checkNameAndScenarioFields() {
		boolean nameOk = false;
		boolean fileOk = false;
		
		String name = loginNameTI.getText();
		nameOk = name != null && name.length() > 0;
		
		if (nameOk) {
			String filePath = filePathTI.getText();
			File scenarioFile = new File(filePath);
			fileOk = scenarioFile.canRead();
		}
		
		return nameOk && fileOk;
	}
	
	private void updateStartServerButtonState(boolean enabled) {
		startServerPB.setEnabled(enabled);
	}
	
	private String getScenarioFilePathFromBrowser() {
		File file = browserSheet.getSelectedFile();
		String path = (file == null ? "" : file.getAbsolutePath());
		return path;
	}
	
	private void startServer(String name, File scenarioFile) {
		playerProps.setPersonDescription(name, null);
		
		IScenarioController.ScenarioLoadStatus status = scenarioController.loadScenario(scenarioFile);
		if (status != IScenarioController.ScenarioLoadStatus.OK) {
			updateStartServerButtonState(checkNameAndScenarioFields());
			showScenarioLoadError(status);
			return;
		}
		
		playerProps.notifyEngineOfPlayerPresence();
		
		IUScenario scenario = checkNotNull(scenarioController.getScenario());
		GameProperties gameProps = new GameProperties(scenario);
		gateway.setGameProperties(gameProps);
		gateway.startServer();
	}
	
	private void showScenarioLoadError(IScenarioController.ScenarioLoadStatus error) {
		String message = translations.getString("s.start.scenario.status." + error.toString());
		Prompt.prompt(MessageType.ERROR, message, getWindow());
	}
	
	private void showServerStartStatus(ServerStartStatus status) {
		MessageType type = MessageType.INFO;
		
		if (status == ServerStartStatus.OK) {
			return;
		}
		
		if (!isServerStartStatusOK(status)) {
			type = MessageType.ERROR;
		}
		
		String message = translations.getString("s.start.server.status." + status.toString());
		Prompt.prompt(type, message, getWindow());
	}
	
	private boolean isServerStartStatusOK(ServerStartStatus status) {
		return status == ServerStartStatus.OK || status == ServerStartStatus.OK_NO_AUTOLOCATE;
	}
	
	@SuppressWarnings("unchecked")
	private List<PersonDescription> getPersonDescriptionList() {
		return (List<PersonDescription>)clientList.getListData();
	}

	@Override
	public void onPlayerConnect(IUPersonDescription playerData) {
		getPersonDescriptionList().add(new PersonDescription(playerData));
	}

	@Override
	public void onPlayerDisconnect(IUPersonDescription playerData) {
		getPersonDescriptionList().remove(new PersonDescription(playerData));
	}

	@Override
	public void onServerStart(ServerStartStatus status, InetSocketAddress bindAddress) {
		assert isServerStartStatusOK(status);
		showServerStartStatus(status);
				
		serverIpTI.setText(bindAddress.getAddress().getHostAddress());
		serverPortTI.setText(String.valueOf(bindAddress.getPort()));
		clientList.getListData().clear();
		
		stagesCP.setSelectedIndex(STAGE_WAIT_FOR_PLAYERS);
	}

	@Override
	public void onServerStartFailed(ServerStartStatus status) {
		assert !isServerStartStatusOK(status);
		showServerStartStatus(status);
		
		stagesCP.setSelectedIndex(STAGE_SET_NAME_AND_FILE);
	}

	@Override
	public void onServerTerminate() {
		logger.debug("onServerTerminate()");
	}
	
	private void closeStartSheet() {
		isClosable.setVal(true);
		this.close();
		this.gateway.getPlayerConnectionListeners().remove(this);
	}

}

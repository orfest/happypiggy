package ru.nsu.ccfit.pm.econ.view.client;

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonGroup;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.CardPane;
import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.RadioButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.net.IUServerProperties;
import ru.nsu.ccfit.pm.econ.common.view.ITurnChangeListener;
import ru.nsu.ccfit.pm.econ.view.shared.GameTimeGateway;
import ru.nsu.ccfit.pm.econ.view.shared.MineStatsGateway;
import ru.nsu.ccfit.pm.econ.view.shared.data.ServerProperties;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.Localization;
import ru.nsu.ccfit.pm.econ.view.shared.wtkx.BooleanValue;

import com.google.common.base.Preconditions;
import com.google.gag.annotation.disclaimer.BossMadeMeDoIt;
import com.google.inject.Inject;

public class LoginSheet extends Sheet 
		implements Bindable, InjectionVisitable, INetworkEventsListener, ITurnChangeListener {
	
	static final Logger logger = LoggerFactory.getLogger(LoginSheet.class);
	
	/*
	 * Login states card pane.
	 */
	@WTKX private CardPane loginStateCP;
	private static final int STATE_AT_INPUT_FORM = 0;
	private static final int STATE_WAITING_CONNECTION = 1;
	private static final int STATE_WAITING_PLAYERS = 2;
	private static final int STATE_FINAL = STATE_WAITING_PLAYERS;
	
	/*
	 * Radio buttons.
	 */
	@SuppressWarnings("unused")
	@WTKX private ButtonGroup serverChooseBG;
	@WTKX private RadioButton serverListRB;
	@WTKX private RadioButton serverEnterRB;
	
	/*
	 * Name-group form.
	 */
	@WTKX private TextInput loginNameTI;
	@WTKX private TextInput loginGroupTI;
	
	/*
	 * Server list.
	 */
	@SuppressWarnings("unused")
	@WTKX private CardPane serverListCP;
	@WTKX private ListView serverList;
	
	/*
	 * Server address form.
	 */
	@SuppressWarnings("unused")
	@WTKX private Form serverEnterForm;
	@WTKX private BoxPane serverEnterBP;
	@WTKX private TextInput serverIpTI;
	@WTKX private TextInput serverPortTI;
	
	/*
	 * Buttons.
	 */
	@WTKX private PushButton connectPB;
	@WTKX private PushButton disconnectPB;
	
	/*
	 * Data objects.
	 */
	@WTKX private BooleanValue isClosable;
	
	/*
	 * Injectables - interface with other components.
	 */
	private ClientNetworkControllerGateway cncGateway;
	private Localization localization;
	private Resources translations;
	private MineStatsGateway playerGateway;
	private GameTimeGateway gameTimeGateway;
	
	/*
	 * Other.
	 */
	private boolean requestedDisconnect = false;
	

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");

		installListeners();
	}
	
	private void installListeners() {
		connectPB.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				if (!isNameGroupFormValid() || !isServerFormValid())
					return;
				
				String name = loginNameTI.getText();
				String group = loginGroupTI.getText();
				
				InetSocketAddress address = null;
				if (serverListRB.isSelected()) {
					address = ((ServerProperties)serverList.getSelectedItem()).getAddress();
				} else if (serverEnterRB.isSelected()) {
					String ip = serverIpTI.getText();
					String port = serverPortTI.getText();
					
					address = new InetSocketAddress(ip, Integer.valueOf(port));
				}
				
				playerGateway.setPersonDescription(name, group);
				cncGateway.connect(address, name, group);
				changeLoginState(STATE_WAITING_CONNECTION);
			}
		});
		
		disconnectPB.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				requestedDisconnect = true;
				cncGateway.disconnect();
			}
		});
	}
	
	private boolean isNameGroupFormValid() {
		String name = loginNameTI.getText();
		
		Form.Flag flag = null;
		if (name.length() == 0) {
			flag = new Form.Flag(MessageType.ERROR, translations.getString("c.login.requiresName"));
		}
		
		Form.setFlag(loginNameTI, flag);
		return flag == null;
	}
	
	@BossMadeMeDoIt("Time constraints suck")
	private boolean isServerFormValid() {
		boolean ok = true;
		
		if (serverListRB.isSelected()) {
			
			if (serverList.getSelectedIndex() < 0) {
				ok = false;
			}
			
		} else if (serverEnterRB.isSelected()) {
			
			String ip = serverIpTI.getText();
			String port = serverPortTI.getText();
			
			Form.Flag flag = null;
			if (ip.length() == 0 || port.length() == 0) {
				flag = new Form.Flag(MessageType.ERROR, 
						translations.getString("c.login.requiresServer"));
			} else {
				try {
					// This freezes interface a bit, but that's OK.
					if (new InetSocketAddress(ip, Integer.valueOf(port)).isUnresolved())
						throw new RuntimeException("Hostname cannot be resolved");
				} catch (RuntimeException e) {
					flag = new Form.Flag(MessageType.ERROR, 
							translations.getString("c.login.requiresValidServer"));
				}
			}
			
			Form.setFlag(serverEnterBP, flag);
			ok &= flag == null;
			
		} else {
			
			ok = false;
			
		}
		
		return ok;
	}
	
	private void changeLoginState(int state) {
		Preconditions.checkPositionIndex(state, STATE_FINAL);
		loginStateCP.setSelectedIndex(state);
	}

	@Inject
	public void setClientNetworkControllerGateway(ClientNetworkControllerGateway cncGateway) {
		if (this.cncGateway != null) {
			logger.warn("Redefining cncGateway");
			this.cncGateway.getNetworkEventsListeners().remove(this);
		}
		
		this.cncGateway = cncGateway;
		this.cncGateway.getNetworkEventsListeners().add(this);
		this.cncGateway.init(true);
	}
	
	@Inject
	public void setLocalization(Localization localization) {
		this.localization = localization;
		this.translations = this.localization.getResources();
	}
	
	@Inject
	public void setPlayerGateway(MineStatsGateway playerGateway) {
		if (this.playerGateway != null) {
			logger.warn("Redefining playerGateway");
		}
		
		this.playerGateway = playerGateway;
	}
	
	@Inject
	public void setGameTimeGateway(GameTimeGateway gameTimeGateway) {
		if (this.gameTimeGateway != null) {
			logger.warn("Redefining gameTimeController");
			this.gameTimeGateway.getTurnChangeListeners().remove(this);
		}
		
		this.gameTimeGateway = gameTimeGateway;
		this.gameTimeGateway.getTurnChangeListeners().add(this);
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
	}

	@Override
	public void onBan(String reason) {
		logger.info("onBan('{}') - not handled", reason);
	}

	@Override
	public void onConnect() {
		requestedDisconnect = false;
		changeLoginState(STATE_WAITING_PLAYERS);
	}

	@Override
	public void onDisconnect(Exception cause) {
		logger.info("Disconnected from server, cause is '{}'", cause);
		changeLoginState(STATE_AT_INPUT_FORM);
		
		// TODO prompt should be opened on main window instead. also open login sheet on disconnect? 
		if (!requestedDisconnect)
			Prompt.prompt(MessageType.WARNING, translations.getString("c.login.disconnected"), this);
		requestedDisconnect = false;
	}

	@Override
	public void onKick(String reason) {
		logger.info("onKick('{}') - not handled", reason);
	}

	@Override
	public void onReconnectAttempt(int numberOfAttempts, Exception cause) {
		logger.info("onReconnectAttempt({}, '{}') - not handled", numberOfAttempts, cause);
	}

	@Override
	public void onServerLookupStart() {
		logger.info("Server lookup started");
	}

	@Override
	public void onServerLookupStop() {
		logger.info("Server lookup stopped");
	}

	@Override
	public void onServerLookupUpdate(List<? extends IUServerProperties> updatedList) {
		org.apache.pivot.collections.List<ServerProperties> list = new ArrayList<ServerProperties>();
		for (IUServerProperties server : updatedList) {
			list.add(new ServerProperties(server));
		}
		
		serverList.setListData(list);
	}

	@Override
	public void onGameStart() {
		closeLoginSheet();
	}

	@Override
	public void onTurnNumberChange(int newTurnNumber) {
	}

	@Override
	public void onTurnStateChange(boolean isFinished) {
	}
	
	private void closeLoginSheet() {
		this.isClosable.setVal(true);
		this.close();
		this.gameTimeGateway.getTurnChangeListeners().remove(this);
	}

}

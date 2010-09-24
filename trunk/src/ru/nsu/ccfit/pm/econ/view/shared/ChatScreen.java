package ru.nsu.ccfit.pm.econ.view.shared;

import java.util.HashMap;
import java.util.List;

import org.apache.pivot.wtk.ApplicationContext;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.TextArea;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.controller.player.IChat.IChatMessage;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.view.IChatListener;
import ru.nsu.ccfit.pm.econ.common.view.ITurnChangeListener;
import ru.nsu.ccfit.pm.econ.engine.data.GameTimeEngine;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.IFormatter;

import com.google.inject.Inject;

public class ChatScreen extends GameTab implements Bindable, InjectionVisitable, IChatListener, ITurnChangeListener {

	static final Logger logger = LoggerFactory.getLogger(ChatScreen.class);

	/*
	 * WTKX controls.
	 */
	@WTKX private TextArea chatTA;
	@WTKX private ScrollPane chatSP;
	@WTKX private TextArea inputTA;
	@WTKX private ListView buddyList;
	@WTKX private PushButton sendPB;
	@WTKX private PushButton sendPrivatePB;

	/*
	 * Injectables.
	 */
	private IFormatter formatter;
	private ChatGateway chatGw;
	private PlayerRosterGateway playerRosterGw;
	private GameTimeGateway gameTimeGw;

	private HashMap<Integer, Long> playerByIndex = new HashMap<Integer, Long>();

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");

		installListeners();
	}

	@Inject
	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}

	@Inject
	public void setChatGateway(ChatGateway chatGateway) {
		if (this.chatGw != null) {
			logger.warn("Redefining chatGateway");
			this.chatGw.getChatListeners().remove(this);
		}

		this.chatGw = chatGateway;
		this.chatGw.getChatListeners().add(this);
	}

	@Inject
	public void setPlayerRosterGateway(PlayerRosterGateway gateway) {
		if (this.playerRosterGw != null) {
			logger.warn("Redefining playerRosterGateway");
		}

		this.playerRosterGw = gateway;
	}

	@Inject
	public void setGameTimeGateway(GameTimeGateway gameTimeGateway) {
		if (this.gameTimeGw != null) {
			logger.warn("Redefining gameTimeGateway");
			this.gameTimeGw.getTurnChangeListeners().remove(this);
		}

		this.gameTimeGw = gameTimeGateway;
		this.gameTimeGw.getTurnChangeListeners().add(this);
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
	}

	private void initScreenControls() {
		chatTA.setText("");
		inputTA.setText("");

		redrawBuddies();
	}

	private void redrawBuddies() {
		org.apache.pivot.collections.List<String> data = new org.apache.pivot.collections.LinkedList<String>();
		buddyList.setListData(data);
		playerByIndex.clear();
		int cnt = 0;
		for (IUPlayer player : playerRosterGw.getPlayerList()) {
			String name = player.getUnmodifiablePersonDescription().getName();
			data.add(name);
			playerByIndex.put(cnt, player.getId());
			cnt++;
		}
		buddyList.setListData(data);
	}

	private void installListeners() {
		sendPB.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				sendChatMessage(true);
			}
		});

		sendPrivatePB.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				sendChatMessage(false);
			}
		});
	}

	private void sendChatMessage(boolean broadcast) {
		int index = buddyList.getSelectedIndex();
		Long playerId = playerByIndex.get(index);
		if (playerId == null && !broadcast)
			return;
		String messageText = inputTA.getText().trim();
		inputTA.setText("");
		if (messageText.isEmpty())
			return;

		if (!broadcast) {
			chatGw.sendPrivateMessage(playerId, messageText);
			appendMessage(formatter.formatChatMessage(false, new GameTimeEngine(gameTimeGw.getTime(), gameTimeGw
					.getTurnNumber(), false), playerRosterGw.getMe().getUnmodifiablePersonDescription().getName(),
					messageText));
		} else {
			chatGw.sendPublicMessage(messageText);
			// appendMessage(formatter.formatChatMessage(true, new
			// GameTimeEngine(new Date(80099), 4, true),"Myself", messageText));
		}
	}

	@Override
	public void onChatLogUpdate(List<? extends IChatMessage> messageLog) {
		chatTA.setText("");
		if (messageLog == null)
			return;
		for (IChatMessage msg : messageLog) {
			if (msg.isPrivate()) {
				onPrivateMessageReceived(msg.getSender(), msg.getMessage(), msg.getTime());
			} else {
				onPublicMessageReceived(msg.getSender(), msg.getMessage(), msg.getTime());
			}
		}
	}

	private void appendMessage(String manyLines) {
		String[] lines = manyLines.split("\n");
		for (String l : lines) {
			int insPoint = chatTA.getDocument().getCharacterCount() - 1;
			chatTA.setSelection(insPoint, 0);
			chatTA.insertText(l);
			chatTA.insertParagraph();
		}
		ApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				chatSP.scrollAreaToVisible(chatTA.getCharacterBounds(chatTA.getSelectionStart()));
			}
		});
	}

	@Override
	public void onPrivateMessageReceived(IUPlayer sender, String message, IUGameTime receiveTime) {
		String who = sender.getUnmodifiablePersonDescription().getName();
		String text = formatter.formatChatMessage(false, receiveTime, who, message);
		appendMessage(text);
	}

	@Override
	public void onPublicMessageReceived(IUPlayer sender, String message, IUGameTime receiveTime) {
		String who = sender.getUnmodifiablePersonDescription().getName();
		String text = formatter.formatChatMessage(true, receiveTime, who, message);
		appendMessage(text);
	}

	@Override
	public void onGameStart() {
		logger.info("onGameStart");
		initScreenControls();
	}

	@Override
	public void onTurnNumberChange(int newTurnNumber) {
	}

	@Override
	public void onTurnStateChange(boolean isFinished) {
	}

}

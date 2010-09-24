package ru.nsu.ccfit.pm.econ.view.server;

import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.controller.player.ITurnControl;
import ru.nsu.ccfit.pm.econ.common.view.ITurnChangeListener;
import ru.nsu.ccfit.pm.econ.view.shared.GameTimeGateway;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.Localization;

import com.google.inject.Inject;

public class TurnControl extends BoxPane implements Bindable,
		InjectionVisitable, ITurnChangeListener {

	private final static String RES_BUTTON_END_TURN = "s.game.endTurn";
	private final static String RES_BUTTON_BEGIN_TURN = "s.game.beginTurn";

	static final Logger logger = LoggerFactory.getLogger(TurnControl.class);

	/*
	 * WTKX components.
	 */
	@WTKX private PushButton turnPB;

	/*
	 * Injectables.
	 */
	private GameTimeGateway gameTimeGateway;
	private ITurnControl turnController;
	private Localization localization;
	private Resources translations;

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");

		installListeners();
	}

	@Inject
	public void setLocalization(Localization localization) {
		this.localization = localization;
		this.translations = this.localization.getResources();
	}

	@Inject
	public void setGameTimeGateway(GameTimeGateway gameTimeGateway) {
		if (this.gameTimeGateway != null) {
			logger.warn("Redefining gameTimeGateway");
			this.gameTimeGateway.getTurnChangeListeners().remove(this);
		}

		this.gameTimeGateway = gameTimeGateway;
		this.gameTimeGateway.getTurnChangeListeners().add(this);
	}

	@Inject
	public void setTurnController(ITurnControl turnController) {
		this.turnController = turnController;
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
	}

	@Override
	public void onGameStart() {
		// enable turnPB, etc.
		// probably no action required
	}

	@Override
	public void onTurnNumberChange(int newTurnNumber) {
		// no action required
	}

	@Override
	public void onTurnStateChange(boolean isFinished) {
		// change turnPB label, etc.
		String message = translations
				.getString(isFinished ? RES_BUTTON_BEGIN_TURN
						: RES_BUTTON_END_TURN);
		turnPB.setButtonData(message);
		turnPB.setEnabled(true);
	}

	private void installListeners() {
		// install turnPB listener (use turnController to make some action)
		turnPB.getButtonPressListeners().add(new ButtonPressListener() {

			@Override
			public void buttonPressed(Button arg0) {
				if (turnController.isTurnFinished()) {
					turnController.startTurn();
					turnPB.setEnabled(false);
				} else {
					turnController.finishTurn();
					turnPB.setEnabled(false);
				}

			}
		});
	}

}

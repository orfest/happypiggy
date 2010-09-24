package ru.nsu.ccfit.pm.econ.view.shared;

import org.apache.pivot.collections.HashMap;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerRatingListener;
import ru.nsu.ccfit.pm.econ.common.view.ITurnChangeListener;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;

import com.google.inject.Inject;

public class RatingScreen extends GameTab implements Bindable, InjectionVisitable, 
	IPlayerRatingListener, ITurnChangeListener {
	
	static final Logger logger = LoggerFactory.getLogger(RatingScreen.class);
	
	/*
	 * WTKX components.
	 */
	@WTKX private TableView ratingTable;
	
	/*
	 * Injectables.
	 */
	private PlayerRosterGateway playerRosterGw;
	private GameTimeGateway gameTimeGw;
	
	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");
	}
	
	@Inject
	public void setPlayerRosterGateway(PlayerRosterGateway gateway) {
		if (this.playerRosterGw != null) {
			logger.warn("Redefining playerRosterGateway");
			this.playerRosterGw.getPlayerRatingListeners().remove(this);
		}
		
		this.playerRosterGw = gateway;
		this.playerRosterGw.getPlayerRatingListeners().add(this);
	}

	@Inject
	public void setGameTimeGateway(GameTimeGateway gateway) {
		if (this.gameTimeGw != null) {
			logger.warn("Redefining companyRosterGateway");
			this.gameTimeGw.getTurnChangeListeners().remove(this);
		}

		this.gameTimeGw = gateway;
		this.gameTimeGw.getTurnChangeListeners().add(this);
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
	}

	private void initScreenControls() {
		onPlayerRatingListUpdate(playerRosterGw.getPlayerRatingList());
	}
	
	@Override
	public void onPlayerRatingListUpdate(java.util.List<? extends IUPlayer> ratingList) {
		org.apache.pivot.collections.List<HashMap<String, String>> data = 
			new org.apache.pivot.collections.LinkedList<HashMap<String,String>>();
		ratingTable.setTableData(data);
		int cnt = 1;
		for (IUPlayer player : ratingList){
			HashMap<String, String> element = new HashMap<String, String>();
			element.put("name", player.getUnmodifiablePersonDescription().getName());
			element.put("place", Integer.toString(cnt));
			data.add(element);
			cnt++;
		}
		ratingTable.setTableData(data);
	}

	@Override
	public void onGameStart() {
		logger.info("onGameStart");
		initScreenControls();
	}

	@Override
	public void onTurnNumberChange(int newTurnNumber) {
		initScreenControls();
	}

	@Override
	public void onTurnStateChange(boolean isFinished) {
	}
	
}

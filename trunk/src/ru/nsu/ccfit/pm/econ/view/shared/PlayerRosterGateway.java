package ru.nsu.ccfit.pm.econ.view.shared;

import java.util.List;

import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ru.nsu.ccfit.pm.econ.common.controller.player.IPlayerRoster;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.view.IPlayerRatingListener;

/**
 * Gateway for views to interact with <tt>PlayerController</tt> player roster.
 * @see IPlayerRoster
 * @see IPlayerRatingListener
 * 
 * @author dragonfly
 */
public class PlayerRosterGateway implements IPlayerRoster, IPlayerRatingListener {
	
	static final Logger logger = LoggerFactory.getLogger(PlayerRosterGateway.class);
	
	protected PlayerRatingListenerList listeners = new PlayerRatingListenerList();
	private IPlayerRoster controller;

	@Override
	public IUPlayer getMe() {
		return controller.getMe();
	}

	@Override
	public IUPlayer getPlayerById(long playerId) {
		return controller.getPlayerById(playerId);
	}

	@Override
	public IUPlayer getPlayerByName(String playerName) {
		return controller.getPlayerByName(playerName);
	}

	@Override
	public List<? extends IUPlayer> getPlayerList() {
		return controller.getPlayerList();
	}

	@Override
	public List<? extends IUPlayer> getPlayerRatingList() {
		return controller.getPlayerRatingList();
	}

	@Override
	public void onPlayerRatingListUpdate(final List<? extends IUPlayer> ratingList) {
		DesktopApplicationContext.queueCallback(new Runnable() {
			@Override
			public void run() {
				listeners.onPlayerRatingListUpdate(ratingList);
			}
		});
	}
	
	public ListenerList<IPlayerRatingListener> getPlayerRatingListeners() {
		return listeners;
	}

	@Inject
	public void setPlayerRoster(IPlayerRoster controller) {
		if (this.controller != null)
			logger.warn("Redefining controller");
		this.controller = controller;
	}
	
	protected class PlayerRatingListenerList
		extends ListenerList<IPlayerRatingListener> implements IPlayerRatingListener {

		@Override
		public void onPlayerRatingListUpdate(List<? extends IUPlayer> ratingList) {
			for (IPlayerRatingListener listener : this)
				listener.onPlayerRatingListUpdate(ratingList);
		}
		
	}

}

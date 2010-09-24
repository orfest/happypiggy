package ru.nsu.ccfit.pm.econ.common.engine.events;

import java.util.Collection;
import java.util.List;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

/**
 * Unmodifiable interface for distributing game snapshots.
 * <p>Game snapshots are used to ensure all players have up to date 
 * game state. Also game snapshots are used by reconnecting players
 * to "catch up" with game. Snapshots are broadcast only during turn
 * start.</p>
 * @author dragonfly
 */
public interface IUGameSnapshotEvent extends IUGameEvent {
	
	/**
	 * Game scenario. This includes not only initial scenario info,
	 * but also some game state information such as share holdings 
	 * lists, company profit values, published messages, interest 
	 * rates. 
	 * @return Game scenario.
	 */
	IUScenario getScenario();
	
	/**
	 * Set of all players in the game, including their player-specific
	 * state. 
	 * <p>When passing this information to client implementations may 
	 * truncate some data that should be available only to Teacher.</p> 
	 * @return
	 */
	Collection<? extends IUPlayer> getPlayers();
	
	/**
	 * Stock index. This opaque value is calculated on server and 
	 * reflects stock "health" or state. It is used only by end players.
	 * @return Stock index value.
	 */
	double getStockIndex();
	
	/**
	 * Chat tail. This list contains a certain number of latest chat 
	 * messages. 
	 * <p>The list includes only messages that should be 
	 * available to the game snapshot event recipient. Therefore the 
	 * list may contain both public and private messages. List is 
	 * ordered chronologically.</p>
	 * @return List of latest chat messages.
	 */
	List<? extends IUChatMessageEvent> getChatMessages();
	
	/**
	 * All currently available buy offers.
	 * @return Collection of all buy offers.
	 */
	Collection<? extends IUBuyOffer> getAllBuyOffers();

}

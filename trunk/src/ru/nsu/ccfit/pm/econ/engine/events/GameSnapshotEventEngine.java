/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine.events;

import java.util.ArrayList;
import java.util.Collection;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameTime;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUChatMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameSnapshotEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.engine.data.BuyOfferEngine;
import ru.nsu.ccfit.pm.econ.engine.data.ScenarioEngine;

/**
 * @author pupatenko
 * 
 */
public class GameSnapshotEventEngine extends GameEventEngine implements
		IUGameSnapshotEvent {

	private ArrayList<BuyOfferEngine> allBuyOffers;
	private ArrayList<ChatMessageEventEngine> chatMessages;
	private ArrayList<IUPlayer> players;
	private ScenarioEngine scenario;
	private double stockIndex;

	public GameSnapshotEventEngine(IUGameTime eventTime,
			Collection<Long> recieverIds, long senderId, boolean broadcast,
			Collection<? extends IUBuyOffer> allBuyOffers,
			Collection<? extends IUChatMessageEvent> chatMessages,
			Collection<? extends IUPlayer> players, IUScenario scenario,
			double stockIndex) {
		super(eventTime, recieverIds, senderId, broadcast);
		this.allBuyOffers = new ArrayList<BuyOfferEngine>(allBuyOffers.size());
		for (IUBuyOffer bo : allBuyOffers)
			this.allBuyOffers.add(new BuyOfferEngine(bo));
		this.chatMessages = new ArrayList<ChatMessageEventEngine>(chatMessages
				.size());
		for (IUChatMessageEvent c : chatMessages)
			this.chatMessages.add(new ChatMessageEventEngine(c));
		this.players = new ArrayList<IUPlayer>(players.size());

		// TODO kostyl'
		for (IUPlayer p : players)
			this.players.add(p);
		this.scenario = new ScenarioEngine(scenario);
		this.stockIndex = stockIndex;
	}

	public GameSnapshotEventEngine(IUGameSnapshotEvent other) {
		super(other.getEventTime(), other.getReceiverIds(), other.getSenderId(),
				other.isBroadcast());
		allBuyOffers = new ArrayList<BuyOfferEngine>(other.getAllBuyOffers()
				.size());
		for (IUBuyOffer bo : other.getAllBuyOffers())
			allBuyOffers.add(new BuyOfferEngine(bo));
		chatMessages = new ArrayList<ChatMessageEventEngine>(other
				.getChatMessages().size());
		for (IUChatMessageEvent c : other.getChatMessages())
			chatMessages.add(new ChatMessageEventEngine(c));
		players = new ArrayList<IUPlayer>(other.getPlayers().size());
		for (IUPlayer p : other.getPlayers())
			players.add(p);
		scenario = new ScenarioEngine(other.getScenario());
		stockIndex = other.getStockIndex();
	}

	public void setAllBuyOffers(ArrayList<BuyOfferEngine> allBuyOffers) {
		this.allBuyOffers = allBuyOffers;
	}

	public void setChatMessages(ArrayList<ChatMessageEventEngine> chatMessages) {
		this.chatMessages = chatMessages;
	}

	public void setPlayers(ArrayList<IUPlayer> players) {
		this.players = players;
	}

	public void setScenario(ScenarioEngine scenario) {
		this.scenario = scenario;
	}

	public void setStockIndex(double stockIndex) {
		this.stockIndex = stockIndex;
	}

	@Override
	public ArrayList<BuyOfferEngine> getAllBuyOffers() {
		return allBuyOffers;
	}

	@Override
	public ArrayList<ChatMessageEventEngine> getChatMessages() {
		return chatMessages;
	}

	@Override
	public ArrayList<IUPlayer> getPlayers() {
		return players;
	}

	@Override
	public ScenarioEngine getScenario() {
		return scenario;
	}

	@Override
	public double getStockIndex() {
		return stockIndex;
	}

}

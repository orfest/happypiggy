package ru.nsu.ccfit.pm.econ.net.engine.events;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUChatMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameSnapshotEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.controller.scenario.Scenario;
import ru.nsu.ccfit.pm.econ.net.engine.data.BuyOffer;
import ru.nsu.ccfit.pm.econ.net.engine.roles.Player;

public class GameSnapshotEvent extends GameEvent implements IUGameSnapshotEvent {

	@SerializeThis
	private IUScenario scenario = new Scenario();
	@SerializeThis
	@BewareCollectionOf(value=Player.class)
	private Collection<? extends IUPlayer> players = new LinkedList<IUPlayer>();
	@SerializeThis
	private double stockIndex = Double.MAX_VALUE;
	@SerializeThis
	@BewareCollectionOf(value=ChatMessageEvent.class)
	private List<? extends IUChatMessageEvent> chatMessages = new LinkedList<IUChatMessageEvent>();
	@SerializeThis
	@BewareCollectionOf(value=BuyOffer.class)
	private Collection<? extends IUBuyOffer> allBuyOffers = new LinkedList<IUBuyOffer>();

	public GameSnapshotEvent(){
	}

	public IUScenario getScenario() {
		return scenario;
	}

	public Collection<? extends IUPlayer> getPlayers() {
		return players;
	}

	public double getStockIndex() {
		return stockIndex;
	}

	public List<? extends IUChatMessageEvent> getChatMessages() {
		return chatMessages;
	}

	public Collection<? extends IUBuyOffer> getAllBuyOffers() {
		return allBuyOffers;
	}

	public void setScenario(IUScenario scenario) {
		this.scenario = scenario;
	}

	public void setPlayers(Collection<? extends IUPlayer> players) {
		this.players = players;
	}

	public void setStockIndex(double stockIndex) {
		this.stockIndex = stockIndex;
	}

	public void setChatMessages(List<? extends IUChatMessageEvent> chatMessages) {
		this.chatMessages = chatMessages;
	}

	public void setAllBuyOffers(Collection<? extends IUBuyOffer> allBuyOffers) {
		this.allBuyOffers = allBuyOffers;
	}

}

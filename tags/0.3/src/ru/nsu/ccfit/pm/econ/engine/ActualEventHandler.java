package ru.nsu.ccfit.pm.econ.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.pm.econ.common.engine.data.BankTransactionType;
import ru.nsu.ccfit.pm.econ.common.engine.data.CompanyMessageType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUAddCashEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankPercentEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankRequestEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankTransactionEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBuyOffersChangeEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBuyRequestEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUChatMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUCompanyMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUDividendPayoutEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUDividendVoteEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUDividendVotingEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUDividendVotingProposalEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameSnapshotEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUShareAllocationRequestEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTransactionEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTransferSharesEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnStartEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent.IUPlayerRatingValue;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.engine.data.BankDepositRequestEngine;
import ru.nsu.ccfit.pm.econ.engine.data.BankLoanRequestEngine;
import ru.nsu.ccfit.pm.econ.engine.data.BuyOfferEngine;
import ru.nsu.ccfit.pm.econ.engine.data.CompanyEngine;
import ru.nsu.ccfit.pm.econ.engine.data.CompanyMessageEngine;
import ru.nsu.ccfit.pm.econ.engine.data.GameTimeEngine;
import ru.nsu.ccfit.pm.econ.engine.data.PlayerRatingValue;
import ru.nsu.ccfit.pm.econ.engine.data.ScenarioEngine;
import ru.nsu.ccfit.pm.econ.engine.data.ShareHoldingEngine;
import ru.nsu.ccfit.pm.econ.engine.data.TextOnlyCompanyMessageEngine;
import ru.nsu.ccfit.pm.econ.engine.data.TransactionEngine;
import ru.nsu.ccfit.pm.econ.engine.events.BankPercentEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.BankPresentEvent;
import ru.nsu.ccfit.pm.econ.engine.events.BankRemoveEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.BuyOffersChangeEventEngine;
import ru.nsu.ccfit.pm.econ.engine.events.ChatMessageEventEngine;
import ru.nsu.ccfit.pm.econ.engine.events.CompanyMessageEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.GameSnapshotEventEngine;
import ru.nsu.ccfit.pm.econ.engine.events.GetScenarioEngine;
import ru.nsu.ccfit.pm.econ.engine.events.NewBankEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.NewPlayerEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.NewScenarioEventEngine;
import ru.nsu.ccfit.pm.econ.engine.events.PlayerPresentEvent;
import ru.nsu.ccfit.pm.econ.engine.events.PlayerRemoveEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.SetTeacherEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.TransactionEventEngine;
import ru.nsu.ccfit.pm.econ.engine.events.TurnEndEngineLocalEvent;
import ru.nsu.ccfit.pm.econ.engine.events.TurnEndEventEngine;
import ru.nsu.ccfit.pm.econ.engine.events.TurnStartEventEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.BankEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.StudentEngine;

import com.google.gag.annotation.remark.Hack;

/**
 * Contain actual game event handling methods. Should be used only by engine
 * worker thread (defined in {@link EngineWorkerThread} class)
 * 
 * @author pupatenko
 */
class ActualEventHandler {
	private IEngineDispatcher engineDispatcher;
	private EngineState engineState;

	public ActualEventHandler(IEngineDispatcher engineDispatcher) {
		this.engineDispatcher = engineDispatcher;
		this.engineState = new EngineState();
		// other initialization here
	}

	public void HandleEvent(EventSourcePair event) {
		if (event.getEvent() instanceof IUAddCashEvent)
			handleAddCashEvent((IUAddCashEvent) event.getEvent());
		else if (event.getEvent() instanceof NewScenarioEventEngine)
			handleNewScenarioEventEngine((NewScenarioEventEngine) event
					.getEvent());
		else if (event.getEvent() instanceof GetScenarioEngine)
			handleGetScenarioEngine((GetScenarioEngine) event.getEvent());
		else if (event.getEvent() instanceof IUBankPercentEvent)
			handleBankPercentEvent((IUBankPercentEvent) event.getEvent());
		else if (event.getEvent() instanceof IUBankRequestEvent)
			handleBankRequestEvent((IUBankRequestEvent) event.getEvent());
		else if (event.getEvent() instanceof IUBankTransactionEvent)
			handleBankTransactionEvent((IUBankTransactionEvent) event
					.getEvent());
		else if (event.getEvent() instanceof IUBuyOffersChangeEvent)
			handleBuyOffersChangeEvent((IUBuyOffersChangeEvent) event
					.getEvent());
		else if (event.getEvent() instanceof IUBuyRequestEvent)
			handleBuyRequestEvent((IUBuyRequestEvent) event.getEvent());
		else if (event.getEvent() instanceof IUChatMessageEvent)
			handleChatMessageEvent((IUChatMessageEvent) event.getEvent());
		else if (event.getEvent() instanceof IUCompanyMessageEvent)
			handleCompanyMessageEvent((IUCompanyMessageEvent) event.getEvent());
		else if (event.getEvent() instanceof IUDividendPayoutEvent)
			handleDividendPayoutEvent((IUDividendPayoutEvent) event.getEvent());
		else if (event.getEvent() instanceof IUDividendVoteEvent)
			handleDividendVoteEvent((IUDividendVoteEvent) event.getEvent());
		else if (event.getEvent() instanceof IUDividendVotingEvent) // !!
			handleDividendVotingEvent((IUDividendVotingEvent) event.getEvent());
		else if (event.getEvent() instanceof IUDividendVotingProposalEvent) // !!
			handleDividendVotingProposalEvent((IUDividendVotingProposalEvent) event
					.getEvent());
		else if (event.getEvent() instanceof IUGameSnapshotEvent)
			handleGameSnapshotEvent((IUGameSnapshotEvent) event.getEvent());
		else if (event.getEvent() instanceof IUShareAllocationRequestEvent)
			handleShareAllocationRequestEvent((IUShareAllocationRequestEvent) event
					.getEvent());
		else if (event.getEvent() instanceof IUTransactionEvent)
			handleTransactionEvent((IUTransactionEvent) event.getEvent());
		else if (event.getEvent() instanceof IUTransferSharesEvent)
			handleTransferSharesEvent((IUTransferSharesEvent) event.getEvent());
		else if (event.getEvent() instanceof IUTurnEndEvent)
			handleTurnEndEvent((IUTurnEndEvent) event.getEvent());
		else if (event.getEvent() instanceof TurnEndEngineLocalEvent)
			handleTurnEndEngineLocalEvent((TurnEndEngineLocalEvent) event
					.getEvent());
		else if (event.getEvent() instanceof IUTurnStartEvent)
			handleTurnStartEvent((IUTurnStartEvent) event.getEvent());
		else if (event.getEvent() instanceof NewPlayerEngineEvent)
			handleNewPlayerEngineEvent((NewPlayerEngineEvent) event.getEvent());
		else if (event.getEvent() instanceof PlayerPresentEvent)
			handlePlayerPresentEvent((PlayerPresentEvent) event.getEvent());
		else if (event.getEvent() instanceof PlayerRemoveEngineEvent)
			handlePlayerRemoveEngineEvent((PlayerRemoveEngineEvent) event
					.getEvent());
		else if (event.getEvent() instanceof NewBankEngineEvent)
			handleNewBankEngineEvent((NewBankEngineEvent) event.getEvent());
		else if (event.getEvent() instanceof BankPresentEvent)
			handleBankPresentEvent((BankPresentEvent) event.getEvent());
		else if (event.getEvent() instanceof BankRemoveEngineEvent)
			handleBankRemoveEngineEvent((BankRemoveEngineEvent) event
					.getEvent());
		else if (event.getEvent() instanceof SetTeacherEngineEvent)
			handleSetTeacherEngineEvent((SetTeacherEngineEvent) event
					.getEvent());
		else {
			// log?
		}
		engineState.addToHistory(event.getEvent());

	}

	public IEngineDispatcher getEngineDispatcher() {
		return engineDispatcher;
	}

	public void setEngineDispatcher(IEngineDispatcher engineDispatcher) {
		this.engineDispatcher = engineDispatcher;
	}

	// handler methods
	private void handleNewScenarioEventEngine(NewScenarioEventEngine event) {
		engineState.setScenario(event.getScenario());
	}

	private void handleGetScenarioEngine(GetScenarioEngine event) {
		try {
			event.addScenario(new ScenarioEngine(engineState.getScenario()));
		} catch (InterruptedException e) {
			// TODO log
			e.printStackTrace();
		}
	}

	private void handleAddCashEvent(IUAddCashEvent event) {

		// TODO player presence

		for (long id : event.getReceiverIds()) {
			int num = engineState.getPlayerNum(id);
			if (num < 0)
				continue; // TODO fail
			StudentEngine s = engineState.getPlayer(num);
			s.setCash(s.getCash() + event.getAddedCash());
		}
		engineDispatcher.getAnswerSender().Send(event, EventSource.Networking);
	}

	private void handleBankPercentEvent(IUBankPercentEvent event) {

		// TODO player presence

		int num = engineState.getBankNum(event.getSenderId());
		if (num < 0)
			return;
		BankEngine b = engineState.getBank(num);
		b.setLoanInterestRate(event.getLoanInterestRate());
		b.setDepositInterestRate(event.getDepositInterestRate());
		// send to teacher
		BankPercentEngineEvent toTeacher = new BankPercentEngineEvent(event);
		toTeacher.clearRecievers();
		toTeacher.addReciever(engineState.getTeacher().getId());
		engineDispatcher.getAnswerSender().Send(toTeacher,
				EventSource.PlayerController);
		// send to students
		BankPercentEngineEvent toStudents = new BankPercentEngineEvent(event);
		toStudents.clearRecievers();
		toStudents.addRecievers(engineState.getPlayersId());
		engineDispatcher.getAnswerSender().Send(toStudents,
				EventSource.Networking);
	}

	private void handleBankRequestEvent(IUBankRequestEvent event) {
		// TODO player presence
		if (event.getReceiverIds().size() == 1
				&& (new ArrayList<Long>(event.getReceiverIds())).get(0) == engineState
						.getTeacher().getId()
				&& event.getTransactionType() == BankTransactionType.LOAN) {
			// bank to teacher
			BankLoanRequestEngine blr = new BankLoanRequestEngine(event
					.getInitialValue(), event.getPeriod());
			engineState.setBankToTeacherLoanRequest(event.getSenderId(), blr);
			engineDispatcher.getAnswerSender().Send(event,
					EventSource.PlayerController); // to teacher
		} else if (event.getTransactionType() == BankTransactionType.DEPOSIT) {
			BankDepositRequestEngine d = new BankDepositRequestEngine(event
					.getInitialValue(), event.getPeriod());
			for (long id : event.getReceiverIds())
				engineState.setBankDepositRequest(event.getSenderId(), id, d);
			engineDispatcher.getAnswerSender().Send(event,
					EventSource.Networking); // to banks

		} else if (event.getTransactionType() == BankTransactionType.LOAN) {
			BankLoanRequestEngine l = new BankLoanRequestEngine(event
					.getInitialValue(), event.getPeriod());
			for (long id : event.getReceiverIds())
				engineState.setBankLoanRequest(event.getSenderId(), id, l);
			engineDispatcher.getAnswerSender().Send(event,
					EventSource.Networking); // to banks
		} else
			return; // Fail
	}

	private void handleBankTransactionEvent(IUBankTransactionEvent event) {
		/*
		 * long bankId = event.getSenderId(); for (long playerId :
		 * event.getReceiverIds()) { if (bankId ==
		 * engineState.getTeacher().getId()) { // loan request to teacher if
		 * (event.getTransactionType() != BankTransactionType.LOAN &&
		 * event.getTransactionType() != BankTransactionType.LOAN_REPAY) return;
		 * // fail
		 * 
		 * } }
		 */
		// TODO finish
	}

	private void handleBuyOffersChangeEvent(IUBuyOffersChangeEvent event) {
		// new buy offer
		// clear old
		// for (int i = 0; i < engineState.getOffers().size(); i++) {
		// BuyOfferEngine bo = engineState.getOffers().get(i);
		// if (bo.getSellerId() == event.getSellerId()
		// && bo.getShareHolding().getCompanyId() == event
		// .getCompanyId()) {
		// engineState.getOffers().remove(i);
		// i--;
		// }
		// }
		//		
		engineState.removeAllOffers(event.getSellerId(), event.getCompanyId());
		// add new
		for (IUBuyOffer bo : event.getUnmodifiableBuyOffers()) {
			engineState.getOffers().add(new BuyOfferEngine(bo));
		}
		engineState.removeInvalidOffers(event.getSellerId(), event
				.getCompanyId());
		// send to all
		BuyOffersChangeEventEngine ev = new BuyOffersChangeEventEngine(event);
		ev.clearRecievers();
		ev.addRecievers(engineState.getPlayersId());
		ev.setNewShareMarketValue(engineState.getShareMarketValue(event
				.getCompanyId()));
		ev.setNewStockIndex(engineState.updateStockIndex());
		engineDispatcher.getAnswerSender().Send(ev, EventSource.Networking);
		// to teacher
		BuyOffersChangeEventEngine evT = new BuyOffersChangeEventEngine(ev);
		evT.clearRecievers();
		evT.addReciever(engineState.getTeacher().getId());
		engineDispatcher.getAnswerSender().Send(evT,
				EventSource.PlayerController);
	}

	private void handleBuyRequestEvent(IUBuyRequestEvent event) {
		if (!engineState.hasOffer(event.getOfferOfInterest())) {
			return; // fail
		}
		
		if (engineState.getTeacher().getId() != event.getBuyerId()) {
			if (engineState.getPlayer(engineState.getPlayerNum(event.getBuyerId())) 
					.getCash() < event.getOfferOfInterest().getSuggestedValue()) {
			return; // fail: not enough money
			}
		}
		// perform transaction
		double value = event.getOfferOfInterest().getSuggestedValue();
		long fromId = event.getOfferOfInterest().getSellerId();
		int fromNum = engineState.getPlayerNum(fromId);
		long toId = event.getBuyerId();
		if (toId == fromId)
			return;
		int toNum = engineState.getPlayerNum(toId);
		long companyId = event.getOfferOfInterest().getShareHolding()
				.getCompanyId();
		int companyNum = engineState.getCompanyNum(companyId);
		boolean fromTeacher = (fromId == engineState.getTeacher().getId());
		boolean toTeacher = (toId == engineState.getTeacher().getId());
		int aNumToSell = event.getOfferOfInterest().getShareHolding()
				.getAmount();
		ArrayList<ShareHoldingEngine> shareHoldingFrom;
		ArrayList<ShareHoldingEngine> shareHoldingTo;
		if (fromTeacher)
			shareHoldingFrom = engineState.getTeacher_share();
		else
			shareHoldingFrom = engineState.getPlayers_share().get(fromNum);
		if (toTeacher)
			shareHoldingTo = engineState.getTeacher_share();
		else
			shareHoldingTo = engineState.getPlayers_share().get(toNum);
			
		// check is share available
		if (shareHoldingFrom.get(companyNum)
				.getAmount() < aNumToSell) {
			// not enough
			boolean rez = engineState.removeInvalidOffers(fromId, companyId);
			if (rez) {
				ArrayList<BuyOfferEngine> current_offers = engineState
						.getAllOffers(fromId, companyId);
				BuyOffersChangeEventEngine ev = new BuyOffersChangeEventEngine(
						event.getEventTime(), engineState.getPlayersId(), -1,
						companyId, true, engineState.getScenario().getCompanies()
								.get(companyNum).getShareMarketValue(),
						engineState.updateStockIndex(), fromId, current_offers);
				engineDispatcher.getAnswerSender().Send(ev,
						EventSource.Networking);
				BuyOffersChangeEventEngine evT = new BuyOffersChangeEventEngine(
						ev);
				evT.clearRecievers();
				evT.addReciever(engineState.getTeacher().getId());
				engineDispatcher.getAnswerSender().Send(evT,
						EventSource.PlayerController);
				return;
			}
		}

		// money
		if (!fromTeacher)
			engineState.getPlayer(fromNum).setCash(
				engineState.getPlayer(fromNum).getCash() + value);
		if (!toTeacher)
			engineState.getPlayer(toNum).setCash(
				engineState.getPlayer(toNum).getCash() - value);
		// share
		shareHoldingFrom.get(companyNum).setAmount(
				shareHoldingFrom.get(companyNum)
						.getAmount()
						- aNumToSell);
		shareHoldingTo.get(companyNum).setAmount(
				shareHoldingTo.get(companyNum)
						.getAmount()
						+ aNumToSell);
		// market value
		engineState.getScenario().getCompanies().get(companyNum)
				.setShareMarketValue(
						event.getOfferOfInterest().getSuggestedValue()
								/ event.getOfferOfInterest().getShareHolding()
										.getAmount());
		// send TransactionEvent
		long teacherId = engineState.getTeacher().getId();
		ArrayList<Long> recieverFrom = new ArrayList<Long>(1);
		recieverFrom.add(fromId);
		TransactionEventEngine evFrom = new TransactionEventEngine(event
				.getEventTime(), recieverFrom, -1, new TransactionEngine(toId,
				fromId, new ShareHoldingEngine(event.getOfferOfInterest()
						.getShareHolding()), new GameTimeEngine(event
						.getEventTime()), event.getOfferOfInterest()
						.getSuggestedValue()), false);
		engineDispatcher.getAnswerSender().Send(evFrom, (fromTeacher) ? EventSource.PlayerController : EventSource.Networking);

		TransactionEventEngine evTo = new TransactionEventEngine(evFrom);
		evTo.clearRecievers();
		evTo.addReciever(toId);
		engineDispatcher.getAnswerSender().Send(evTo, (toId == teacherId) ? EventSource.PlayerController : EventSource.Networking);
		// we should send all transactions to teacher too (bug #31)
		if (!fromTeacher && !toTeacher)
			engineDispatcher.getAnswerSender().Send(evTo, EventSource.PlayerController);

		// update offers

		// remove current offer
		int offerNum = engineState.getOfferNum(event.getOfferOfInterest());
		engineState.getOffers().remove(offerNum);

		// remove newly-invalid offers
		engineState.removeInvalidOffers(fromId, companyId);
		ArrayList<BuyOfferEngine> current_offers = engineState.getAllOffers(
				fromId, companyId);
		BuyOffersChangeEventEngine _ev = new BuyOffersChangeEventEngine(event
				.getEventTime(), engineState.getPlayersId(), -1, companyId, true, 
				engineState.getScenario().getCompanies().get(companyNum)
						.getShareMarketValue(), engineState.updateStockIndex(),
				fromId, current_offers);
		engineDispatcher.getAnswerSender().Send(_ev, EventSource.Networking);
		BuyOffersChangeEventEngine _evT = new BuyOffersChangeEventEngine(_ev);
		_evT.clearRecievers();
		_evT.addReciever(engineState.getTeacher().getId());
		engineDispatcher.getAnswerSender().Send(_evT,
				EventSource.PlayerController);
	}

	private void handleChatMessageEvent(IUChatMessageEvent event) {
		if (event.isBroadcast())
		{
			ChatMessageEventEngine toStudents = new ChatMessageEventEngine(event);
			toStudents.clearRecievers();
			toStudents.addRecievers(engineState.getPlayersId());
			engineDispatcher.getAnswerSender().Send(toStudents, EventSource.Networking);
			
			ChatMessageEventEngine toTeacher = new ChatMessageEventEngine(event);
			toTeacher.clearRecievers();
			toTeacher.addReciever(engineState.getTeacher().getId());
			engineDispatcher.getAnswerSender().Send(toTeacher, EventSource.PlayerController);
		}
		else
		{
			boolean hasT = false;
			ChatMessageEventEngine toStudents = new ChatMessageEventEngine(event);
			for (Long plId : toStudents.getReceiverIds())
			{
				if (plId == engineState.getTeacher().getId())
				{
					ChatMessageEventEngine toTeacher = new ChatMessageEventEngine(event);
					toTeacher.clearRecievers();
					toTeacher.addReciever(engineState.getTeacher().getId());
					engineDispatcher.getAnswerSender().Send(toTeacher, EventSource.PlayerController);
					hasT = true;
				}
			}
//			if (hasT)
//				toStudents.getReceiverIds().remove(engineState.getTeacher().getId());
			if (hasT && toStudents.getReceiverIds().size() < 2)
				return;
			engineDispatcher.getAnswerSender().Send(toStudents, EventSource.Networking);
		}
	}

	private void handleCompanyMessageEvent(IUCompanyMessageEvent event) {

		// TODO player presence
		
		CompanyMessageEngineEvent ev = new CompanyMessageEngineEvent(event);
		if (event.getCompanyMessage().getType() == CompanyMessageType.OFFICIAL) {
			ev.clearRecievers();
			ev.addRecievers(engineState.getPlayersId());
			ev.setBroadcast(true);
		}
		ev.getCompanyMessage().setPublishTime(new GameTimeEngine(event.getEventTime()));
		engineDispatcher.getAnswerSender().Send(ev, EventSource.Networking);
		
		CompanyMessageEngineEvent evT = new CompanyMessageEngineEvent(ev);
		if (event.getCompanyMessage().getType() == CompanyMessageType.OFFICIAL) {
			evT.clearRecievers();
			evT.addReciever(engineState.getTeacher().getId());
			evT.setBroadcast(true);
		}
		evT.getCompanyMessage().setPublishTime(new GameTimeEngine(event.getEventTime()));
		engineDispatcher.getAnswerSender().Send(evT, EventSource.PlayerController);

		for (CompanyEngine c : engineState.getScenario().getCompanies()) {
			if (c.getId() == event.getCompanyMessage().getCompanyId()) {
				// find appropriate company message
				for (CompanyMessageEngine m : c.getAllMessages()) {
					if (m.getId() == event.getCompanyMessage().getId()) {
						m.setPublished(true);
						m.setPublishTime(new GameTimeEngine(event
								.getEventTime()));
						c.setExpectedProfit(m.effectOnExpectedProfit(c
								.getExpectedProfit()));
						c.getPublishedMessages().add(new TextOnlyCompanyMessageEngine((IUTextOnlyCompanyMessage)m));
						break;
					}
				}

			}
		}
	}

	private void handleDividendPayoutEvent(IUDividendPayoutEvent event) {

	}

	private void handleDividendVoteEvent(IUDividendVoteEvent event) {

	}

	private void handleDividendVotingEvent(IUDividendVotingEvent event) {

	}

	private void handleDividendVotingProposalEvent(
			IUDividendVotingProposalEvent event) {

	}

	private void handleGameSnapshotEvent(IUGameSnapshotEvent event) {

	}

	private void handleShareAllocationRequestEvent(
			IUShareAllocationRequestEvent event) {
		// TODO check valid game state
		// TODO sending to teacher error!
/*		int i, j;

		for (IUShareAllocationRequest r : event.getRequests()) {
			engineState.addPlayerAllocation(r.getCompanyId(), event
					.getSenderId(), r.getRequestedShareAmount());
		}

		if (engineState.allAllocationRecieved()) {
			// perform share distribution
			int pl_num = engineState.getPlayers().size();
			int comp_num = engineState.getScenario().getCompanies().size();
			double[] player_money = new double[pl_num];
			for (i = 0; i < pl_num; i++)
				player_money[i] = engineState.getScenario()
						.getStartingCashValue();

			for (i = 0; i < comp_num; i++) {
				int total = 0;
				for (j = 0; j < pl_num; j++) {
					if (player_money[j] >= engineState
							.getPlayerAllocation(i, j)
							* engineState.getScenario().getCompanies().get(i)
									.getShareMarketValue())
						total += engineState.getPlayerAllocation(i, j);
					else
						engineState.setPlayerAllocation(i, j, 0);
				}
				int overall = (int) ((1 - engineState.getScenario()
						.getCompanies().get(i).getTeacherSharePart()) * engineState
						.getScenario().getCompanies().get(i)
						.getTotalSharesAmount());
				double k = total <= overall ? 1.0 : (double) overall
						/ (double) total;
				int sum = 0;
				for (j = 0; j < pl_num; j++) {
					int t_n = (int) (k * engineState.getPlayerAllocation(i, j));
					player_money[j] -= t_n
							* engineState.getScenario().getCompanies().get(i)
									.getShareMarketValue();
					long playerId = engineState.getPlayersId().get(j);
					ShareHoldingEngine shareHolding = new ShareHoldingEngine(
							t_n, engineState.getScenario().getCompanies()
									.get(i).getId(), playerId);
					engineState.getPlayers_share().get(j).set(i, shareHolding);
					// send IUTransactionEvent
					long teacherId = engineState.getTeacher().getId();
					GameTimeEngine gt = new GameTimeEngine(new Date(0), 0, true);
					ArrayList<Long> rec = new ArrayList<Long>(1);
					rec.add(playerId);
					TransactionEventEngine ev = new TransactionEventEngine(gt,
							rec, -1, new TransactionEngine(playerId, teacherId,
									shareHolding, gt, t_n
											* engineState.getScenario()
													.getCompanies().get(i)
													.getShareMarketValue()), false);
					engineDispatcher.getAnswerSender().Send(ev,
							EventSource.Networking);
					sum += t_n;
				}
				ShareHoldingEngine _shareHolding = new ShareHoldingEngine(
						engineState.getScenario().getCompanies().get(i)
								.getTotalSharesAmount()
								- sum, engineState.getScenario().getCompanies()
								.get(i).getId(), engineState.getPlayersId()
								.get(j));
				engineState.getTeacher_share().set(i, _shareHolding);
				long _teacherId = engineState.getTeacher().getId();
				GameTimeEngine _gt = new GameTimeEngine(new Date(0), 0, true);
				ArrayList<Long> _rec = new ArrayList<Long>(1);
				_rec.add(_teacherId);
				TransactionEventEngine _ev = new TransactionEventEngine(_gt,
						_rec, -1, new TransactionEngine(_teacherId, -1,
								_shareHolding, _gt, 0), false);
				// NOTE! Shares value for teacher equals to 0.
				engineDispatcher.getAnswerSender().Send(_ev,
						EventSource.Networking);
			}

			for (j = 0; j < pl_num; j++) {
				engineState.getPlayer(j).setCash(player_money[j]);
			}
		}
*/
	}

	private void handleTransactionEvent(IUTransactionEvent event) {
		return;
	}

	private void handleTransferSharesEvent(IUTransferSharesEvent event) {
		if (event.getReceiverIds().size() < 1)
			return;
		long recieverId = (new ArrayList<Long>(event.getReceiverIds())).get(0);
		long senderId = event.getSenderId();
		if (!engineState.hasPlayer(recieverId))
			return;
		int player_ind = engineState.getPlayerNum(recieverId);
		int sh_num = event.getTransferredShareHolding().getAmount();
		if (senderId != engineState.getTeacher().getId())
			return; // only teacher can do this
		for (int j = 0; j < engineState.getScenario().getCompanies().size(); j++) {
			if (engineState.getScenario().getCompanies().get(j).getId() == event
					.getTransferredShareHolding().getCompanyId()) {
				if (engineState.getTeacher_share().get(j).getAmount() <= sh_num)
					return;
				engineState.getTeacher_share().get(j).setAmount(
						engineState.getTeacher_share().get(j).getAmount()
								- sh_num);
				int cur_num = engineState.getPlayers_share().get(player_ind)
						.get(j).getAmount();
				engineState.getPlayers_share().get(player_ind).get(j)
						.setAmount(cur_num + sh_num);
				break;
			}
		}
		engineDispatcher.getAnswerSender().Send(event, EventSource.Networking);
		engineDispatcher.getAnswerSender().Send(event, EventSource.PlayerController);
	}

	private void handleTurnEndEvent(IUTurnEndEvent event) {
		if (engineState.isTurnEnded())
			return; // TODO fail
		endTurn();
	}

	private void handleTurnEndEngineLocalEvent(TurnEndEngineLocalEvent event) {
		if (engineState.isTurnEnded()
				|| engineState.getTurnNum() != event.getNum())
			return; // Already finished
		endTurn();
	}

	private void endTurn() {
		engineState.setTurnEnded(true);
		// TODO finish implementation
		// send bank requests
		// clear all bank requests
		engineState.clearAllBankRequests();
		engineState.clearAllBankToTeacherRequests();
		// perform deposit and loan calculations
		// calculate rating
		ArrayList<Double> ranking = new ArrayList<Double>();
		for (int i = 0; i < engineState.getPlayers().size(); i++) {
			double v = engineState.getPlayer(i).getCash();
			for (int j = 0; j < engineState.getScenario().getCompanies().size(); j++) {
				v += engineState.getScenario().getCompanies().get(j)
						.getShareMarketValue()
						* engineState.getPlayers_share().get(i).get(j)
								.getAmount();
			}
			ranking.add(v);
		}
		TurnEndEventEngine e = new TurnEndEventEngine(new GameTimeEngine(
				new Date(0), engineState.getTurnNum(), true), engineState
				.getPlayersId(), -1, new ArrayList<IUPlayerRatingValue>(), true);
		for (int k = 0; k < ranking.size(); k++) {
			double max = ranking.get(0);
			int maxn = 0;
			for (int i = 1; i < ranking.size(); i++) {
				if (ranking.get(i) > max) {
					max = ranking.get(i);
					maxn = i;
				}
			}
			ranking.set(maxn, -1e100);
			e.getRatingList().add(
					new PlayerRatingValue(engineState.getPlayer(maxn).getId(),
							max));
		}
		e.setBroadcast(true);
		engineDispatcher.getAnswerSender().Send(e, EventSource.Networking);
		TurnEndEventEngine eT = new TurnEndEventEngine(e);
		eT.clearRecievers();
		eT.addReciever(engineState.getTeacher().getId());
		engineDispatcher.getAnswerSender().Send(eT,
				EventSource.PlayerController);
	}

	@Hack
	private void shareDistributionHack()
	{
		int i,j;
		int pl_num = engineState.getPlayers().size();
		int comp_num = engineState.getScenario().getCompanies().size();
		for (i = 0; i < comp_num; i++) {
			int t_n = 0;
			if (pl_num > 0)
				t_n = engineState.getScenario().getCompanies().get(i).getTotalSharesAmount() / pl_num;
			int sum = 0;
			for (j = 0; j < pl_num; j++) {
				long playerId = engineState.getPlayersId().get(j);
				ShareHoldingEngine shareHolding = new ShareHoldingEngine(
						t_n, engineState.getScenario().getCompanies()
								.get(i).getId(), playerId);
				engineState.getPlayers_share().get(j).set(i, shareHolding);
				// send IUTransactionEvent
				long teacherId = engineState.getTeacher().getId();
				GameTimeEngine gt = new GameTimeEngine(new Date(0), 0, true);
				ArrayList<Long> rec = new ArrayList<Long>(1);
				rec.add(playerId);
				TransactionEventEngine ev = new TransactionEventEngine(gt,
						rec, -1, new TransactionEngine(playerId, teacherId,
								shareHolding, gt, 0), false);
				engineDispatcher.getAnswerSender().Send(ev,
						EventSource.Networking);
				sum += t_n;
			}
			ShareHoldingEngine _shareHolding = new ShareHoldingEngine(
					engineState.getScenario().getCompanies().get(i)
							.getTotalSharesAmount()
							- sum, engineState.getScenario().getCompanies()
							.get(i).getId(), engineState.getTeacher().getId());
			engineState.getTeacher_share().set(i, _shareHolding);
			long _teacherId = engineState.getTeacher().getId();
			GameTimeEngine _gt = new GameTimeEngine(new Date(0), 0, true);
			ArrayList<Long> _rec = new ArrayList<Long>(1);
			_rec.add(_teacherId);
			TransactionEventEngine _ev = new TransactionEventEngine(_gt,
					_rec, -1, new TransactionEngine(_teacherId, -1,
							_shareHolding, _gt, 0), false);
			engineDispatcher.getAnswerSender().Send(_ev,
					EventSource.PlayerController);	
		}
	}
	
	private void handleTurnStartEvent(IUTurnStartEvent event) {
		if (engineState.isTurnEnded() == false)
			// TODO fail
			return;
		engineState.setTurnEnded(false);
		engineState.setTurnNum(engineState.getTurnNum() + 1);
		final long sleepLength = engineState.getScenario()
				.getTurnLengthMinutes() * 60 * 1000;
		final int turnnum = engineState.getTurnNum();
		(new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(sleepLength);
				} catch (InterruptedException e) {
					e.printStackTrace(); // TODO log
				}
				engineDispatcher.handleEvent(new EventSourcePair(
						EventSource.Engine,
						new TurnEndEngineLocalEvent(turnnum)));
			}
		})).start();
		// Send to all
		GameTimeEngine time = new GameTimeEngine(new Date(0), engineState
				.getTurnNum(), false);
		TurnStartEventEngine messageDraft = new TurnStartEventEngine(
				engineState.getTurnNum(), time, new ArrayList<Long>(), -1, true);
		TurnStartEventEngine messageBroadcast = new TurnStartEventEngine(
				messageDraft);
		TurnStartEventEngine messageTeacher = new TurnStartEventEngine(
				messageDraft);

		for (StudentEngine p : engineState.getPlayers()) {
			if (engineState.isPlayerActive(p.getId())) {
				messageBroadcast.addReciever(p.getId());
			} else {
				TurnStartEventEngine ev = new TurnStartEventEngine(messageDraft);
				ev.addReciever(p.getId());
				engineState.addDelayedMessage(p.getId(), new EventSourcePair(
						EventSource.Networking, ev));
			}
		}
		for (BankEngine b : engineState.getBanks()) {
			if (engineState.hasPlayer(b.getId())) // TODO not needed
				continue;

			if (engineState.isBankActive(b.getId())) {
				messageBroadcast.addReciever(b.getId());
			} else {
				TurnStartEventEngine ev = new TurnStartEventEngine(messageDraft);
				ev.addReciever(b.getId());
				engineState.addDelayedMessageBank(b.getId(),
						new EventSourcePair(EventSource.Networking, ev));
			}
		}
		engineDispatcher.getAnswerSender().Send(messageBroadcast,
				EventSource.Networking);

		messageTeacher.addReciever(engineState.getTeacher().getId());
		engineDispatcher.getAnswerSender().Send(messageTeacher,
				EventSource.PlayerController);
		// GameSnapshot
		List<IUPlayer> allPlayers = new ArrayList<IUPlayer>();
		allPlayers.add(engineState.getTeacher());
		allPlayers.addAll(engineState.getPlayers());
		GameSnapshotEventEngine ev = new GameSnapshotEventEngine(time,
				engineState.getPlayersId(), -1, true, engineState.getOffers(),
				new ArrayList<ChatMessageEventEngine>(), allPlayers,
				engineState.getScenario(), engineState.updateStockIndex());
		engineDispatcher.getAnswerSender().Send(ev, EventSource.Networking);

		GameSnapshotEventEngine evT = new GameSnapshotEventEngine(ev);
		evT.clearRecievers();
		evT.addReciever(engineState.getTeacher().getId());
		engineDispatcher.getAnswerSender().Send(evT,
				EventSource.PlayerController);

		
		if (event.getNewTurnNumber() == 1)
		{
			this.shareDistributionHack();
		}
	}

	private void handleNewPlayerEngineEvent(NewPlayerEngineEvent event) {
		engineState.addNewPlayer(event.getPl());
	}

	private void handlePlayerPresentEvent(PlayerPresentEvent event) {
		if (!engineState.hasPlayer(event.getPlayerId()))
			return;
		boolean oldState = engineState.isPlayerActive(event.getPlayerId());
		engineState.setPlayerActive(event.getPlayerId(), event.isPresent());
		if (oldState == false && event.isPresent() == true) {
			// we need to send all delayed messages
			Collection<EventSourcePair> el = engineState
					.getDelayedMessages(event.getPlayerId());
			for (EventSourcePair e : el) {
				engineDispatcher.getAnswerSender().Send(e.getEvent(),
						e.getSource());
			}
			engineState.cleanDelayedMessages(event.getPlayerId());
		}
	}

	private void handlePlayerRemoveEngineEvent(PlayerRemoveEngineEvent event) {
		engineState.removePlayer(event.getId());
	}

	private void handleNewBankEngineEvent(NewBankEngineEvent event) {
		engineState.addNewBank(event.getBank());
	}

	private void handleBankPresentEvent(BankPresentEvent event) {
		if (!engineState.hasBank(event.getId()))
			return;
		long id = event.getId();
		boolean isActive = event.isPresent();
		boolean oldState = engineState.isBankActive(id);
		engineState.setBankActive(id, isActive);
		if (oldState == false && isActive == true) {
			Collection<EventSourcePair> el = engineState
					.getDelayedMessagesBank(id);
			for (EventSourcePair e : el) {
				engineDispatcher.getAnswerSender().Send(e.getEvent(),
						e.getSource());
			}
			engineState.cleanDelayedMessagesBank(id);
		}
	}

	private void handleBankRemoveEngineEvent(BankRemoveEngineEvent event) {
		engineState.removeBank(event.getId());
	}

	private void handleSetTeacherEngineEvent(SetTeacherEngineEvent event) {
		engineState.setTeacher(event.getTeacher());
	}

	/*
	 * // sender functions private void sendToAllPlayers(IUGameEvent message) {
	 * for (PlayerEngine pl : engineState.getPlayers()) {
	 * sendToPlayer(pl.getId(), message); } }
	 * 
	 * private void sendToAllBanks(IUGameEvent message) { for (BankEngine b :
	 * engineState.getBanks()) { sendToBank(b.getId(), message); } }
	 * 
	 * private void sendToTeacher(IUGameEvent message) {
	 * engineDispatcher.getAnswerSender().Send(message,
	 * EventSource.PlayerController); }
	 * 
	 * private void sendToPlayer(long player_id, IUGameEvent message) { if
	 * (!engineState.hasPlayer(player_id)) return; if
	 * (!engineState.isPlayerActive(player_id))
	 * engineState.addDelayedMessage(player_id, new EventSourcePair(
	 * EventSource.Networking, message)); else
	 * engineDispatcher.getAnswerSender().Send(message, EventSource.Networking);
	 * }
	 * 
	 * private void sendToBank(long bank_id, IUGameEvent message) { if
	 * (!engineState.hasBank(bank_id)) return; if
	 * (!engineState.isBankActive(bank_id))
	 * engineState.addDelayedMessageBank(bank_id, new EventSourcePair(
	 * EventSource.Networking, message)); else
	 * engineDispatcher.getAnswerSender().Send(message, EventSource.Networking);
	 * }
	 */

}

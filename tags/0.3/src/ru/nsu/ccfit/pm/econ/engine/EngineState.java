/**
 * 
 */
package ru.nsu.ccfit.pm.econ.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenario;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.engine.data.BankDepositRequestEngine;
import ru.nsu.ccfit.pm.econ.engine.data.BankLoanRequestEngine;
import ru.nsu.ccfit.pm.econ.engine.data.BankTransactionEngine;
import ru.nsu.ccfit.pm.econ.engine.data.BuyOfferEngine;
import ru.nsu.ccfit.pm.econ.engine.data.CompanyEngine;
import ru.nsu.ccfit.pm.econ.engine.data.ScenarioEngine;
import ru.nsu.ccfit.pm.econ.engine.data.ShareHoldingEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.BankEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.StudentEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.TeacherEngine;

/**
 * State variables for engine
 * 
 * @author pupatenko
 * 
 */
public class EngineState {
	private ArrayList<IUGameEvent> history;
	private ArrayList<StudentEngine> players;
	private ArrayList<Boolean> player_active;
	private ArrayList<ArrayList<EventSourcePair>> player_delayed_messages;
	private ArrayList<ArrayList<BankLoanRequestEngine>> player_loan_requests;
	private ArrayList<ArrayList<BankDepositRequestEngine>> player_deposit_requests;
	private ArrayList<BankLoanRequestEngine> bank_loan_requests;

	private ArrayList<BankTransactionEngine> bank_loans;
	private ArrayList<BankTransactionEngine> player_loans;
	private ArrayList<BankTransactionEngine> player_deposits;
	private ArrayList<BankEngine> banks;
	private ArrayList<Boolean> bank_active;
	private ArrayList<ArrayList<EventSourcePair>> bank_delayed_messages;
	private TeacherEngine teacher;

	private ArrayList<ArrayList<Integer>> players_allocation_requests;
	private ArrayList<Boolean> players_allocation_recieved;

	// TODO initialize on scenario load
	private ArrayList<ArrayList<ShareHoldingEngine>> players_share;
	private ArrayList<ShareHoldingEngine> teacher_share;
	private ArrayList<BuyOfferEngine> offers;

	private int turnNum;
	private boolean turnEnded;

	private double stockIndex;

	private ScenarioEngine scenario;

	public EngineState() {
		offers = new ArrayList<BuyOfferEngine>();
		players_allocation_requests = new ArrayList<ArrayList<Integer>>();
		players_allocation_recieved = new ArrayList<Boolean>();
		history = new ArrayList<IUGameEvent>();
		players = new ArrayList<StudentEngine>();
		player_active = new ArrayList<Boolean>();
		player_delayed_messages = new ArrayList<ArrayList<EventSourcePair>>();
		player_loan_requests = new ArrayList<ArrayList<BankLoanRequestEngine>>();
		player_deposit_requests = new ArrayList<ArrayList<BankDepositRequestEngine>>();
		bank_loan_requests = new ArrayList<BankLoanRequestEngine>();
		bank_loans = new ArrayList<BankTransactionEngine>();
		player_loans = new ArrayList<BankTransactionEngine>();
		player_deposits = new ArrayList<BankTransactionEngine>();
		banks = new ArrayList<BankEngine>();
		bank_active = new ArrayList<Boolean>();
		bank_delayed_messages = new ArrayList<ArrayList<EventSourcePair>>();
		players_share = new ArrayList<ArrayList<ShareHoldingEngine>>();
		teacher_share = new ArrayList<ShareHoldingEngine>();
		turnNum = 0;
		turnEnded = true;
		stockIndex = 0;
	}

	public ArrayList<BuyOfferEngine> getAllOffers(long playerId, long companyId) {
		ArrayList<BuyOfferEngine> rez = new ArrayList<BuyOfferEngine>();
		for (BuyOfferEngine bo : offers) {
			if (bo.getSellerId() == playerId
					&& bo.getShareHolding().getCompanyId() == companyId)
				rez.add(new BuyOfferEngine(bo));
		}
		return rez;
	}

	public void removeAllOffers(long playerId, long companyId) {
		for (int i = 0; i < offers.size(); i++) {
			BuyOfferEngine bo = offers.get(i);
			if (bo.getSellerId() == playerId
					&& bo.getShareHolding().getCompanyId() == companyId) {
				offers.remove(i);
				i--;
			}
		}
	}

	public boolean hasOffer(IUBuyOffer bo) {
		for (BuyOfferEngine tek : offers) {
			if (tek.getSellerId() == bo.getSellerId()
					&& tek.getSuggestedValue() == bo.getSuggestedValue()
					&& tek.getShareHolding().getCompanyId() == bo
							.getShareHolding().getCompanyId()
					&& tek.getShareHolding().getAmount() == bo
							.getShareHolding().getAmount()
					&& tek.getShareHolding().getOwnerId() == bo
							.getShareHolding().getOwnerId())
				return true;

		}
		return false;
	}

	public int getOfferNum(IUBuyOffer bo) {
		for (int i = 0; i < offers.size(); i++) {
			BuyOfferEngine tek = offers.get(i);
			if (tek.getSellerId() == bo.getSellerId()
					&& tek.getSuggestedValue() == bo.getSuggestedValue()
					&& tek.getShareHolding().getCompanyId() == bo
							.getShareHolding().getCompanyId()
					&& tek.getShareHolding().getAmount() == bo
							.getShareHolding().getAmount()
					&& tek.getShareHolding().getOwnerId() == bo
							.getShareHolding().getOwnerId())
				return i;

		}
		return -1;
	}

	public boolean removeInvalidOffers(long playerId, long companyId) {
		boolean removed = false;
		for (int i = 0; i < offers.size(); i++) {
			BuyOfferEngine bo = offers.get(i);
			ArrayList<ShareHoldingEngine> share_holding;
			if (teacher.getId() == bo.getSellerId())
				share_holding = teacher_share;
			else
				share_holding = players_share.get(getPlayerNum(bo.getSellerId()));
			if (bo.getSellerId() == playerId
					&& bo.getShareHolding().getCompanyId() == companyId) {
				if (bo.getShareHolding().getAmount() > share_holding.get(
						getCompanyNum(bo.getShareHolding().getCompanyId()))
						.getAmount()) {
					offers.remove(i);
					i--;
					removed = true;
				}

			}

		}
		return removed;
	}

	public int getCompanyNum(long id) {
		for (int i = 0; i < scenario.getCompanies().size(); i++) {
			CompanyEngine c = scenario.getCompanies().get(i);
			if (c.getId() == id)
				return i;
		}
		return -1;
	}

	public ArrayList<BuyOfferEngine> getOffers() {
		return offers;
	}

	public void setOffers(ArrayList<BuyOfferEngine> offers) {
		this.offers = offers;
	}

	public double getShareMarketValue(long companyId) {
		for (CompanyEngine j : scenario.getCompanies()) {
			if (j.getId() == companyId)
				return j.getShareMarketValue();
		}
		return 0;
	}

	public int getPlayerAllocation(int compNum, int playerNum) {
		return players_allocation_requests.get(playerNum).get(compNum);
	}

	public void setPlayerAllocation(int compNum, int playerNum, int num) {
		players_allocation_requests.get(playerNum).set(compNum, num);
	}

	public void addPlayerAllocation(long compId, long playerId, int num) {
		if (!hasPlayer(playerId))
			return;
		int n = this.getPlayerNum(playerId);
		for (int i = 0; i < scenario.getCompanies().size(); i++) {
			if (scenario.getCompanies().get(i).getId() == compId) {
				players_allocation_recieved.set(n, true);
				players_allocation_requests.get(n).set(i, num);
			}
		}
	}

	public boolean allAllocationRecieved() {
		boolean rez = true;
		for (int i = 0; i < players.size(); i++) {
			rez = rez
					&& (!player_active.get(i) || players_allocation_recieved
							.get(i));
		}
		return rez;
	}

	public ArrayList<BankTransactionEngine> getBank_loans() {
		return bank_loans;
	}

	public ArrayList<BankTransactionEngine> getPlayer_loans() {
		return player_loans;
	}

	public ArrayList<BankTransactionEngine> getPlayer_deposits() {
		return player_deposits;
	}

	// TODO after company list creation
	public double updateStockIndex() {
		// arithmetical mean is used for simplicity
		stockIndex = 0;
		for (int i = 0; i < scenario.getCompanies().size(); i++) {
			stockIndex += scenario.getCompanies().get(i).getShareMarketValue();
		}
		stockIndex /= (double) scenario.getCompanies().size();
		return stockIndex;
	}

	private void initAllocations() {
		players_allocation_recieved.clear();
		players_allocation_requests.clear();
		for (int j = 0; j < scenario.getCompanies().size(); j++) {
			players_allocation_recieved.add(false);
		}
		for (int i = 0; i < players.size(); i++) {
			players_allocation_requests.add(new ArrayList<Integer>());
			for (int j = 0; j < scenario.getCompanies().size(); j++) {
				players_allocation_requests.get(i).add(0);
			}
		}
	}

	private void initShareHoldingsTeacher() {
		teacher_share.clear();
		for (int j = 0; j < scenario.getCompanies().size(); j++) {
			teacher_share.add(new ShareHoldingEngine(0, scenario.getCompanies()
					.get(j).getId(), teacher.getId()));
		}
	}

	private void initShareHoldings() {
		players_share.clear();
		if (teacher != null) {
			teacher_share.clear();
			for (int j = 0; j < scenario.getCompanies().size(); j++) {
				teacher_share.add(new ShareHoldingEngine(0, scenario
						.getCompanies().get(j).getId(), teacher.getId()));
			}
		}
		for (int i = 0; i < players.size(); i++) {
			players_share.add(new ArrayList<ShareHoldingEngine>());
			for (int j = 0; j < scenario.getCompanies().size(); j++) {
				players_share.get(i).add(
						new ShareHoldingEngine(0, scenario.getCompanies()
								.get(j).getId(), players.get(i).getId()));
			}
		}
	}

	public ArrayList<ArrayList<ShareHoldingEngine>> getPlayers_share() {
		return players_share;
	}

	public ArrayList<ShareHoldingEngine> getTeacher_share() {
		return teacher_share;
	}

	public void clearAllBankRequests() {
		for (int i = 0; i < players.size(); i++) {
			for (int j = 0; j < banks.size(); j++) {
				player_loan_requests.get(i).set(j, null);
				player_deposit_requests.get(i).set(j, null);
			}
		}
	}

	public void clearAllBankToTeacherRequests() {
		for (int i = 0; i < banks.size(); i++)
			bank_loan_requests.set(i, null);
	}

	public void setBankToTeacherLoanRequest(long bankId,
			BankLoanRequestEngine blr) {
		for (int i = 0; i < banks.size(); i++) {
			if (banks.get(i).getId() == bankId) {
				bank_loan_requests.set(i, blr);
			}
		}
	}

	public BankLoanRequestEngine getBankToTeacherLoanRequest(long bankId) {
		for (int i = 0; i < banks.size(); i++) {
			if (bankId == banks.get(i).getId()) {
				return bank_loan_requests.get(i);
			}
		}
		return null;
	}

	public void setBankLoanRequest(long playerId, long bankId,
			BankLoanRequestEngine blr) {
		int j; // bank num
		for (j = 0; j < banks.size(); j++) {
			if (bankId == banks.get(j).getId())
				break;
		}
		if (j >= banks.size())
			return; // nothing found

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getId() == playerId) {
				player_loan_requests.get(i).set(j,
						new BankLoanRequestEngine(blr));
				return;
			}
		}
	}

	public BankLoanRequestEngine getBankLoanRequest(long playerId, long bankId) {
		int j; // bank num
		for (j = 0; j < banks.size(); j++) {
			if (bankId == banks.get(j).getId())
				break;
		}
		if (j >= banks.size())
			return null; // nothing found
		if (!hasPlayer(playerId))
			return null; // has no player with such id
		return player_loan_requests.get(getPlayerNum(playerId)).get(j);
	}

	public void setBankDepositRequest(long playerId, long bankId,
			BankDepositRequestEngine bdr) {
		int j; // bank num
		for (j = 0; j < banks.size(); j++) {
			if (bankId == banks.get(j).getId())
				break;
		}
		if (j >= banks.size())
			return; // nothing found

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getId() == playerId) {
				player_deposit_requests.get(i).set(j,
						new BankDepositRequestEngine(bdr));
				return;
			}
		}
	}

	public BankDepositRequestEngine getBankDepositRequest(long playerId,
			long bankId) {
		int j; // bank num
		for (j = 0; j < banks.size(); j++) {
			if (bankId == banks.get(j).getId())
				break;
		}
		if (j >= banks.size())
			return null; // nothing found

		if (!hasPlayer(playerId))
			return null; // no player with such id
		return player_deposit_requests.get(getPlayerNum(playerId)).get(j);
	}

	public int getTurnNum() {
		return turnNum;
	}

	public boolean isTurnEnded() {
		return turnEnded;
	}

	public void setTurnNum(int turnNum) {
		this.turnNum = turnNum;
	}

	public void setTurnEnded(boolean turnEnded) {
		this.turnEnded = turnEnded;
	}

	public ScenarioEngine getScenario() {
		return scenario;
	}

	public void setScenario(IUScenario scenario) {
		this.scenario = new ScenarioEngine(scenario);
		initAllocations();
		initShareHoldings();
	}

	public TeacherEngine getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEngine teacher) {
		this.teacher = teacher;

		initShareHoldingsTeacher();
	}

	public void addToHistory(IUGameEvent event) {
		history.add(event);
	}

	public Collection<StudentEngine> getPlayers() {
		return Collections.unmodifiableCollection(players);
	}

	public StudentEngine getPlayer(int i) {
		return players.get(i);
	}

	public BankEngine getBank(int i) {
		return banks.get(i);
	}

	public ArrayList<Long> getPlayersId() {
		ArrayList<Long> rez = new ArrayList<Long>(players.size());
		for (StudentEngine s : players)
			rez.add(s.getId());
		return rez;
	}

	public Collection<BankEngine> getBanks() {
		return Collections.unmodifiableCollection(banks);
	}

	public void addNewPlayer(StudentEngine player) {
		if (hasPlayer(player.getId())) {
			for (int i = 0; i < player_active.size(); i++) {
				if (players.get(i).getId() == player.getId()) {
					players.set(i, player);
					player_active.set(i, true);
					player_delayed_messages.get(i).clear();
					for (int j = 0; j < banks.size(); j++) {
						player_loan_requests.get(i).set(j, null);
						player_deposit_requests.get(i).set(j, null);
					}
					players.get(i).setCash(scenario.getStartingCashValue());
				}
			}
		} else {
			players.add(player);
			players.get(players.size() - 1).setCash(
					scenario.getStartingCashValue());
			player_active.add(true);
			player_delayed_messages.add(new ArrayList<EventSourcePair>());
			player_deposit_requests
					.add(new ArrayList<BankDepositRequestEngine>());
			player_loan_requests.add(new ArrayList<BankLoanRequestEngine>());
			for (int j = 0; j < banks.size(); j++) {
				player_loan_requests.get(player_loan_requests.size() - 1).add(
						null);
				player_deposit_requests.get(player_deposit_requests.size() - 1)
						.add(null);
			}
		}
		initAllocations();
		initShareHoldings();
	}

	public boolean hasPlayer(long id) {
		for (StudentEngine p : players)
			if (p.getId() == id)
				return true;
		return false;
	}

	public int getPlayerNum(long id) {
		for (int i = 0; i < players.size(); i++)
			if (players.get(i).getId() == id)
				return i;
		return -1;
	}

	public int getBankNum(long id) {
		for (int i = 0; i < banks.size(); i++)
			if (banks.get(i).getId() == id)
				return i;
		return -1;
	}

	public void removePlayer(long id) {
		for (int i = 0; i < player_active.size(); i++) {
			if (players.get(i).getId() == id) {
				players.remove(i);
				player_active.remove(i);
				player_delayed_messages.remove(i);
				player_loan_requests.remove(i);
				player_deposit_requests.remove(i);
				return;
			}
		}
	}

	public boolean isPlayerActive(long id) {
		for (int i = 0; i < player_active.size(); i++) {
			if (players.get(i).getId() == id && player_active.get(i) == true)
				return true;
		}
		return false;
	}

	public void setPlayerActive(long id, boolean isActive) {
		for (int i = 0; i < player_active.size(); i++) {
			if (players.get(i).getId() == id)
				player_active.set(i, isActive);
		}
	}

	public void addDelayedMessage(long id, EventSourcePair message) {
		for (int i = 0; i < player_active.size(); i++) {
			if (players.get(i).getId() == id)
				player_delayed_messages.get(i).add(message);
		}
	}

	public Collection<EventSourcePair> getDelayedMessages(long id) {
		for (int i = 0; i < player_active.size(); i++) {
			if (players.get(i).getId() == id)
				return Collections
						.unmodifiableCollection(player_delayed_messages.get(i));
		}
		return Collections
				.unmodifiableCollection(new ArrayList<EventSourcePair>());
	}

	public void cleanDelayedMessages(long id) {
		for (int i = 0; i < player_active.size(); i++) {
			if (players.get(i).getId() == id)
				player_delayed_messages.get(i).clear();
		}
	}

	public void addNewBank(BankEngine bank) {
		if (hasBank(bank.getId())) {
			for (int i = 0; i < bank_active.size(); i++) {
				if (banks.get(i).getId() == bank.getId()) {
					banks.set(i, bank);
					bank_active.set(i, true);
					bank_delayed_messages.get(i).clear();
					bank_loan_requests.set(i, null);
					// clear bank requests
					for (int ii = 0; ii < players.size(); ii++) {
						player_loan_requests.get(ii).set(i, null);
						player_deposit_requests.get(ii).set(i, null);
					}
				}
			}
		} else {
			banks.add(bank);
			bank_active.add(true);
			bank_delayed_messages.add(new ArrayList<EventSourcePair>());
			bank_loan_requests.add(null);
			for (int i = 0; i < players.size(); i++) {
				player_loan_requests.get(i).add(null);
				player_deposit_requests.get(i).add(null);
			}
		}
	}

	public boolean hasBank(long id) {
		for (BankEngine b : banks)
			if (b.getId() == id)
				return true;
		return false;
	}

	public void removeBank(long id) {
		for (int i = 0; i < bank_active.size(); i++) {
			if (banks.get(i).getId() == id) {
				banks.remove(i);
				bank_active.remove(i);
				bank_delayed_messages.remove(i);
				bank_loan_requests.remove(i);
				for (int j = 0; j < players.size(); j++) {
					player_loan_requests.get(j).remove(i);
					player_deposit_requests.get(j).remove(i);
				}
				return;
			}
		}
	}

	public boolean isBankActive(long id) {
		for (int i = 0; i < bank_active.size(); i++) {
			if (banks.get(i).getId() == id && bank_active.get(i) == true)
				return true;
		}
		return false;
	}

	public void setBankActive(long id, boolean isActive) {
		for (int i = 0; i < bank_active.size(); i++) {
			if (banks.get(i).getId() == id)
				bank_active.set(i, isActive);
		}
	}

	public void addDelayedMessageBank(long id, EventSourcePair message) {
		for (int i = 0; i < bank_active.size(); i++) {
			if (banks.get(i).getId() == id)
				bank_delayed_messages.get(i).add(message);
		}
	}

	public Collection<EventSourcePair> getDelayedMessagesBank(long id) {
		for (int i = 0; i < bank_active.size(); i++) {
			if (banks.get(i).getId() == id)
				return Collections.unmodifiableCollection(bank_delayed_messages
						.get(i));
		}
		return Collections
				.unmodifiableCollection(new ArrayList<EventSourcePair>());
	}

	public void cleanDelayedMessagesBank(long id) {
		for (int i = 0; i < bank_active.size(); i++) {
			if (banks.get(i).getId() == id)
				bank_delayed_messages.get(i).clear();
		}
	}
}

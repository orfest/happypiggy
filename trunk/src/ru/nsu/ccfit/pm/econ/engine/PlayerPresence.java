package ru.nsu.ccfit.pm.econ.engine;

import java.util.ArrayList;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUBank;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUBankStudent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUStudent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUTeacher;
import ru.nsu.ccfit.pm.econ.engine.events.BankPresentEvent;
import ru.nsu.ccfit.pm.econ.engine.events.BankRemoveEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.NewBankEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.NewPlayerEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.PlayerPresentEvent;
import ru.nsu.ccfit.pm.econ.engine.events.PlayerRemoveEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.events.SetTeacherEngineEvent;
import ru.nsu.ccfit.pm.econ.engine.roles.BankEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.StudentEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.TeacherEngine;

/**
 * Internal player presence-related stuff implementation
 * 
 * @author pupatenko
 * 
 */
class PlayerPresence {

	private ArrayList<StudentEngine> playerList;
	private ArrayList<Boolean> playerListPresent;
	private long max;
	private IEngineDispatcher engineDispatcher;
	private TeacherEngine teacher;
	private ArrayList<BankEngine> bankList;
	private ArrayList<Boolean> bankListPresent;

	public PlayerPresence(IEngineDispatcher engineDispatcher) {
		max = 0;
		playerList = new ArrayList<StudentEngine>();
		playerListPresent = new ArrayList<Boolean>();
		teacher = null;
		bankList = new ArrayList<BankEngine>();
		bankListPresent = new ArrayList<Boolean>();
		this.engineDispatcher = engineDispatcher;
	}

	private boolean checkPlayerValid(StudentEngine pl) {
		for (StudentEngine p : playerList) {
			if (p.getPersonDescription().getName().equalsIgnoreCase(
					pl.getPersonDescription().getName()))
				return false;
		}
		return true;

	}

	private boolean checkTeacherValid(TeacherEngine t) {
		return true;
	}

	private boolean checkBankValid(BankEngine bank) {
		for (BankEngine b : bankList) {
			if (bank.getBankName().equalsIgnoreCase(b.getBankName()))
				return false;
		}
		return true;
	}

	private long getUnusedLongId() {
		return ++max;
	}

	long registerPlayer(IUPlayer player) throws IllegalArgumentException {
		if (player instanceof IUTeacher) {
			TeacherEngine t = new TeacherEngine((IUTeacher) player);
			if (!checkTeacherValid(t))
				throw new IllegalArgumentException(
						"Trying to register invalid teacher");
			long id = getUnusedLongId();
			t.setId(id);
			teacher = t;
			engineDispatcher.handleEvent(new EventSourcePair(
					EventSource.Engine, new SetTeacherEngineEvent(teacher)));
			return id;
		} else if (player instanceof IUBankStudent) {
			StudentEngine pl = new StudentEngine((IUStudent) player);
			BankEngine b = new BankEngine((IUBank) player);
			if (!checkPlayerValid(pl))
				throw new IllegalArgumentException(
						"Trying to register invalid player");
			if (!checkBankValid(b))
				throw new IllegalArgumentException(
						"Trying to register invalid bank");
			long id = getUnusedLongId();
			pl.setId(id);
			b.setId(id);
			playerList.add(pl);
			bankList.add(b);
			playerListPresent.add(true);
			bankListPresent.add(true);
			engineDispatcher.handleEvent(new EventSourcePair(
					EventSource.Engine, new NewPlayerEngineEvent(pl)));
			engineDispatcher.handleEvent(new EventSourcePair(
					EventSource.Engine, new NewBankEngineEvent(b)));
			return id;

		} else if (player instanceof IUBank) {
			BankEngine b = new BankEngine((IUBank) player);
			if (!checkBankValid(b))
				throw new IllegalArgumentException(
						"Trying to register invalid bank");
			long id = getUnusedLongId();
			b.setId(id);
			bankList.add(b);
			bankListPresent.add(true);
			engineDispatcher.handleEvent(new EventSourcePair(
					EventSource.Engine, new NewBankEngineEvent(b)));
			return id;
		} else {
			StudentEngine pl = new StudentEngine((IUStudent) player);
			if (!checkPlayerValid(pl))
				throw new IllegalArgumentException(
						"Trying to register invalid player");
			long id = getUnusedLongId();
			pl.setId(id);
			playerList.add(pl);
			playerListPresent.add(true);
			engineDispatcher.handleEvent(new EventSourcePair(
					EventSource.Engine, new NewPlayerEngineEvent(pl)));
			return id;
		}
	}

	void unregisterPlayer(long playerId) {
		// players
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getId() == playerId) {
				playerList.remove(i);
				playerListPresent.remove(i);
				engineDispatcher.handleEvent(new EventSourcePair(
						EventSource.Engine, new PlayerRemoveEngineEvent(
								playerId)));
				break;
			}
		}
		// banks
		for (int i = 0; i < bankList.size(); i++) {
			if (bankList.get(i).getId() == playerId) {
				bankList.remove(i);
				bankListPresent.remove(i);
				engineDispatcher
						.handleEvent(new EventSourcePair(EventSource.Engine,
								new BankRemoveEngineEvent(playerId)));
				break;
			}
		}
	}

	void setPlayerPresence(long playerId, boolean isPresent) {
		// student
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getId() == playerId) {
				playerListPresent.set(i, isPresent);
				engineDispatcher.handleEvent(new EventSourcePair(
						EventSource.Engine, new PlayerPresentEvent(playerList
								.get(i).getId(), isPresent)));
			}
		}
		// bank
		for (int i = 0; i < bankList.size(); i++) {
			if (bankList.get(i).getId() == playerId) {
				bankListPresent.set(i, isPresent);
				engineDispatcher.handleEvent(new EventSourcePair(
						EventSource.Engine, new BankPresentEvent(bankList
								.get(i).getId(), isPresent)));
			}
		}
	}

	boolean isPlayerPresent(long playerId) {
		// teacher
		if (playerId == teacher.getId())
			return true;
		// student
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getId() == playerId)
				return playerListPresent.get(i);
		}
		// bank
		for (int i = 0; i < bankList.size(); i++) {
			if (bankList.get(i).getId() == playerId)
				return bankListPresent.get(i);
		}
		return false;

	}
}

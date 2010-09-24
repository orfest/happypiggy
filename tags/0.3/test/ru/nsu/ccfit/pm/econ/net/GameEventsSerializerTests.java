package ru.nsu.ccfit.pm.econ.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import ru.nsu.ccfit.pm.econ.common.engine.data.BankTransactionType;
import ru.nsu.ccfit.pm.econ.common.engine.data.CompanyMessageType;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUBuyOffer;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUAddCashEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankRequestEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBankTransactionEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUBuyOffersChangeEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUChatMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUCompanyMessageEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUDividendPayoutEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameSnapshotEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUTurnEndEvent.IUPlayerRatingValue;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUStudent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUTeacher;
import ru.nsu.ccfit.pm.econ.controller.scenario.persistence.ScenarioDAO;
import ru.nsu.ccfit.pm.econ.engine.data.BuyOfferEngine;
import ru.nsu.ccfit.pm.econ.engine.data.DepositEngine;
import ru.nsu.ccfit.pm.econ.engine.data.GameTimeEngine;
import ru.nsu.ccfit.pm.econ.engine.data.LoanEngine;
import ru.nsu.ccfit.pm.econ.engine.data.PlayerRatingValue;
import ru.nsu.ccfit.pm.econ.engine.data.ShareHoldingEngine;
import ru.nsu.ccfit.pm.econ.engine.events.ChatMessageEventEngine;
import ru.nsu.ccfit.pm.econ.engine.events.GameEventEngine;
import ru.nsu.ccfit.pm.econ.engine.events.GameSnapshotEventEngine;
import ru.nsu.ccfit.pm.econ.engine.events.TurnEndEventEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.PersonDescriptionEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.PlayerEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.StudentEngine;
import ru.nsu.ccfit.pm.econ.engine.roles.TeacherEngine;
import ru.nsu.ccfit.pm.econ.net.engine.data.BankTransaction;
import ru.nsu.ccfit.pm.econ.net.engine.data.GameTime;
import ru.nsu.ccfit.pm.econ.net.engine.data.TextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.net.engine.events.AddCashEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.BankPercentEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.BankRequestEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.BankTransactionEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.BuyOffersChangeEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.BuyRequestEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.ChatMessageEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.CompanyMessageEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.DividendPayoutEvent;
import ru.nsu.ccfit.pm.econ.net.engine.roles.PersonDescription;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.GameEventProto;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.PersonDescriptionProto;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.PlayerProto;

import com.google.gag.annotation.remark.Facepalm;

public class GameEventsSerializerTests {

	@Test
	public void serializeAddCashEventTest() {
		AddCashEvent e = new AddCashEvent();
		e.setAddedCash(2341523);
		e.setBroadcast(true);
		e.setSenderId(5151515);
		e.setReceiverIds(Arrays.asList((long) 534534, (long) 234526, (long) 539534508));

		GameEventProto proto = GameEventsSerializer.getInstance().serializeGameEvent(e);

		assertEquals(e.getSenderId(), proto.getSenderId());
		assertEquals(e.getAddedCash(), proto.getAddedCash(), 1e-5);
		assertEquals(e.getMessage(), proto.getMessage());
		assertEquals(e.isBroadcast(), proto.getBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), proto.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), proto.getEventTime().getTurnFinished());
		assertEquals(e.getReceiverIds(), proto.getReceiverIdsList());

		IUAddCashEvent ie = (IUAddCashEvent) GameEventsSerializer.getInstance().deserializeProto(proto);

		assertEquals(e.getSenderId(), ie.getSenderId());
		assertEquals(e.getAddedCash(), ie.getAddedCash(), 1e-5);
		assertEquals(e.getMessage(), ie.getMessage());
		assertEquals(e.isBroadcast(), ie.isBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), ie.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), ie.getEventTime().isTurnFinished());
		assertEquals(e.getReceiverIds(), ie.getReceiverIds());
	}

	@Test
	public void serializeBankPercentEventTest() throws Exception {
		BankPercentEvent e = new BankPercentEvent();
		e.setDepositInterestRate(-2341523.423);
		e.setLoanInterestRate(234.324);
		e.setBroadcast(true);
		e.setSenderId(5151515);
		e.setReceiverIds(Arrays.asList(534534L, 234526L, 539534508L));

		GameEventProto proto = GameEventsSerializer.getInstance().serializeGameEvent(e);

		assertEquals(e.getSenderId(), proto.getSenderId());
		assertEquals(e.getDepositInterestRate(), proto.getDepositInterestRate(), 1e-5);
		assertEquals(e.getLoanInterestRate(), proto.getLoanInterestRate(), 1e-5);
		assertEquals(e.isBroadcast(), proto.getBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), proto.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), proto.getEventTime().getTurnFinished());
		assertEquals(e.getReceiverIds(), proto.getReceiverIdsList());

	}

	@Test
	public void serializeBankRequestEventTest() throws Exception {
		BankRequestEvent e = new BankRequestEvent();
		e.setBroadcast(true);
		e.setSenderId(5151515);
		e.setReceiverIds(Arrays.asList(534534L, 234526L, 539534508L));
		e.setInitialValue(4234.2351251251);
		e.setPeriod(123);
		e.setTransactionType(BankTransactionType.LOAN_REPAY);

		GameEventProto proto = GameEventsSerializer.getInstance().serializeGameEvent(e);

		assertEquals(e.getSenderId(), proto.getSenderId());
		assertEquals(e.isBroadcast(), proto.getBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), proto.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), proto.getEventTime().getTurnFinished());
		assertEquals(e.getReceiverIds(), proto.getReceiverIdsList());
		assertEquals(e.getInitialValue(), proto.getInitialValue(), 1e-5);
		assertEquals(e.getPeriod(), proto.getPeriod());
		assertEquals(e.getTransactionType().ordinal(), proto.getTransactionType());

		IUBankRequestEvent ie = (IUBankRequestEvent) GameEventsSerializer.getInstance().deserializeProto(proto);

		assertEquals(e.getSenderId(), ie.getSenderId());
		assertEquals(e.isBroadcast(), ie.isBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), ie.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), ie.getEventTime().isTurnFinished());
		assertEquals(e.getReceiverIds(), ie.getReceiverIds());
		assertEquals(e.getEventTime().getTurnNumber(), ie.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), ie.getEventTime().isTurnFinished());
		assertEquals(e.getReceiverIds(), ie.getReceiverIds());
		assertEquals(e.getInitialValue(), ie.getInitialValue(), 1e-5);
		assertEquals(e.getPeriod(), ie.getPeriod());
		assertEquals(e.getTransactionType().ordinal(), ie.getTransactionType().ordinal());
	}

	@Test
	public void serializeBankTransactionEventTest() throws Exception {
		BankTransactionEvent e = new BankTransactionEvent();
		e.setBroadcast(true);
		e.setSenderId(5151515);
		e.setReceiverIds(Arrays.asList((long) 534534, (long) 234526, (long) 539534508));
		e.setBankTransaction(new BankTransaction());

		GameEventProto proto = GameEventsSerializer.getInstance().serializeGameEvent(e);

		assertEquals(e.getSenderId(), proto.getSenderId());
		assertEquals(e.isBroadcast(), proto.getBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), proto.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), proto.getEventTime().getTurnFinished());
		assertEquals(e.getReceiverIds(), proto.getReceiverIdsList());
		assertEquals(e.getBankTransaction().getBankId(), proto.getBankTransaction().getBankId());

		IUBankTransactionEvent ie = (IUBankTransactionEvent) GameEventsSerializer.getInstance().deserializeProto(proto);

		assertEquals(e.getSenderId(), ie.getSenderId());
		assertEquals(e.isBroadcast(), ie.isBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), ie.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), ie.getEventTime().isTurnFinished());
		assertEquals(e.getReceiverIds(), ie.getReceiverIds());
		assertEquals(e.getTransactionType().ordinal(), ie.getTransactionType().ordinal());
	}

	@Test
	public void serializeBuyOffersChangeEvent() throws Exception {
		BuyOffersChangeEvent e = new BuyOffersChangeEvent();
		e.setBroadcast(true);
		e.setSenderId(5151515);
		e.setReceiverIds(Arrays.asList((long) 534534, (long) 234526, (long) 539534508));
		e.setCompanyId(123L);
		e.setNewShareMarketValue(532523.5235);

		GameEventProto proto = GameEventsSerializer.getInstance().serializeGameEvent(e);

		assertEquals(e.getSenderId(), proto.getSenderId());
		assertEquals(e.isBroadcast(), proto.getBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), proto.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), proto.getEventTime().getTurnFinished());
		assertEquals(e.getReceiverIds(), proto.getReceiverIdsList());
		assertEquals(e.getNewShareMarketValue(), proto.getNewShareMarketValue(), 1e-5);
		assertEquals(e.getCompanyId(), proto.getCompanyId());

		IUBuyOffersChangeEvent ie = (IUBuyOffersChangeEvent) GameEventsSerializer.getInstance().deserializeProto(proto);

		assertEquals(e.getSenderId(), ie.getSenderId());
		assertEquals(e.isBroadcast(), ie.isBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), ie.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), ie.getEventTime().isTurnFinished());
		assertEquals(e.getReceiverIds(), ie.getReceiverIds());
		assertEquals(e.getNewShareMarketValue(), ie.getNewShareMarketValue(), 1e-5);
		assertEquals(e.getCompanyId(), ie.getCompanyId());
	}

	@Test
	public void serializeChatMessageEventTest() throws Exception {
		ChatMessageEvent e = new ChatMessageEvent();
		e.setBroadcast(true);
		e.setSenderId(5151515);
		e.setReceiverIds(Arrays.asList(534534L, 234526L, 539534508L));
		e.setMessage("Hi there sk j2090*(&%(@#&)*(#%&)@*(&*(l;jdsf ");

		GameEventProto proto = GameEventsSerializer.getInstance().serializeGameEvent(e);

		assertEquals(e.getSenderId(), proto.getSenderId());
		assertEquals(e.isBroadcast(), proto.getBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), proto.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), proto.getEventTime().getTurnFinished());
		assertEquals(e.getReceiverIds(), proto.getReceiverIdsList());
		assertEquals(e.getMessage(), proto.getMessage());

		IUChatMessageEvent ie = (IUChatMessageEvent) GameEventsSerializer.getInstance().deserializeProto(proto);

		assertEquals(e.getSenderId(), ie.getSenderId());
		assertEquals(e.isBroadcast(), ie.isBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), ie.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), ie.getEventTime().isTurnFinished());
		assertEquals(e.getReceiverIds(), ie.getReceiverIds());
		assertEquals(e.getMessage(), ie.getMessage());
	}

	@Test
	public void serializeCompanyMessageEventTest() throws Exception {
		CompanyMessageEvent e = new CompanyMessageEvent();
		e.setBroadcast(true);
		e.setSenderId(5151515);
		e.setReceiverIds(Arrays.asList(34534L, 234526L, 539534508L));
		TextOnlyCompanyMessage tm = new TextOnlyCompanyMessage();
		tm.setCompanyId(4125L);
		tm.setId(51035l);
		tm.setMessage(" 53 534 58 90-890-8()%*" + "" + "_90 258 9-" + ""
				+ "890-*()%*() %*4fdsfjkas;kj kfdsj fkla;j kla;ghklasg jio " + "gjio JFfio;regerg f gk" + "");
		GameTime time = new GameTime();
		time.setTime(new Date());
		time.setTurnFinished(true);
		time.setTurnNumber(51235);
		tm.setPublishTime(time);
		tm.setTitle(" GYGY Roga I Kopete  ");
		tm.setType(CompanyMessageType.RUMOR);
		e.setCompanyMessage(tm);

		GameEventProto proto = GameEventsSerializer.getInstance().serializeGameEvent(e);

		assertEquals(e.getSenderId(), proto.getSenderId());
		assertEquals(e.isBroadcast(), proto.getBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), proto.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), proto.getEventTime().getTurnFinished());
		assertEquals(e.getReceiverIds(), proto.getReceiverIdsList());
		assertEquals(e.getCompanyMessage().getId(), proto.getCompanyMessage().getId());
		assertEquals(e.getCompanyMessage().getCompanyId(), proto.getCompanyMessage().getCompanyId());
		assertEquals(e.getCompanyMessage().getMessage(), proto.getCompanyMessage().getMessage());
		assertEquals(e.getCompanyMessage().getTitle(), proto.getCompanyMessage().getTitle());
		assertEquals(e.getCompanyMessage().getPublishTime().getTurnNumber(), proto.getCompanyMessage().getPublishTime()
				.getTurnNumber());

		IUCompanyMessageEvent ie = (IUCompanyMessageEvent) GameEventsSerializer.getInstance().deserializeProto(proto);

		assertEquals(e.getSenderId(), ie.getSenderId());
		assertEquals(e.isBroadcast(), ie.isBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), ie.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), ie.getEventTime().isTurnFinished());
		assertEquals(e.getReceiverIds(), ie.getReceiverIds());
		assertEquals(e.getCompanyMessage().getId(), ie.getCompanyMessage().getId());
		assertEquals(e.getCompanyMessage().getCompanyId(), ie.getCompanyMessage().getCompanyId());
		assertEquals(e.getCompanyMessage().getMessage(), ie.getCompanyMessage().getMessage());
		assertEquals(e.getCompanyMessage().getTitle(), ie.getCompanyMessage().getTitle());
		assertEquals(e.getCompanyMessage().getType().toString(), ie.getCompanyMessage().getType().toString());
		assertEquals(e.getCompanyMessage().getPublishTime().getTurnNumber(), ie.getCompanyMessage().getPublishTime()
				.getTurnNumber());
	}

	@Test
	public void serializeDividendPayoutEventTest() throws Exception {
		DividendPayoutEvent e = new DividendPayoutEvent();
		e.setBroadcast(true);
		e.setSenderId(5151515);
		e.setReceiverIds(Arrays.asList(534534L, 234526L, 539534508L));
		e.setCompanyId(9123L);
		e.setCompanyProfit(4124.1512);
		e.setDividendPayoutRatio(241.4);
		e.setDividendPayoutValue(1521.3);

		GameEventProto proto = GameEventsSerializer.getInstance().serializeGameEvent(e);

		assertEquals(e.getSenderId(), proto.getSenderId());
		assertEquals(e.isBroadcast(), proto.getBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), proto.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), proto.getEventTime().getTurnFinished());
		assertEquals(e.getReceiverIds(), proto.getReceiverIdsList());
		assertEquals(e.getCompanyId(), proto.getCompanyId());
		assertEquals(e.getCompanyProfit(), proto.getCompanyProfit(), 1e-5);
		assertEquals(e.getDividendPayoutRatio(), proto.getDividendPayoutRatio(), 1e-5);
		assertEquals(e.getDividendPayoutValue(), proto.getDividendPayoutValue(), 1e-5);

		IUDividendPayoutEvent ie = (IUDividendPayoutEvent) GameEventsSerializer.getInstance().deserializeProto(proto);

		assertEquals(e.getSenderId(), ie.getSenderId());
		assertEquals(e.isBroadcast(), ie.isBroadcast());
		assertEquals(e.getEventTime().getTurnNumber(), ie.getEventTime().getTurnNumber());
		assertEquals(e.getEventTime().isTurnFinished(), ie.getEventTime().isTurnFinished());
		assertEquals(e.getReceiverIds(), ie.getReceiverIds());
		assertEquals(e.getCompanyId(), ie.getCompanyId());
		assertEquals(e.getCompanyProfit(), ie.getCompanyProfit(), 1e-5);
		assertEquals(e.getDividendPayoutRatio(), ie.getDividendPayoutRatio(), 1e-5);
		assertEquals(e.getDividendPayoutValue(), ie.getDividendPayoutValue(), 1e-5);
	}

	@Test
	public void serializeGameTimeEngine() throws Exception {
		GameTimeEngine gt = new GameTimeEngine(new Date(), 5, true);
		Object obj = GameEventsSerializer.getInstance().serializeToProto(gt);
		assertNotNull(obj);
	}

	@Test
	public void serializeBuyOfferEngine() throws Exception {
		IUBuyOffer bo = new BuyOfferEngine(41L, new ShareHoldingEngine(124, 8591L, 12510L), 6231.5126);
		Object obj = GameEventsSerializer.getInstance().serializeToProto(bo);
		assertNotNull(obj);
	}

	@Test
	public void serializeBuyOfferEvent() throws Exception {
		BuyRequestEvent buyEvent = new BuyRequestEvent();
		buyEvent.setBroadcast(false);
		buyEvent.setBuyerId(17L);
		buyEvent.setEventTime(new GameTimeEngine(new Date(), 13, true));
		buyEvent.setOfferOfInterest(new BuyOfferEngine(41L, new ShareHoldingEngine(124, 8591L, 12510L), 6231.5126));
		buyEvent.setReceiverIds(Arrays.asList(5L, 10L));
		buyEvent.setSenderId(6L);
		GameEventProto proto = GameEventsSerializer.getInstance().serializeGameEvent(buyEvent);
		assertNotNull(proto);
	}

	@Test
	public void serializeStudentTest() throws Exception {
		StudentEngine st = new StudentEngine(4L, new PersonDescriptionEngine("gr", "na"), 4.2, Arrays
				.asList(new LoanEngine(BankTransactionType.LOAN, 2L, 4L, 52.3, 626.3, 35, 25, 16)), Arrays
				.asList(new DepositEngine(BankTransactionType.DEPOSIT_REPAY, 5L, 3L, 14.214, 463.4, 4214, 325, 42.42)));
		PlayerProto plp = (PlayerProto) GameEventsSerializer.getInstance().serializeToProto(st);
		IUStudent ist = (IUStudent) GameEventsSerializer.getInstance().deserializeObject(plp, null);
		assertEquals(st.getId(), ist.getId());
		assertEquals(st.getCash(), ist.getCash(), 1e-5);
	}

	@Test
	public void serializeTeacherTest() throws Exception {
		TeacherEngine st = new TeacherEngine(54L, new PersonDescriptionEngine("gr", "na"), 4.2, Arrays
				.asList(new LoanEngine(BankTransactionType.LOAN, 2L, 4L, 52.3, 626.3, 35, 25, 16)));
		PlayerProto plp = (PlayerProto) GameEventsSerializer.getInstance().serializeToProto(st);
		IUTeacher ist = (IUTeacher) GameEventsSerializer.getInstance().deserializeObject(plp, null);
		assertEquals(st.getId(), ist.getId());
		assertEquals(st.getLoanInterestRate(), ist.getLoanInterestRate(), 1e-5);
	}

	/**
	 * serializeDividendVoteEventTest
	 * 
	 * @throws Exception
	 */
	@Test
	public void serializeDividendVoteEventTest() throws Exception {
	}

	/**
	 * serializeDividendVotingEventTest
	 * 
	 * @throws Exception
	 */
	@Test
	public void serializeDividendVotingEventTest() throws Exception {
	}

	@Test
	public void serializePersonDescriptionTest() throws Exception {
		PersonDescriptionEngine pd = new PersonDescriptionEngine(null, "vasya");
		PersonDescriptionProto pdp = (PersonDescriptionProto)GameEventsSerializer.getInstance().serializeToProto(pd);
		assertNotNull(pdp);
		IUPersonDescription pdAgain = (IUPersonDescription)GameEventsSerializer.getInstance().deserializeObject(pdp, PersonDescription.class);
		assertNotNull(pdAgain);
	}
	
	/**
	 * serializeGameSnapshotEventTest
	 * 
	 * @throws Exception
	 */
	@Test
	@Facepalm
	public void serializeGameSnapshotEventTest() throws Exception {
		GameSnapshotEventEngine event = new GameSnapshotEventEngine(
				new GameTimeEngine(new Date(), 4231, true), 
				Arrays.asList(19L, 12L, 329523523325235L),
				5123523523L, false,
				Arrays.asList(new BuyOfferEngine(2423423L, new ShareHoldingEngine(4234234, 432324L, 114124L), 3423423.41241), new BuyOfferEngine(2423523523423L,
				new ShareHoldingEngine(1244234, 442141235212324L, 151251214124L), 3423442342323.41241)), Arrays.asList(
				new ChatMessageEventEngine(" fdsfjsdklfj;kl sd", new GameEventEngine(new GameTimeEngine(new Date(),
						51235, false), Arrays.asList(412L, 2141L, 125L), 12412L, false)), new ChatMessageEventEngine(
						" fdsfjsdklfj;k3523gsdg sdgasdf sdfl sd", new GameEventEngine(new GameTimeEngine(new Date(),
								51235235, true), Arrays.asList(412L, 2141L, 125L, 525235L), 124132252352L, false))), Arrays
				.asList(new PlayerEngine(5325235L, new PersonDescriptionEngine("zxcx  ", "3523 fgsdf")),
						new PlayerEngine(5323423325235L, new PersonDescriptionEngine(null, "fsdf fsd"))),
				new ScenarioDAO().loadScenario(new File("Scenario.SimpleExample.ssml")), 3523523.5235);
		GameEventProto proto = GameEventsSerializer.getInstance().serializeGameEvent(event);
		assertNotNull(proto);
		IUGameSnapshotEvent eventAgain = (IUGameSnapshotEvent) GameEventsSerializer.getInstance().deserializeProto(
				proto);
		assertNotNull(eventAgain);
	}

	/**
	 * serializeShareAllocationRequestEventTest
	 * 
	 * @throws Exception
	 */
	@Test
	public void serializeShareAllocationRequestEventTest() throws Exception {
	}

	/**
	 * serializeTransactionEventTest
	 * 
	 * @throws Exception
	 */
	@Test
	public void serializeTransactionEventTest() throws Exception {
	}

	/**
	 * serializeTransferSharesEventTest
	 * 
	 * @throws Exception
	 */
	@Test
	public void serializeTransferSharesEventTest() throws Exception {
	}

	/**
	 * serializeTurnEndEventTest
	 * 
	 * @throws Exception
	 */
	@Test
	public void serializeTurnEndEventTest() throws Exception {
		TurnEndEventEngine event = new TurnEndEventEngine(new GameTimeEngine(new Date(), 1, true), Arrays.asList(2L),
				-1L, Arrays.asList(((IUPlayerRatingValue) new PlayerRatingValue(2L, 300.0))), false);
		GameEventProto proto = GameEventsSerializer.getInstance().serializeGameEvent(event);
		assertNotNull(proto);
		IUTurnEndEvent eventAgain = (IUTurnEndEvent) GameEventsSerializer.getInstance().deserializeProto(proto);
		assertNotNull(eventAgain);
	}

	/**
	 * serializeTurnStartEventTest
	 * 
	 * @throws Exception
	 */
	@Test
	public void serializeTurnStartEventTest() throws Exception {
	}

}

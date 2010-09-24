package ru.nsu.ccfit.pm.econ.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.controller.scenario.IUScenarioProperties;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUGameProperties;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUAddCashEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.net.IUServerProperties;
import ru.nsu.ccfit.pm.econ.controller.clientnet.PersonDescription;
import ru.nsu.ccfit.pm.econ.controller.servernet.ServerNetworkController;
import ru.nsu.ccfit.pm.econ.engine.data.BuyOfferEngine;
import ru.nsu.ccfit.pm.econ.engine.data.GamePropertiesEngine;
import ru.nsu.ccfit.pm.econ.engine.data.GameTimeEngine;
import ru.nsu.ccfit.pm.econ.engine.data.ShareHoldingEngine;
import ru.nsu.ccfit.pm.econ.mock.GameEventHandlerHistory;
import ru.nsu.ccfit.pm.econ.mock.controller.clientnet.NetworkEventsHistory;
import ru.nsu.ccfit.pm.econ.mock.controller.servernet.PlayerNetworkPresenceHistory;
import ru.nsu.ccfit.pm.econ.mock.engine.PlayerPresenceAutoIncrementHistory;
import ru.nsu.ccfit.pm.econ.mock.engine.ServerLifecycleNetworkEventsHistory;
import ru.nsu.ccfit.pm.econ.net.engine.events.AddCashEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.BuyRequestEvent;

public class NetworkServerTest {

	private Logger logger = LoggerFactory.getLogger(NetworkServerTest.class);

	private ServerNetworkController controller = new ServerNetworkController();
	private NetworkServer server = new NetworkServer();
	private PlayerNetworkPresenceHistory playerNetworkPresence = new PlayerNetworkPresenceHistory();
	private PlayerPresenceAutoIncrementHistory playerPresence = new PlayerPresenceAutoIncrementHistory();
	private ServerLifecycleNetworkEventsHistory serverLifecycleNetworkEvents = new ServerLifecycleNetworkEventsHistory();
	private IUScenarioProperties scenarioProperties = null;
	private IUGameProperties gameProperties = new GamePropertiesEngine("A game", scenarioProperties);

	private NetworkClient client = new NetworkClient();
	private IUPersonDescription player = new PersonDescription("Vasya", "puorg");
	private NetworkEventsHistory networkEvents = new NetworkEventsHistory();
	private GameEventHandlerHistory gameEventHandler = new GameEventHandlerHistory();

	private AddCashEvent someEvent = new AddCashEvent();
	private BuyRequestEvent buyEvent = new BuyRequestEvent();

	private IUPersonDescription anotherPlayer = new PersonDescription("Petya", "pgr");
	private NetworkClient anotherClient = new NetworkClient();

	private ServerNetworkController anotherController = new ServerNetworkController();
	
	private IUGameProperties anotherGameProperties = new GamePropertiesEngine("ZZ", scenarioProperties);
	private NetworkServer anotherServer = new NetworkServer();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		logger.info("test started");
		
		server.setEngine(gameEventHandler);
		server.setPlayerNetworkPresence(playerNetworkPresence);
		server.setPlayerPresence(playerPresence);
		
		controller.setNetworkServer(server);
		controller.setServerLifecycleNetworkEvents(serverLifecycleNetworkEvents);
		controller.setGameProperties(gameProperties);
		controller.setAutolocateEnabled(true);

		anotherServer.setEngine(gameEventHandler);
		anotherServer.setPlayerNetworkPresence(playerNetworkPresence);
		anotherServer.setPlayerPresence(playerPresence);

		anotherController.setNetworkServer(anotherServer);
		anotherController.setGameProperties(anotherGameProperties);
		anotherController.setServerLifecycleNetworkEvents(serverLifecycleNetworkEvents);
		
		client.setNetworkEvents(networkEvents);
		client.setEventHandler(gameEventHandler);
		anotherClient.setNetworkEvents(networkEvents);
		anotherClient.setEventHandler(gameEventHandler);

		someEvent.setAddedCash(543);
		someEvent.setBroadcast(true);
		someEvent.setEventTime(new GameTimeEngine(new Date(), 5, false));
		someEvent.setMessage("Hi there");
		someEvent.setReceiverIds(Arrays.asList(1L, 2L, 3L, 4L));
		someEvent.setSenderId(543);

		buyEvent.setBroadcast(false);
		buyEvent.setBuyerId(17L);
		buyEvent.setEventTime(new GameTimeEngine(new Date(), 13, true));
		buyEvent.setOfferOfInterest(new BuyOfferEngine(41L, new ShareHoldingEngine(124, 8591L, 12510L), 6231.5126));
		buyEvent.setReceiverIds(Arrays.asList(5L, 10L));
		buyEvent.setSenderId(6L);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		controller.terminateServer();
		anotherController.terminateServer();
	}

	@Test
	public void testStartServer() throws SocketException, InterruptedException {
		controller.setAutolocateEnabled(false);
		controller.startServer();
		Thread.sleep(200);
		assertEquals(1, serverLifecycleNetworkEvents.getStarted());
		assertEquals(0, serverLifecycleNetworkEvents.getFailed());
		assertEquals(0, serverLifecycleNetworkEvents.getTerminate());
	}

	@Test
	public void testStartTwoServersSameAddress() throws SocketException, UnknownHostException, InterruptedException {
		controller.setAutolocateEnabled(false);
		anotherController.setAutolocateEnabled(false);
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		anotherController.setGameProperties(gameProperties);
		anotherController.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		Thread.sleep(200);
		assertEquals(1, serverLifecycleNetworkEvents.getStarted());
		assertEquals(1, serverLifecycleNetworkEvents.getFailed());
		assertEquals(0, serverLifecycleNetworkEvents.getTerminate());
	}

	@Test
	public void testStartServerWithBroadcast() throws SocketException, InterruptedException {
		controller.startServer();
		Thread.sleep(500);
		assertEquals(1, serverLifecycleNetworkEvents.getStarted());
		assertEquals(0, serverLifecycleNetworkEvents.getFailed());
		assertEquals(0, serverLifecycleNetworkEvents.getTerminate());
	}

	@Test
	public void testStartServerTwice() throws SocketException, InterruptedException {
		controller.startServer();
		controller.startServer();
		Thread.sleep(200);
		assertEquals(2, serverLifecycleNetworkEvents.getStarted());
		assertEquals(0, serverLifecycleNetworkEvents.getFailed());;
		assertEquals(0, serverLifecycleNetworkEvents.getTerminate());
	}

	@Test
	public void testTerminateServerTwice() throws SocketException, InterruptedException {
		controller.startServer();
		Thread.sleep(300);
		controller.terminateServer();
		controller.terminateServer();
		controller.terminateServer();
		controller.terminateServer();
		controller.terminateServer();
		controller.terminateServer();
		controller.terminateServer();
		Thread.sleep(300);
		assertEquals(1, serverLifecycleNetworkEvents.getStarted());
		assertEquals(0, serverLifecycleNetworkEvents.getFailed());;
		assertEquals(1, serverLifecycleNetworkEvents.getTerminate());
	}

	@Test
	public void testStartServerWithBroadcastFixedAddress() throws SocketException, UnknownHostException, InterruptedException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		Thread.sleep(300);
		assertEquals(1, serverLifecycleNetworkEvents.getStarted());
		assertEquals(0, serverLifecycleNetworkEvents.getFailed());
		assertEquals(0, serverLifecycleNetworkEvents.getTerminate());
	}

	@Test
	public void testTerminateServer() throws SocketException, UnknownHostException, InterruptedException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		Thread.sleep(400);
		controller.terminateServer();
		Thread.sleep(400);
		assertEquals(1, serverLifecycleNetworkEvents.getStarted());
		assertEquals(0, serverLifecycleNetworkEvents.getFailed());
		assertEquals(1, serverLifecycleNetworkEvents.getTerminate());
	}

	@Test
	public void testServerAutolocated() throws SocketException, UnknownHostException, InterruptedException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		Thread.sleep(400);
		client.startServerLookup();
		Thread.sleep(400); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(1, networkEvents.getServers().size());
		assertEquals(gameProperties.getGameSessionName(), networkEvents.getServers().get(0).getGameSessionName());
	}

	@Test
	public void testTwoServersAutolocated() throws SocketException, UnknownHostException, InterruptedException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		anotherController.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 19814));
		client.startServerLookup();
		Thread.sleep(400); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(2, networkEvents.getServers().size());
	}

	@Test
	public void testConnect() throws SocketException, UnknownHostException, InterruptedException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		client.startServerLookup();
		Thread.sleep(400); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(1, networkEvents.getServers().size());
		assertEquals(gameProperties.getGameSessionName(), networkEvents.getServers().get(0).getGameSessionName());
		IUServerProperties serverProperties = networkEvents.getServers().get(0);
		client.connect(serverProperties.getAddress(), player);
	}

	@Test
	public void testPlayerConnected() throws InterruptedException, SocketException, UnknownHostException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		client.startServerLookup();
		Thread.sleep(800); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(1, networkEvents.getServers().size());
		assertEquals(gameProperties.getGameSessionName(), networkEvents.getServers().get(0).getGameSessionName());
		IUServerProperties serverProperties = networkEvents.getServers().get(0);
		client.connect(serverProperties.getAddress(), player);
		Thread.sleep(1000);
		assertTrue(networkEvents.isConnect());
		assertEquals(1, playerNetworkPresence.getPlayers().size());
		assertEquals(player.getName(), playerNetworkPresence.getPlayers().get(0).getName());
		assertEquals(player.getGroup(), playerNetworkPresence.getPlayers().get(0).getGroup());
		assertEquals(1, playerPresence.getPlayers().size());
		assertEquals(player.getName(), playerPresence.getPlayers().get(0).getUnmodifiablePersonDescription().getName());
		assertEquals(player.getGroup(), playerPresence.getPlayers().get(0).getUnmodifiablePersonDescription().getGroup());
	}

	@Test
	public void testHandleEvent() throws SocketException, UnknownHostException, InterruptedException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		client.startServerLookup();
		Thread.sleep(400); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(1, networkEvents.getServers().size());
		assertEquals(gameProperties.getGameSessionName(), networkEvents.getServers().get(0).getGameSessionName());
		IUServerProperties serverProperties = networkEvents.getServers().get(0);
		client.connect(serverProperties.getAddress(), player);
		Thread.sleep(1000);
		assertTrue(networkEvents.isConnect());
		assertEquals(1, playerNetworkPresence.getPlayers().size());
		assertEquals(player.getName(), playerNetworkPresence.getPlayers().get(0).getName());
		assertEquals(player.getGroup(), playerNetworkPresence.getPlayers().get(0).getGroup());
		assertEquals(1, playerPresence.getPlayers().size());
		assertEquals(player.getName(), playerPresence.getPlayers().get(0).getUnmodifiablePersonDescription().getName());
		assertEquals(player.getGroup(), playerPresence.getPlayers().get(0).getUnmodifiablePersonDescription().getGroup());
		client.handleEvent(someEvent);
		Thread.sleep(1000);
		assertEquals(1, gameEventHandler.getEvents().size());
		assertTrue(((IUAddCashEvent) (gameEventHandler.getEvents().get(0))).getAddedCash() == someEvent.getAddedCash());
		assertTrue(((IUAddCashEvent) (gameEventHandler.getEvents().get(0))).getMessage().equals(someEvent.getMessage()));
	}

	@Test
	public void testHandleBuyEvent() throws SocketException, UnknownHostException, InterruptedException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		client.startServerLookup();
		Thread.sleep(400); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(1, networkEvents.getServers().size());
		assertEquals(gameProperties.getGameSessionName(), networkEvents.getServers().get(0).getGameSessionName());
		IUServerProperties serverProperties = networkEvents.getServers().get(0);
		client.connect(serverProperties.getAddress(), player);
		Thread.sleep(1000);
		assertTrue(networkEvents.isConnect());
		assertEquals(1, playerNetworkPresence.getPlayers().size());
		assertEquals(player.getName(), playerNetworkPresence.getPlayers().get(0).getName());
		assertEquals(player.getGroup(), playerNetworkPresence.getPlayers().get(0).getGroup());
		assertEquals(1, playerPresence.getPlayers().size());
		assertEquals(player.getName(), playerPresence.getPlayers().get(0).getUnmodifiablePersonDescription().getName());
		assertEquals(player.getGroup(), playerPresence.getPlayers().get(0).getUnmodifiablePersonDescription().getGroup());
		client.handleEvent(buyEvent);
		Thread.sleep(1000);
		assertEquals(1, gameEventHandler.getEvents().size());
	}

	@Test
	public void testPlayerConnectedTwice() throws InterruptedException, SocketException, UnknownHostException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		client.startServerLookup();
		Thread.sleep(400); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(1, networkEvents.getServers().size());
		assertEquals(gameProperties.getGameSessionName(), networkEvents.getServers().get(0).getGameSessionName());
		IUServerProperties serverProperties = networkEvents.getServers().get(0);
		client.connect(serverProperties.getAddress(), player);
		client.connect(serverProperties.getAddress(), player);
		client.connect(serverProperties.getAddress(), player);
		client.connect(serverProperties.getAddress(), player);
		client.connect(serverProperties.getAddress(), player);
		Thread.sleep(1000);
		assertTrue(networkEvents.isConnect());
		assertEquals(1, playerNetworkPresence.getPlayers().size());
		assertEquals(player.getName(), playerNetworkPresence.getPlayers().get(0).getName());
		assertEquals(player.getGroup(), playerNetworkPresence.getPlayers().get(0).getGroup());
		assertEquals(1, playerPresence.getPlayers().size());
		assertEquals(player.getName(), playerPresence.getPlayers().get(0).getUnmodifiablePersonDescription().getName());
		assertEquals(player.getGroup(), playerPresence.getPlayers().get(0).getUnmodifiablePersonDescription().getGroup());
	}

	@Test
	public void testTwoDifferentPlayersConnected() throws InterruptedException, SocketException, UnknownHostException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		client.startServerLookup();
		Thread.sleep(400); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(1, networkEvents.getServers().size());
		assertEquals(gameProperties.getGameSessionName(), networkEvents.getServers().get(0).getGameSessionName());
		IUServerProperties serverProperties = networkEvents.getServers().get(0);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), anotherPlayer);
		Thread.sleep(1000);
		assertTrue(networkEvents.isConnect());
		assertEquals(2, playerNetworkPresence.getPlayers().size());
		assertEquals(2, playerPresence.getPlayers().size());
	}

	@Test
	public void testTwoDifferentPlayersSameName() throws InterruptedException, SocketException, UnknownHostException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		client.startServerLookup();
		Thread.sleep(400); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(1, networkEvents.getServers().size());
		assertEquals(gameProperties.getGameSessionName(), networkEvents.getServers().get(0).getGameSessionName());
		IUServerProperties serverProperties = networkEvents.getServers().get(0);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), player);
		Thread.sleep(1000);
		assertTrue(networkEvents.isConnect());
		assertEquals(1, playerNetworkPresence.getPlayers().size());
		assertEquals(1, playerPresence.getPlayers().size());
	}

	@Test
	public void testTwoDifferentPlayersConnectedTwice() throws InterruptedException, SocketException, UnknownHostException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		client.startServerLookup();
		Thread.sleep(400); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(1, networkEvents.getServers().size());
		assertEquals(gameProperties.getGameSessionName(), networkEvents.getServers().get(0).getGameSessionName());
		IUServerProperties serverProperties = networkEvents.getServers().get(0);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), anotherPlayer);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), anotherPlayer);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), anotherPlayer);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), anotherPlayer);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), anotherPlayer);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), anotherPlayer);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), anotherPlayer);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), anotherPlayer);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), anotherPlayer);
		Thread.sleep(1000);
		assertTrue(networkEvents.isConnect());
		assertEquals(2, playerNetworkPresence.getPlayers().size());
		assertEquals(2, playerPresence.getPlayers().size());
	}

	@Test
	public void testHandleEventsFromTwoDifferentPlayers() throws InterruptedException, SocketException, UnknownHostException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		client.startServerLookup();
		Thread.sleep(400); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(1, networkEvents.getServers().size());
		assertEquals(gameProperties.getGameSessionName(), networkEvents.getServers().get(0).getGameSessionName());
		IUServerProperties serverProperties = networkEvents.getServers().get(0);
		client.connect(serverProperties.getAddress(), player);
		anotherClient.connect(serverProperties.getAddress(), anotherPlayer);
		Thread.sleep(1000);
		assertTrue(networkEvents.isConnect());
		assertEquals(2, playerNetworkPresence.getPlayers().size());
		assertEquals(2, playerPresence.getPlayers().size());
		client.handleEvent(someEvent);
		anotherClient.handleEvent(buyEvent);
		Thread.sleep(1000);
		assertEquals(2, gameEventHandler.getEvents().size());
	}


	@Test
	public void testClientHandleEvents() throws InterruptedException, SocketException, UnknownHostException {
		controller.startServer(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9814));
		client.startServerLookup();
		Thread.sleep(400); // 1 sec
		assertTrue(networkEvents.isServerLookupUpdate());
		assertEquals(1, networkEvents.getServers().size());
		assertEquals(gameProperties.getGameSessionName(), networkEvents.getServers().get(0).getGameSessionName());
		IUServerProperties serverProperties = networkEvents.getServers().get(0);
		client.connect(serverProperties.getAddress(), player);
		Thread.sleep(1000);
		assertTrue(networkEvents.isConnect());
		assertEquals(1, playerNetworkPresence.getPlayers().size());
		assertEquals(1, playerPresence.getPlayers().size());
		server.handleEvent(someEvent);
		Thread.sleep(1000);
		assertEquals(1, gameEventHandler.getEvents().size());
	}

}

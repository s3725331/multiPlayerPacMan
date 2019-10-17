package networking.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.rmi.RemoteException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.GameData;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import game.Enumeration.PlayerState;
import game.interfaces.ClientRemoteObjectInterface;
import networking.Client;
import networking.ClientRemoteObject;
import networking.Server;

class ClientInputTests {
	static Server testServer;
	static Client testClient;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		testServer = new Server();
	}

	@BeforeEach
	void setUp() throws Exception {
		testServer.restartServer();
		testClient = new Client(InetAddress.getLocalHost().getHostAddress());
		//Giving client a alive player as gameData
		GameData testData = new GameData();
		testData.addPlayer();
		testData.getPlayer(PlayerNum.PLAYER_ONE).state = PlayerState.ALIVE;
		testClient.setGameData(testData);
	}

	@Test
	void badInput() throws Exception {
		testClient.updateKeyInput(KeyEvent.VK_SPACE);
		assertEquals(testClient.getCurrentKey(),KeyEvent.VK_UNDEFINED);
	}
	
	@Test
	void goodInput() throws Exception {
		for(ClientRemoteObjectInterface clientStub: testServer.getClientStubs()) {
			clientStub.sendGameData(testServer.getGameData());
		}
		testClient.updateKeyInput(KeyEvent.VK_UP);
		assertEquals(testClient.getCurrentKey(),KeyEvent.VK_UP);
		assertEquals(testServer.getGameData().getPlayer(PlayerNum.PLAYER_ONE).getBufferDirection(),
				Direction.UP);
		
	}

}

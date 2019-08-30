package networking.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.net.InetAddress;

import org.junit.*;
import org.junit.Test;

import game.GameState;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import gui.SimpleOutPut;
import networking.Client;
import networking.Server;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

class ClientTests {
	Server testServer;
	PlayerNum clientNum;
	Client testClient;
	
	@BeforeClass
	public void setUpClass() throws Exception {
	}
	
	
	@Before
	public void setUp() throws Exception {
		testServer = new Server();
		testClient = new Client(InetAddress.getLocalHost().getHostAddress());
	}
	
	@After
	public void tearDown() throws Exception{
		//testClient = null;
	}
	
	@AfterClass
	public void tearDownClass() throws Exception {
		testServer.terminateSever();
	}

	@Test
	public void badKeyInput() {
		//Client given invalid key, should not call to server
		testClient.updateKeyInput(KeyEvent.VK_SPACE);
		
		//tests that clients direction has not been set on server
		Direction serverDirection = testServer.getGameState().getPlayer(clientNum).getDirection();
		assertEquals(serverDirection,Direction.NO_DIRECTION);
	}
	
	//TODO this method, i have no idea
	/*@Test
	public void sameKeyInput() {
		testClient.updateKeyInput(KeyEvent.VK_SPACE);
		
		Direction clientDirection = testServer.getGameState().getPlayer(clientNum).getDirection();
		assertEquals(clientDirection,Direction.NO_DIRECTION);
	}*/
	
	@Test
	public void newKeyInput() {
		//Client given up key
		testClient.updateKeyInput(KeyEvent.VK_UP);
		//tests that server has been updated
		Direction clientDirection = testServer.getGameState().getPlayer(clientNum).getDirection();
		assertEquals(clientDirection,Direction.UP);
		
		//Client given new key down
		testClient.updateKeyInput(KeyEvent.VK_LEFT);
		//tests that server has been updated again
		clientDirection = testServer.getGameState().getPlayer(clientNum).getDirection();
		assertEquals(clientDirection,Direction.LEFT);
	}
	
	@Test
	public void getServersGameState() throws Exception {
		//Add a second client to server and request new game state
		testServer.addNewClient();
		testClient.requestGameState();
		
		//assert that new game state with second player has been returned
		assertEquals(testClient.getGameState().getNumPlayers(),2);
		
		
	}

}

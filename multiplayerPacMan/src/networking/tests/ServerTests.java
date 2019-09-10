package networking.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import networking.Client;
import networking.Server;
import networking.exceptions.FullServerException;
import networking.exceptions.NoRegistryException;

class ServerTests {
	Server testServer;
	
	@Before
	public void setUp() throws Exception{
		testServer = new Server();
	}
	
	@Test
	public void addClient() {
		//Adding a client and testing that has correct id
		assertEquals(testServer.addNewClient(),PlayerNum.PLAYER_ONE);
		System.out.println(testServer.addNewClient());
		//testing that client has been added to gamestate
		assertEquals(testServer.getGameState().getNumPlayers(),1);	
	}
	
	@Test
	public void fullServer(){
		//Filling server
		testServer.addNewClient();
		testServer.addNewClient();
		testServer.addNewClient();
		testServer.addNewClient();
		
		//testing that adding to a full server returns invalid player id
		assertEquals(testServer.addNewClient(), PlayerNum.INVALID_PLAYER);
	}
	
	@Test
	public void invalidPlayerNum() {
		testServer.addNewClient();
		//Updating direction to be ignored
		testServer.updateClientInput(Direction.UP, PlayerNum.PLAYER_TWO);
		
		//Asserting that exsiting players have not been incorrectly updated
		assertEquals(testServer.getGameState().getPlayer(PlayerNum.PLAYER_ONE).getDirection(), Direction.NO_DIRECTION);
	}
	
	@Test
	public void pudatingPlayerDirection() {
		testServer.addNewClient();
		//Updating direction
		testServer.updateClientInput(Direction.UP, PlayerNum.PLAYER_ONE);
		
		//Asserting that player direction has been updated
		assertEquals(testServer.getGameState().getPlayer(PlayerNum.PLAYER_ONE).getDirection(), Direction.UP);
	}

}

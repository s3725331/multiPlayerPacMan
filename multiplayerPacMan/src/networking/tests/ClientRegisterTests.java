package networking.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import game.Enumeration.PlayerNum;
import networking.Client;
import networking.Server;
import networking.exceptions.FullServerException;
import networking.exceptions.NoRegistryException;

class ClientRegisterTests {
	Server testServer;
	Client testClient;
	
	@Before 
	public void setUp() throws Exception {
		testServer = new Server();
	}

	@Test (expected=NoRegistryException.class)
	public void badHostName() throws FullServerException, NoRegistryException, RemoteException{
		//create new client with a bad ip
		testClient = new Client("Bad IP") ;
	}
	
	@Test (expected=FullServerException.class)
	public void fullServer() throws RemoteException, FullServerException, UnknownHostException, NoRegistryException{
		//Filling server
		testServer.addNewClient();
		testServer.addNewClient();
		testServer.addNewClient();
		testServer.addNewClient();
		
		//creating client (which will try to add to server)
		testClient = new Client(InetAddress.getLocalHost().getHostAddress());
		
	}
	
	@Test 
	public void successfulConnect() throws RemoteException, FullServerException, NoRegistryException, UnknownHostException{
		//create new client with a correct ip
		testClient = new Client(InetAddress.getLocalHost().getHostAddress());
		
		//Testing that server has correctly added client and assigned a player num
		assertEquals(testClient.getPlayerNum(),PlayerNum.PLAYER_ONE);
	}
	

}

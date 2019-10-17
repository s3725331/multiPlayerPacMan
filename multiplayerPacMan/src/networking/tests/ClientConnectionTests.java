package networking.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Enumeration.PlayerNum;
import networking.Client;
import networking.ClientRemoteObject;
import networking.Server;
import networking.exceptions.FullServerException;
import networking.exceptions.NoRegistryException;

class ClientConnectionTests {
	static Server testServer;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		testServer = new Server();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		testServer.restartServer();
	}

	@Test
	void successfulConnect() throws Exception {
		//connecting client to server
		Client testClient = new Client(InetAddress.getLocalHost().getHostAddress());
		//Testing client is connected to server
		assertEquals(testClient.getPlayerNum(),PlayerNum.PLAYER_ONE);	
	}
	
	@Test
	public void badHostName() throws Exception{
		//create new client with a bad ip
		assertThrows(NoRegistryException.class,
				()->{new Client("Bad IP");}
			) ;
	}
	
	@Test
	public void fullServer() throws RemoteException {
		//Filling server
		testServer.addNewClient(new ClientRemoteObject(null));
		testServer.addNewClient(new ClientRemoteObject(null));
		testServer.addNewClient(new ClientRemoteObject(null));
		testServer.addNewClient(new ClientRemoteObject(null));
				
		//creating client (which will try to add to server)
		assertThrows(FullServerException.class,
				()->{new Client(InetAddress.getLocalHost().getHostAddress());
			}) ;
	}

}

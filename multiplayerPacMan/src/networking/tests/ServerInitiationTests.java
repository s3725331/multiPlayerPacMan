package networking.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.interfaces.ServerRemoteObjectInterface;
import networking.Server;
import networking.exceptions.RegistryExistsException;

class ServerInitiationTests {
	Server testServer;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Throwable {
		testServer.terminateSever();
	}

	@Test
	void goodServer() throws Exception{
		testServer = new Server();
		
		//Getting stub form server registry
		Context namingContext = new InitialContext();
		String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/remote_obj";
		ServerRemoteObjectInterface stub = (ServerRemoteObjectInterface) namingContext.lookup(url);
		assert(true);
	}
	
	@Test
	void secondServer() throws Exception {
		testServer = new Server();
		assertThrows(RegistryExistsException.class, 
				()->{new Server();}
			);
	}
	
	

}

package networking.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;

import org.junit.jupiter.api.Test;

import networking.Server;

class ServerRegisterTests {

	@Test
	public void sreateServer() throws Exception {
		Server testServer = new Server();
		
		//Testing registry is mechine's localhost address
		assertEquals(testServer.getHostAddress(),InetAddress.getLocalHost().getHostAddress());
		
	}

}

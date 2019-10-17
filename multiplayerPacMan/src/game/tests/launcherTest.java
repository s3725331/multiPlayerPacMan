

package game.tests;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game.launch;

class launcherTest {

	//@Test
	//void goodIP() {
		//assertNotEquals(launch.validateIPaddress("192.168.1.1"),null);
	//}

	@Test
	void badIP() {
		assertEquals(launch.validateIPaddress("a.a.a.a"),null);	
	}
	
	@Test
	void serverOption() {
		assertEquals(launch.getServerOption("aaaa"),null);	
	}
	
	@Test
	void serverOptionJoin() {
		assertEquals(launch.getServerOption("join"),"join");	
	}

	@Test
	void serverOptionHost() {
		assertNotEquals(launch.getServerOption("Host"),null);	
	}





}

package game;

import gui.SimpleOutPut;
import networking.Client;
import networking.Server;

public class testLauncher {

	public static void main(String[] args) {
		try {
			System.out.print("Making Server:");
			Server server = new Server();
			
			System.out.print("Making Client:");
			Client client = new Client(server.getHostAddress());
			
			System.out.print("Making GUI:");
			client.registerOutput(new SimpleOutPut(client));
			
			Client client2 = new Client(server.getHostAddress());
			client2.registerOutput(new SimpleOutPut(client2));
			
			server.startGame();
			
		}catch(Exception e) {
			System.err.println(e);
		}
		
		
		
		
	}

}

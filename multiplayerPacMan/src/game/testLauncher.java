package game;

import gui.SimpleOutPut;
import networking.Client;
import networking.Server;

public class testLauncher {

	public static void main(String[] args) {
		try {
			
			Server server = new Server();
			Client client = new Client(server.getHostAddress());
			
			server.registerOutput(new SimpleOutPut(client, "Server"));
			
			/*Client client = new Client(server.getHostAddress());
			SimpleOutPut output = new SimpleOutPut(client);
			client.registerOutput(output);
			*/
			
			client.registerOutput(new SimpleOutPut(client,client.getPlayerNum().toString()));
			
			Client client2 = new Client(server.getHostAddress());
			client2.registerOutput(new SimpleOutPut(client2,client2.getPlayerNum().toString()));
			
			server.startGame();
			
		}catch(Exception e) {
			System.err.println(e);
		}
		
		
		
		
	}

}

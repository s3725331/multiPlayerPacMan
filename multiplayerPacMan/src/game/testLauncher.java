package game;

import java.util.Scanner;

import gui.SimpleOutPut;
import gui.GameFrame;
import networking.Client;
import networking.Server;

public class testLauncher {

	public static void main(String[] args) {
		try {
			String serverIp;
			Server server = new Server();
			//server.registerOutput(new SimpleOutPut(null, "Server"));
			serverIp = server.getHostAddress();
			
			
			Client client = new Client(server.getHostAddress());
			client.registerOutput(new GameFrame(client,client.getPlayerNum().toString()));
			
			Client client2 = new Client(server.getHostAddress());
			client2.registerOutput(new GameFrame(client2,client2.getPlayerNum().toString()));
			
			Client client3 = new Client(server.getHostAddress());
			client3.registerOutput(new GameFrame(client3,client3.getPlayerNum().toString()));
			
			server.startCountDown();	
		}catch(Exception e) {
			System.err.println(e);
		}
		
		
		
		
	}

}

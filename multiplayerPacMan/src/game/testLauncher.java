package game;

import java.util.Scanner;

import gui.SimpleOutPut;
import gui.TemplateFrame;
import networking.Client;
import networking.Server;

public class testLauncher {

	public static void main(String[] args) {
		try {
			String serverIp;
			Server server = new Server();
			server.registerOutput(new SimpleOutPut(null, "Server"));
			serverIp = server.getHostAddress();
			
			/*//Test Launch
			Thread serverThread = new Thread();
			serverThread.{
				@Override
				public void run() {
					
					serverIp = server.getHostAddress();
				}
			}.start();
			
			new Thread() {
				@Override
				public void run() {
					
				}
			}.start();
			
			*/
			
			
			/*Client client = new Client(server.getHostAddress());
			SimpleOutPut output = new SimpleOutPut(client);
			client.registerOutput(output);
			*/
			Client client = new Client(serverIp);
			client.registerOutput(new TemplateFrame(client,client.getPlayerNum().toString()));
			
			Client client2 = new Client(server.getHostAddress());
			client2.registerOutput(new TemplateFrame(client2,client2.getPlayerNum().toString()));
			
			Client client3 = new Client(server.getHostAddress());
			client3.registerOutput(new TemplateFrame(client3,client3.getPlayerNum().toString()));
			/*Client client4 = new Client(server.getHostAddress());
			client4.registerOutput(new TemplateFrame(client4,client4.getPlayerNum().toString()));
			*/
			
			//Client client2 = new Client(server.getHostAddress());
			//client2.registerOutput(new SimpleOutPut(client2,client2.getPlayerNum().toString()));
			//Thread.sleep(5000);
			
			server.startCountDown();	
		}catch(Exception e) {
			System.err.println(e);
		}
		
		
		
		
	}

}

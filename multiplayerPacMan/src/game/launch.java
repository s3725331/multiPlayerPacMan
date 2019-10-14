package game;
import java.util.Scanner;
import java.util.regex.*;

import gui.SimpleOutPut;
import gui.TemplateFrame;
import networking.Client;
import networking.Server;

public class launch {

	public static void main(String[] args) {

		Scanner player = new Scanner(System.in);
		Scanner address = new Scanner(System.in);
		
		System.out.println("Welcome to the Multiplayer Pacman Game");
		System.out.println("Before we get started. Could you please tell us your name:");
		String userName = player.nextLine();

		String serverOption = null;

		System.out.println("Welcome " + userName + ". Would you like to Host or Join a Server?");
		while (serverOption == null) {
			serverOption = getServerOption(player.nextLine());
		}
		
		String ipAddress = null;
		
		while (serverOption == "join" && ipAddress == null) {
			System.out.println("Please input the host's IP address");
			ipAddress = validateIPaddress(address.nextLine());
		}
		
		if (serverOption == "host") {
			String serverIp;
			Server server;
			try {
				server = new Server();
				serverIp = server.getHostAddress();
				server.registerOutput(new SimpleOutPut(null, serverIp));
				Client client = new Client(serverIp);
				client.registerOutput(new TemplateFrame(client,client.getPlayerNum().toString()));
				System.out.println("The host address is " + serverIp);
				
				boolean start = false;
				while(!start) {
					System.out.println("Press any key to start:");
					player.nextLine();
					if(server.getGameData().getNumPlayers() > 1) {
						start = true;
						server.startCountDown();
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}	
		}
			
			
		
		if (serverOption == "join") {
			try {
				Client client = new Client(ipAddress);
				client.registerOutput(new TemplateFrame(client,client.getPlayerNum().toString()));				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
			
	}

	
	
	static String getServerOption(String input) {
		if (input.toLowerCase().equals("join")) {
			return "join";
		} else if (input.toLowerCase().equals("host")) {
			return "host";
		} else {
			System.out.println("Please try again. Would you like to Join or Host a server?");
			return null;
		}

	}

	static String validateIPaddress(String input) {
		String regex = "\\b((25[0–5]|2[0–4]\\d|[01]?\\d\\d?)(\\.)){3}(25[0–5]|2[0–4]\\d|[01]?\\d\\d?)\\b";
		if (Pattern.matches(regex, input)) {
			System.out.println("True");
			return input;
		}
		else {
			return null;
		}
		

	}

}



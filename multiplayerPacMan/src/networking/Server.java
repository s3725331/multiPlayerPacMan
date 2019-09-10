package networking;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Collection;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.SwingUtilities;

import game.GameEngine;
import game.GameData;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import game.interfaces.GameOutput;

public class Server{
	private final int TICK_RATE = 500;
	private final int FRAME_RATE = 30;
	GameData gameData;
	String registryAddress;
	
	boolean gameStart;
	Timer serverSideGameUpdate;

	Collection<GameOutput> outputs = new HashSet<GameOutput>(); 
	
	
	public Server() throws RemoteException, NamingException, UnknownHostException{
		//creating client stub
		RemoteObject stub = new RemoteObject(this);
		
		//Creating registry on local host
		LocateRegistry.createRegistry(1099);
		
		//binding remoteObject on registry
		Context namingContext = new InitialContext();
		namingContext.bind("rmi:remote_obj",stub);
		
		registryAddress = InetAddress.getLocalHost().getHostAddress();
			
		System.out.println("Waiting for clients...");
		System.out.println("Localhost Address: " + registryAddress);
		
		//
		gameStart = false;
		gameData = new GameData();
		
	}
	
	public PlayerNum addNewClient() {
		return gameData.addPlayer();
	}
	
	public void updateClientInput(Direction direction,PlayerNum playerNum) {
		gameData.getPlayer(playerNum).setDirection(direction);
	}
	
	public void startGame() {
		gameStart = true;
		//defining clientSideGameUpdater
		serverSideGameUpdate = new Timer();
		serverSideGameUpdate.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				GameEngine.updateGame(gameData);
			}
		}, 0, TICK_RATE);
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				//updating each output
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						//Update each gui
						for(GameOutput output:outputs)
							output.updateGame(gameData);
					}
				});
			}
			
		}, 0, 1000/FRAME_RATE);
	}
	
	public GameData getGameData() {
		return gameData;
	}
	
	public void registerOutput(GameOutput output) {
		outputs.add(output);
	}
	
	public String getHostAddress() {
		return registryAddress;
	}
	
	public boolean getGameStart() {
		return gameStart;
	}
	
	public void terminateSever() {
		System.exit(0);
	}
}

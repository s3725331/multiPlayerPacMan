package networking;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import game.GameEngine;
import game.GameState;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;

public class Server{
	private final int TICK_RATE = 333;
	GameState gameState;
	String registryAddress;
	
	boolean gameStart;
	Timer serverSideGameUpdate;
	
	
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
		gameState = new GameState();
		
	}
	
	public PlayerNum addNewClient() {
		return gameState.addPlayer();
	}
	
	public void updateClientInput(Direction direction,PlayerNum playerNum) {
		gameState.getPlayer(playerNum).setDirection(direction);
	}
	
	public void startGame() {
		gameStart = true;
		//defining clientSideGameUpdater
		serverSideGameUpdate = new Timer();
		serverSideGameUpdate.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				GameEngine.updateGame(gameState);
			}
		}, 0, TICK_RATE);
	}
	
	public GameState getGameState() {
		return gameState;
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

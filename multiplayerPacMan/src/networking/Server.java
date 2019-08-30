package networking;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Timer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import game.GameState;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;

public class Server {
	GameState gameState;
	String registryAddress;
	
	boolean gameStart;
	Timer severSideGameUpdate;
	
	
	public Server() throws RemoteException, NamingException, UnknownHostException{
		
		//creating client stub
		RemoteObject stub = new RemoteObject(this);
		
		//Creating registry on local host
		LocateRegistry.createRegistry(1099);
		
		//binding remoteObject on registry
		Context namingContext = new InitialContext();
		namingContext.bind("rmi:remote_obj",stub);
		
		
		registryAddress = InetAddress.getLocalHost().getHostAddress();
			
		/*System.out.println("Waiting for clients...");
		System.out.println("Localhost Address: " + InetAddress.getLocalHost().getHostAddress());
		*/
		
	}
	
	public PlayerNum addNewClient() {
		if (gameState.getNumPlayers() < 4) {
			return gameState.addPlayer();
		}
		return PlayerNum.INVALID_PLAYER;
	}
	
	public void updateClientInput(Direction direction,PlayerNum playerNum) {
		gameState.getPlayer(playerNum).setDirection(direction);
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public String getHostAddress() {
		return registryAddress;
	}
	
	public void terminateSever() {
		System.exit(0);
	}
}

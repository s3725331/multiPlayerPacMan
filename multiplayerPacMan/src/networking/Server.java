package networking;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
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
import game.Enumeration.ServerState;
import game.interfaces.ClientRemoteObjectInterface;
import game.interfaces.GameOutput;

import networking.exceptions.*;

public class Server{
	private final int TICK_RATE = 333;
	private final int FRAME_RATE = 30;
	private GameData gameData;
	private String registryAddress;
	private Registry registry;
	
	private ServerState state;
	
	private Timer serverSideGameUpdate;

	private Collection<GameOutput> outputs = new HashSet<GameOutput>(); 
	
	private Collection<ClientRemoteObjectInterface> clientStubs = new HashSet<ClientRemoteObjectInterface>();
	
	
	
	public Server() throws Exception{
		try {
			//creating client stub
			ServerRemoteObject skeleton = new ServerRemoteObject(this);
			
			//Creating registry on local host
			registry = LocateRegistry.createRegistry(1099);
			
			//binding remoteObject on registry
			Context namingContext = new InitialContext();
			namingContext.bind("rmi:remote_obj",skeleton);
			
			registryAddress = InetAddress.getLocalHost().getHostAddress();
		} catch(ExportException e) {
			throw new RegistryExistsException("Registry already exists, likey that a host server is already running.");
		}
			
		System.out.println("Waiting for clients...");
		System.out.println("Localhost Address: " + registryAddress);
		
		restartServer();
		
	}
	
	public void restartServer() {
		//
		state = ServerState.WAITING;
		gameData = new GameData();
		
		clientStubs.clear();
	}
	
	public PlayerNum addNewClient(ClientRemoteObjectInterface clientStub) {
		//Adding new player to gamedata, adding client stub if player is added to gamedata
		PlayerNum newPlayerNum = gameData.addPlayer();
		if (newPlayerNum != PlayerNum.INVALID_PLAYER)
			clientStubs.add(clientStub);
		return newPlayerNum;
	}
	
	public void updateClientInput(Direction direction,PlayerNum playerNum) {
		gameData.getPlayer(playerNum).setBufferDirection(direction);
	}
	
	public void startGame() {
		state = ServerState.PLAYING;
		//Setting state for all clients
		for(ClientRemoteObjectInterface clientStub:clientStubs) {
			try {
				clientStub.startGame(TICK_RATE,System.currentTimeMillis()+5000);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		
		//defining clientSideGameUpdater
		serverSideGameUpdate = new Timer();
		serverSideGameUpdate.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				//Updating game
				GameEngine.updateGame(gameData);
				//Sending gameData to each client
				for(ClientRemoteObjectInterface clientStub:clientStubs) {
					try {
						clientStub.sendGameData(gameData);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}, 0, TICK_RATE);
		
		
		//Updating gui for server
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
	
	public ServerState getServerStart() {
		return state;
	}
	
	public Collection<ClientRemoteObjectInterface> getClientStubs() {
		return clientStubs;
	}

	public void terminateSever() throws Throwable {
		UnicastRemoteObject.unexportObject(registry,true);
	}
}

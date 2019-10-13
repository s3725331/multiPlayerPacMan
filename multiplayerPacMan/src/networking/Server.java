package networking;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import game.GameEngine;
import game.PlayerData;
import game.GameData;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import game.Enumeration.PlayerState;
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
	
	private Map<PlayerNum,ClientRemoteObjectInterface> clientStubs = new HashMap<PlayerNum,ClientRemoteObjectInterface>();
	
	
	
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
	
	public void restartServer() {
		//
		state = ServerState.WAITING;
		gameData = new GameData();
		
		clientStubs.clear();
	}
	
	public void newGame() {
		//Reseting vars
		GameData newGame = new GameData();
		Map<PlayerNum,ClientRemoteObjectInterface> newStubs = new HashMap<PlayerNum,ClientRemoteObjectInterface>();
		
		for(PlayerNum playerNum: clientStubs.keySet()) {
			ClientRemoteObjectInterface currentStub = clientStubs.get(playerNum);
			//Adding player to new gameData
			PlayerNum newPlayerNum = newGame.addPlayer();
			//Copying client Stub to new stubs w/ new playerNum
			newStubs.put(newPlayerNum, currentStub);
			
			if(playerNum!=newPlayerNum) {
				try {
					currentStub.setPlayerNumber(newPlayerNum);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					System.out.println("119");
					System.out.println(e);
				}
			}
		}
		
		gameData = newGame;
		clientStubs = newStubs;
		
		startCountDown();
	}
	
	public PlayerNum addNewClient(ClientRemoteObjectInterface clientStub) {
		PlayerNum newPlayerNum = PlayerNum.INVALID_PLAYER;
		if(state == ServerState.WAITING) {
			//Adding new player to gamedata, adding client stub if player is added to gamedata
			newPlayerNum = gameData.addPlayer();
			
			if (newPlayerNum != PlayerNum.INVALID_PLAYER) {
				clientStubs.put(newPlayerNum,clientStub);
				
				//sending gamedata to each client
				for(ClientRemoteObjectInterface clients:clientStubs.values()) {
					try {
						clients.sendGameData(gameData);
					} catch (Exception e) {
						System.out.println("93");
						System.out.println(e);
					}
				}
			}
			
		}
		
		return newPlayerNum;
	}
	
	public void dropPlayer(PlayerNum playerNum) {
		clientStubs.remove(playerNum);
		gameData.removePlayer(playerNum);
	}
	
	public void updateClientInput(Direction direction,PlayerNum playerNum) {
		gameData.getPlayer(playerNum).setBufferDirection(direction);
	}
	
	public void startCountDown() {
		long startTime = System.currentTimeMillis()+5000;
		//Setting state for all clients
		for(ClientRemoteObjectInterface clientStub:clientStubs.values()) {
			try {
				//Send final game data and notify clients of start time
				clientStub.startGame(TICK_RATE,startTime);
				
				clientStub.sendGameData(gameData);
					
			} catch (Exception e) {
				System.out.println("117");
				System.out.println(e);
			}
		}
		
		//Set start game timer
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				//Starting game
				state = ServerState.PLAYING;
				System.out.println("Start Server");
				for(PlayerData player:gameData.getPlayers()) {
					player.state = PlayerState.ALIVE;
				}
				
				startGame();
			}
		},startTime - System.currentTimeMillis());
	}
	
	public void startGame() {
		//defining clientSideGameUpdater
		serverSideGameUpdate = new Timer();
		serverSideGameUpdate.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				//Updating game
				GameEngine.updateGame(gameData);
				
				//TODO test if only one player left alive...
				
				//Sending gameData to each client
				for(ClientRemoteObjectInterface clientStub:clientStubs.values()) {
					try {
						clientStub.sendGameData(gameData);
					} catch (Exception e) {
						System.out.println("208");
						System.out.println(e);
					}
				}
				
				//Testing if a player has won (only one alive)
				int numPlayersAlive = 0;
				PlayerNum winner = PlayerNum.INVALID_PLAYER;
				for(PlayerData player:gameData.getPlayers()) {
					if(player.state == PlayerState.ALIVE) {
						numPlayersAlive++;
						winner = player.getPlayerNum();
					}
				}
				
				if(numPlayersAlive == 1) {
					state = ServerState.END_LOBY;
					serverSideGameUpdate.cancel();
					//Updating player states
					for(PlayerData player: gameData.getPlayers())
						player.state = PlayerState.END_LOBY;
					//informing clients of end game
					for(ClientRemoteObjectInterface clientStub: clientStubs.values()) {
						try {
							clientStub.sendGameData(gameData);
							clientStub.endGame(winner);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if (JOptionPane.showConfirmDialog(null, 
				            "Start new game?", "New Game?", 
				            JOptionPane.YES_NO_OPTION,
				            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				           	newGame();
				        }
				}
			}
		}, 0, TICK_RATE);
		
				
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
		return clientStubs.values();
	}

	public void terminateSever() throws Throwable {
		UnicastRemoteObject.unexportObject(registry,true);
	}
}

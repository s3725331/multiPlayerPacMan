package networking;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
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
import game.interfaces.ClientRemoteObjectInterface;
import game.interfaces.GameOutput;
import game.interfaces.ServerRemoteObjectInterface;
import networking.exceptions.FullServerException;
import networking.exceptions.NoRegistryException;

public class Client {
	private int TICK_RATE;
	private final int FRAME_RATE = 30;
	
	
	private ServerRemoteObjectInterface stub;
	private PlayerNum playerNum;
	
	private GameData gameData;
	private int gameTick;
	private int lastServerTick;
	private int currentKeyCode;
	
	private Timer updater;
	
	private Collection<GameOutput> outputs = new HashSet<GameOutput>(); 
	
	public Client (String hostName) throws NoRegistryException, FullServerException, RemoteException{
		connectToServer(hostName);
		
		gameTick = 0;
		currentKeyCode = KeyEvent.VK_UNDEFINED;
		
		//TODO instantiating gameData (will depend on how loby is set up)
		
	}
	
	private void connectToServer(String hostAddress) throws NoRegistryException, FullServerException, RemoteException {
		try {
			//Getting stub form server registry
			Context namingContext = new InitialContext();
			String url = "rmi://" + hostAddress + "/remote_obj";
			stub = (ServerRemoteObjectInterface) namingContext.lookup(url);
		} catch (NamingException e) {
			throw new NoRegistryException("Naming Exception: " + e.getMessage());
		}
		System.out.println(stub);
		//call to server and receive player number
		playerNum = stub.connectToServer(new ClientRemoteObject(this));
		System.out.println("PlayerNum: " + playerNum);
		if (playerNum == PlayerNum.INVALID_PLAYER) {
			throw new FullServerException("Server refused to add player\n(Likely that server is full)");
		}
	}
	
	
	public void registerOutput(GameOutput output) {
		outputs.add(output);
	}
	
	
	public void updateKeyInput(int keyCode) throws RemoteException{
		//Checking that keyinput is a valid direction
		if (keyCode == KeyEvent.VK_UP || 
				keyCode == KeyEvent.VK_DOWN || 
				keyCode == KeyEvent.VK_LEFT || 
				keyCode == KeyEvent.VK_RIGHT) {
			//Checking that keyinput is a different input
			if(keyCode != currentKeyCode) {
				currentKeyCode = keyCode;
				Direction newDirection = Direction.keyCode2Direction(keyCode);
				stub.updateInput(newDirection, playerNum);
				gameData.getPlayer(playerNum).setBufferDirection(newDirection);
			}
		}
	}
	
	public void startGame(int TICK_RATE) {
		this.TICK_RATE = TICK_RATE;
		startGameTimer();
	}
	
	public void setGameData(GameData gameData) {
		this.gameData = gameData;
	}
	
	public GameData getGameData() {
		return gameData;
	}
	
	public PlayerNum getPlayerNum() {
		return playerNum;
	}
	
	public int getCurrentKey() {
		return currentKeyCode;
	}
	
	private void startGameTimer() {
		System.out.println("startGameTimer");
		
		gameData = new GameData();
		lastServerTick = 0;
		
		//defining guiUpdater
		
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
		
		
		
		/*updater = new Timer();
		updater.scheduleAtFixedRate(new TimerTask() {
			
			
			@Override
			public void run() {
				System.out.println(playerNum + ": run game update");
				System.out.println(playerNum + ":Tick: " + gameData.getTick());
				try{
					System.out.println(playerNum + ":Player Pos: " + 
							gameData.getPlayer(playerNum).getX()
							+ ", " + gameData.getPlayer(playerNum).getY());}
				catch(Exception e) {
					System.out.println(e);
				}
				
				gameTick++;
				//Update gameData (Should only happen once)
				while (gameData.getTick() < gameTick) {
					System.out.println(playerNum + ":Client Game Tick");
					GameEngine.updateGame(gameData);
				}
				
				
				try {
					//Get newest gameData from server
					GameData serverState = stub.requestGameData();
					if(serverState.getTick() >= lastServerTick) {
						gameData = serverState;
						lastServerTick = gameData.getTick();
					}
								
				} catch(Exception e) {
					System.out.println(e);
				}
				//Predict forward if gameData old
				while (gameData.getTick() < gameTick) {
					System.out.println(playerNum + ":Post-Server Game Tick");
					GameEngine.updateGame(gameData);
				}
			}
		} , 0, TICK_RATE);*/
	}

}

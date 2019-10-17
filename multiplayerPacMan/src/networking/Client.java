package networking;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.SwingUtilities;

import game.GameEngine;
import game.GameData;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import game.Enumeration.PlayerState;
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
	
	private Timer localUpdater;
	private Timer guiUpdater;
	
	private Collection<GameOutput> outputs = new HashSet<GameOutput>(); 
	
	public Client (String hostName) throws NoRegistryException, FullServerException, RemoteException{
		connectToServer(hostName);
		//-1 denoting pre game lobby
		gameTick = -1;
		lastServerTick = -1;
		
		currentKeyCode = KeyEvent.VK_UNDEFINED;
		gameData = new GameData();
		
		
		
		//defining guiUpdater
		guiUpdater = new Timer();
		guiUpdater.scheduleAtFixedRate(new TimerTask() {
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
	
	private void connectToServer(String hostAddress) throws NoRegistryException, FullServerException, RemoteException {
		try {
			//Getting stub form server registry
			Context namingContext = new InitialContext();
			String url = "rmi://" + hostAddress + "/remote_obj";
			stub = (ServerRemoteObjectInterface) namingContext.lookup(url);
		} catch (NamingException e) {
			throw new NoRegistryException("Naming Exception: " + e.getMessage());
		}
		
		//call to server and receive player number
		playerNum = stub.connectToServer(new ClientRemoteObject(this));
		if (playerNum == PlayerNum.INVALID_PLAYER)
			throw new FullServerException("Server refused to add player\n(Likely that server is full)");
	}
	
	
	public void registerOutput(GameOutput output) {
		outputs.add(output);
	}
	
	public void quitGame() {
		try {
			stub.disconnectFromServer(playerNum);
		} catch (RemoteException e) {
			System.out.println("100");
			System.out.println(e);
		}
		//Ending timers, class is dead
		guiUpdater.cancel();
		localUpdater.cancel();
	}
	
	
	public void updateKeyInput(int keyCode) throws RemoteException{
		if(gameData.getPlayer(playerNum).state == PlayerState.ALIVE) {
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
	}
	
	public void startGame(int TICK_RATE, long startTime) {
		this.TICK_RATE = TICK_RATE;
		long msToStart = startTime;
		int secondsToStart = (int) Math.ceil(msToStart/1000.0);
		gameTick = -1;
		lastServerTick = -1;
		
		//Setting timer to start in sync with server
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				startGameTimer();
			}
		}, startTime);
		
		
		//Setting timer to count down to start
		new Timer().scheduleAtFixedRate(new CountDownTimer(secondsToStart), 
				startTime%1000, 1000);
		
		
	}
	
	public void endGame(PlayerNum winner) {
		
		System.out.println(winner + " won");
		for(GameOutput output: outputs)
			output.setWinState(playerNum == winner);
		localUpdater.cancel();
	}
	
	public void setGameData(GameData gameData) {
		//Ensuring no out of order game data is used
		if(gameData.getTick() > lastServerTick) {
			this.gameData = gameData;
			lastServerTick = gameData.getTick();
			
			//Predicting forward if necessary 
			while(this.gameData.getTick() < gameTick) {
				System.out.println("Prodicting foward");
				this.gameData.gameTick();
			}
		}
		
	}
	
	public GameData getGameData() {
		return gameData;
	}
	
	public PlayerNum getPlayerNum() {
		return playerNum;
	}
	
	public void setPlayerNum(PlayerNum playerNum) {
		this.playerNum = playerNum;
	}
	
	public int getCurrentKey() {
		return currentKeyCode;
	}
	
	private void startGameTimer() {
		System.out.println(playerNum + " startGame");
		
		gameData.getPlayer(playerNum).state = PlayerState.ALIVE;
		
		gameTick = 0;
		lastServerTick = 0;
		
		//Schedule local updates
		localUpdater = new Timer();
		localUpdater.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				gameTick++;
				while(gameData.getTick()<gameTick) {
					//Update gameData locally waiting for server
					gameData.gameTick();
				}
					
			}
			
			
		}, 0, TICK_RATE);
	}
	
	public class CountDownTimer extends TimerTask {
		int count;
		
		public CountDownTimer(int initialCount) {
			this.count = initialCount;
		}
		
		@Override
		public void run() {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					//Update each gui with count down
					for(GameOutput output:outputs) {
						if(count>0 && count <= 3) {
							output.genericPushMessage(String.valueOf(count));
							System.out.println(playerNum + " " + count);
						} else if(count==0){
							output.genericPushMessage(String.valueOf("Go"));
							System.out.println(playerNum + " GO,");
						}
					}
				}
			});
	
			count--;
			
			//ending timer
			if(count<0)
				this.cancel();
		}
	}

	
}

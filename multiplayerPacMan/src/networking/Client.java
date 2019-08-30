package networking;

import java.awt.event.KeyEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import game.GameState;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import game.interfaces.RemoteObjectInterface;
import gui.SimpleOutPut;
import networking.exceptions.FullServerException;
import networking.exceptions.NoRegistryException;

public class Client {
	RemoteObjectInterface stub;
	PlayerNum playerNum;
	
	GameState gameState;
	int gameTick;
	int currentKeyCode;
	
	Timer guiUpdate;
	Timer clientSideGameUpdate;
	Timer gameStateRequest;
	
	public Client (String hostName) throws NoRegistryException, FullServerException {
		connectToServer(hostName);
	}
	
	//TODO register guiMethod (or observer pattern)
	
	
	private void connectToServer(String hostAddress) throws NoRegistryException, FullServerException {
		try {
		//Getting stub form server registry
		Context namingContext = new InitialContext();
		String url = "rmi://" + hostAddress + "/remote_obj";
		stub = (RemoteObjectInterface) namingContext.lookup(url);
		} catch (NamingException e) {
			throw new NoRegistryException("Naming Exception: " + e.getMessage());
		}
		
		
		//call to server and receive player number
		playerNum = stub.connectToServer();
		
		if (playerNum == PlayerNum.INVALID_PLAYER) {
			throw new FullServerException("Server refused to add player\n(Likely that server is full)");
		}
	}
	
	public void updateKeyInput(int keyCode) {
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
				gameState.getPlayer(playerNum).setDirection(newDirection);
			}
		}
	}
	
	public void requestGameState() {
		gameState = stub.requestGameState();
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public PlayerNum getPlayerNum() {
		return playerNum;
	}

}

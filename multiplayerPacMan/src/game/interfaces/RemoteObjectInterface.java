package game.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import game.GameState;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;

public interface RemoteObjectInterface extends Remote {
	
	PlayerNum connectToServer() throws RemoteException;
	
	void updateInput(Direction direction, PlayerNum playerNum) throws RemoteException;
	
	GameState requestGameState() throws RemoteException;
	
	boolean gameStarted() throws RemoteException;

}

package game.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import game.GameData;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;

public interface RemoteObjectInterface extends Remote {
	
	PlayerNum connectToServer() throws RemoteException;
	
	void updateInput(Direction direction, PlayerNum playerNum) throws RemoteException;
	
	GameData requestGameData() throws RemoteException;
	
	boolean gameStarted() throws RemoteException;

}

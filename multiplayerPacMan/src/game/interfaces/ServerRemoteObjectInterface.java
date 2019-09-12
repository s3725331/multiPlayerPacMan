package game.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import game.GameData;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;

public interface ServerRemoteObjectInterface extends Remote {
	
	PlayerNum connectToServer(ClientRemoteObjectInterface clientStub) throws RemoteException;
	
	void updateInput(Direction direction, PlayerNum playerNum) throws RemoteException;
	
	boolean gameStarted() throws RemoteException;

}

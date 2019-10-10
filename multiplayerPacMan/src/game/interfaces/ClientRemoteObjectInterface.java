package game.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import game.GameData;

public interface ClientRemoteObjectInterface extends Remote {

	public void sendGameData(GameData gameData) throws RemoteException;
	
	public void startGame(int TICK_RATE,long startTime) throws RemoteException; 
}

package networking;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import game.GameData;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import game.interfaces.ClientRemoteObjectInterface;
import game.interfaces.ServerRemoteObjectInterface;

public class ClientRemoteObject extends UnicastRemoteObject implements ClientRemoteObjectInterface{
	private Client client;
	
	
	public ClientRemoteObject(Client client) throws RemoteException{
		this.client = client;
	}

	@Override
	public void setPlayerNumber(PlayerNum playerNum) throws RemoteException {
		client.setPlayerNum(playerNum);
	}

	@Override
	public void sendGameData(GameData gameData) throws RemoteException {
		client.setGameData(gameData);
		
	}


	@Override
	public void startGame(int TICK_RATE, long startTime) throws RemoteException {
		client.startGame(TICK_RATE, startTime);
		
	}
	
	@Override
	public void endGame(PlayerNum winner) throws RemoteException {
		client.endGame(winner);
	}


	
	
}

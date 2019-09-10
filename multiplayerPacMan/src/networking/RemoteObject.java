package networking;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import game.GameData;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import game.interfaces.RemoteObjectInterface;

public class RemoteObject extends UnicastRemoteObject implements RemoteObjectInterface{
	Server server;
	
	public RemoteObject(Server server)throws RemoteException {
		this.server = server;
	}
	
	@Override
	public PlayerNum connectToServer() throws RemoteException{
		return server.addNewClient();
	}

	@Override
	public void updateInput(Direction direction, PlayerNum playerNum) throws RemoteException{
		server.updateClientInput(direction,playerNum);
	}

	@Override
	public GameData requestGameData() throws RemoteException{
		return server.getGameData();
	}
	
	@Override
	public boolean gameStarted() throws RemoteException {
		return server.getGameStart();
	}

}

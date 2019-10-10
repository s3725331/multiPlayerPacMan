package networking;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import game.GameData;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import game.interfaces.ClientRemoteObjectInterface;
import game.interfaces.ServerRemoteObjectInterface;

public class ServerRemoteObject extends UnicastRemoteObject implements ServerRemoteObjectInterface{
	Server server;
	
	public ServerRemoteObject(Server server)throws RemoteException {
		this.server = server;
	}
	
	@Override
	public PlayerNum connectToServer(ClientRemoteObjectInterface clientStub) throws RemoteException{
		return server.addNewClient(clientStub);
	}

	@Override
	public void updateInput(Direction direction, PlayerNum playerNum) throws RemoteException{
		server.updateClientInput(direction,playerNum);
	}
}

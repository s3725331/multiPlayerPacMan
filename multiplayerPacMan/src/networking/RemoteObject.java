package networking;

import game.GameState;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import game.interfaces.RemoteObjectInterface;

public class RemoteObject implements RemoteObjectInterface{
	Server server;
	
	public RemoteObject(Server server) {
		this.server = server;
	}

	@Override
	public PlayerNum connectToServer() {
		return server.addNewClient();
	}

	@Override
	public void updateInput(Direction direction, PlayerNum playerNum) {
		server.updateClientInput(direction,playerNum);
	}

	@Override
	public GameState requestGameState() {
		return server.getGameState();
	}

}

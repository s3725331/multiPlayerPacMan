package game.interfaces;

import game.GameState;
import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;

public interface RemoteObjectInterface {
	
	PlayerNum connectToServer();
	
	void updateInput(Direction direction, PlayerNum playerNum);
	
	GameState requestGameState();
	

}

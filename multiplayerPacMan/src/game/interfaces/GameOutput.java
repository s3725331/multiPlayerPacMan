package game.interfaces;

import game.GameData;

public interface GameOutput {
	void updateGame(GameData gameState);
	
	void setWinState(boolean win);
	
	void genericPushMessage(String message);

}
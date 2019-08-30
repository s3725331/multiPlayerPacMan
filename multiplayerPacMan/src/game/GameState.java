package game;

import game.Enumeration.PlayerNum;

public class GameState {
	int playerNum;
	
	public GameState() {
		playerNum = 0;
	}
	
	public int getNumPlayers() {
		return playerNum;
	}
	
	public PlayerNum addPlayer() {
		playerNum++;
		return PlayerNum.PLAYER_TWO;
	}
	
	public PlayerData getPlayer(PlayerNum playerNum) {
		return new PlayerData();
	}
	
	public GameMap getGameMap() {
		return null;
	}

}

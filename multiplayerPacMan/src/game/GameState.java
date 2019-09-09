package game;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import game.Enumeration.PlayerNum;

public class GameState implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numPlayers;
	private int gameTick;
	private Map<PlayerNum, PlayerData> players = new HashMap<PlayerNum, PlayerData>();
	
	public GameState() {
		numPlayers = 0;
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	public int getTick() {
		return gameTick;
	}
	
	public void gameTick() {
		gameTick++;
	}
	
	public PlayerNum addPlayer() {
		
		PlayerNum playerNum;
		if((playerNum = PlayerNum.numToPlayerNum(numPlayers+1)) != PlayerNum.INVALID_PLAYER) {
			numPlayers++;
			players.put(playerNum, new PlayerData(playerNum, 1, 1));	
		}
		return playerNum;
	}
	
	public PlayerData getPlayer(PlayerNum playerNum) {
		return players.get(playerNum);
	}
	
	public Collection<PlayerData> getPlayers(){
		return players.values();
	}

}

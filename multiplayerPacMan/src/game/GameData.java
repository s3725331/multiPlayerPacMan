package game;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import game.Enumeration.PlayerNum;

public class GameData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numPlayers;
	private int gameTick;
	private Map<PlayerNum, PlayerData> players = new HashMap<PlayerNum, PlayerData>();
	private GhostData ghost;
	
	
	public GameData() {
		numPlayers = 0;
		ghost = new GhostData(5,1);
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
	
	public GhostData getGhost() {
		return ghost;
	}
	
	public Collection<PlayerData> getPlayers(){
		return players.values();
	}

}

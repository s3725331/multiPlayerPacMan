package game;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import game.Enumeration.PlayerNum;
import game.Enumeration.PlayerState;

public class GameData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numPlayers;
	private int gameTick;
	private GameMap map;
	private Map<PlayerNum, PlayerData> players = new HashMap<PlayerNum, PlayerData>();
	private GhostData ghost;
	
	
	public GameData() {
		numPlayers = 0;
		map = new GameMap();
		int[] ghostPos = map.getStartingGhostPos();
		ghost = new GhostData(ghostPos[0],ghostPos[1]);
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
			
			int[] pos = map.getStartingPos(playerNum);
			players.put(playerNum, new PlayerData(playerNum, pos[0], pos[1], PlayerState.WAITING));	
		}
		return playerNum;
	}
	
	public PlayerNum addPlayer(PlayerNum playerNum) {
		if(players.get(playerNum) == null) {
			numPlayers++;
			
			int[] pos = map.getStartingPos(playerNum);
			players.put(playerNum, new PlayerData(playerNum, pos[0], pos[1], PlayerState.WAITING));
		}else {
			playerNum = PlayerNum.INVALID_PLAYER;
		}
		return playerNum;
	}
	
	public void removePlayer(PlayerNum playerNum) {
		players.remove(playerNum);
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

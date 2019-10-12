package game;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import game.Enumeration.BlockType;
import game.Enumeration.PlayerNum;

public class GameMap implements Serializable{
	private static final long serialVersionUID = 1L;
	/*private BlockType[][] map{{BlockType.WALL,BlockType.WALL,BlockType.WALL,BlockType.WALL,BlockType.WALL},
									{BlockType.WALL,BlockType.EMPTY_EMPTY_PATH,BlockType.EMPTY_EMPTY_PATH,BlockType.EMPTY_EMPTY_PATH,BlockType.WALL},
									{BlockType.WALL,BlockType.EMPTY_EMPTY_PATH,BlockType.WALL,BlockType.EMPTY_EMPTY_PATH,BlockType.WALL},
									{BlockType.WALL,BlockType.WALL,BlockType.WALL,BlockType.WALL,BlockType.WALL}},
		*/	
	private int width = 16;
	private int height = 15;
	
	
	
	private BlockType[][] map = {{BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL},


	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.WALL},

	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL},
	
	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL},

	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL},
	
	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL},
	
	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL},
	
	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.WALL},
	
	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL},

	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL},
	
	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL},
	
	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL},
	
	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.WALL},
	
	{BlockType.WALL,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.EMPTY_PATH,
	BlockType.WALL},
	
	{BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL,
	BlockType.WALL}};
	
	
	
	
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public BlockType getBlockAt(int x,int y) {
		return map[y][x];
	}
	
	public int[] getStartingPos(PlayerNum playerNum) {
		int[] pos = new int[] {};
		switch(playerNum) {
		case INVALID_PLAYER:
			break;
		case PLAYER_ONE:
			pos = new int[]{1,1};
			break;
		case PLAYER_TWO:
			pos = new int[]{14,1};
			break;
		case PLAYER_THREE:
			pos = new int[]{1,13};
			break;
		case PLAYER_FOUR:
			pos = new int[]{14,13};
			break;
		default:
			break;
		}
		
		return pos;
	}
	
	public int[] getStartingGhostPos() {
		return new int[] {5,1};
	}
	
	public int[][] getWallCoords(){
		Set<int[]> wallSet = new HashSet<int[]>();
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				if(map[i][j] == BlockType.WALL)
					wallSet.add(new int[]{i,j});
			}
		}
		
		return wallSet.toArray(new int[2][wallSet.size()]);
	}

}

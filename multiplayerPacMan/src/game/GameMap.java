package game;

import java.util.HashSet;
import java.util.Set;

import game.Enumeration.BlockType;

public class GameMap {
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
	
	public int[][] getWallCoords(){
		Set<int[]> wallSet = new HashSet<int[]>();
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				if(map[i][j] == BlockType.WALL)
					wallSet.add(new int[]{j,i});
			}
		}
		
		return wallSet.toArray(new int[2][wallSet.size()]);
	}

}

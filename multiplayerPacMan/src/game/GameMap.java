package game;

import game.Enumeration.BlockType;

public class GameMap {
	
	public int getHeight() {
		return 0;
	}
	
	public int getWidth() {
		return 0;
	}
	
	public BlockType getBlockAt(int x,int y) {
		return BlockType.WALL;
	}

}

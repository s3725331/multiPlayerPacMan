package game;
import game.Enumeration.BlockType;
import game.Enumeration.Direction;

public class GameEngine {
	private static GameMap map = new GameMap();
	
	public static void updateGame(GameData gameState) {
		for(PlayerData player:gameState.getPlayers()) {
			movementUpdate(player);
		}
		gameState.gameTick();
	}
	
	private static void movementUpdate(PlayerData player) {
		int x;
		int y;
		
		switch(player.getBufferDirection()) {
		case RIGHT:
			x = player.getX() + 1;
			y = player.getY();
			if (x < map.getWidth() && 
					map.getBlockAt(x,y) == BlockType.EMPTY_PATH)
				player.useDirBuffer();
			
			break;
			
		case LEFT:
			x = player.getX() - 1;
			y = player.getY();
			if (x >= 0 && 
					map.getBlockAt(x,y) == BlockType.EMPTY_PATH)
				player.useDirBuffer();
			
			break;
			
		case UP:
			x = player.getX();
			y = player.getY() - 1;
			if (y >= 0 && 
					map.getBlockAt(x,y) == BlockType.EMPTY_PATH)
				player.useDirBuffer();
			
			break;
		case DOWN:
			x = player.getX();
			y = player.getY() + 1;
			if (y < map.getHeight() && 
					map.getBlockAt(x,y) == BlockType.EMPTY_PATH)
				player.useDirBuffer();
			
			break;
			
		default:
			break;
			
		}
		switch(player.getDirection()) {
		case RIGHT:
			x = player.getX() + 1;
			y = player.getY();
			if (x < map.getWidth() && 
					map.getBlockAt(x,y) == BlockType.EMPTY_PATH)
				player.setPos(x,y);
			
			break;
			
		case LEFT:
			x = player.getX() - 1;
			y = player.getY();
			if (x >= 0 && 
					map.getBlockAt(x,y) == BlockType.EMPTY_PATH) 
				player.setPos(x,y);
			
			break;
			
		case UP:
			x = player.getX();
			y = player.getY() - 1;
			if (y >= 0 && 
					map.getBlockAt(x,y) == BlockType.EMPTY_PATH) 
				player.setPos(x,y);
			
			break;
			
		case DOWN:
			x = player.getX();
			y = player.getY() + 1;
			if (y < map.getHeight() && 
					map.getBlockAt(x,y) == BlockType.EMPTY_PATH)
				player.setPos(x,y);
			
			break;
			
		default:
			break;
			
		}
		
		
		
		
		
	}
}


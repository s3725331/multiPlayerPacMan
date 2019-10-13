package game;

import java.util.List;

import AStar.AStar;
import AStar.Node;
import game.Enumeration.BlockType;
import game.Enumeration.PlayerState;

public class GameEngine {
	private static GameMap map = new GameMap();

	public static void updateGame(GameData gameState) {
		GhostData ghost = gameState.getGhost();

		int minDistance = 1000;
		List<Node> minPath = null;
		for (PlayerData player : gameState.getPlayers()) {
			if(player.state == PlayerState.ALIVE) {
				movementUpdate(player);
				
				//Checking if player hits ghost
				if(player.getX() == ghost.getX() && player.getY() == ghost.getY()) {
					player.state = PlayerState.DEAD;
				}else {
					//Getting path from ghost to player
					List<Node> currentPath = ghostMovementUpdate(ghost, player);
					//Updating minPath if current player is closest to ghost
					if (currentPath.size() < minDistance) {
						minDistance = currentPath.size();
						minPath = currentPath;
					}
				}
			}
		}
		ghost.setPos(minPath.get(1));

		//Checking if player hits ghost after ghost moves
		for (PlayerData player : gameState.getPlayers()) {
			if(player.getX() == ghost.getX() && player.getY() == ghost.getY())
				player.state = PlayerState.DEAD;
		}

		gameState.gameTick();
	}

	private static void movementUpdate(PlayerData player) {
		int x;
		int y;

		switch (player.getBufferDirection()) {
		case RIGHT:
			x = player.getX() + 1;
			y = player.getY();
			if (x < map.getWidth() && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				player.useDirBuffer();

			break;

		case LEFT:
			x = player.getX() - 1;
			y = player.getY();
			if (x >= 0 && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				player.useDirBuffer();

			break;

		case UP:
			x = player.getX();
			y = player.getY() - 1;
			if (y >= 0 && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				player.useDirBuffer();

			break;
		case DOWN:
			x = player.getX();
			y = player.getY() + 1;
			if (y < map.getHeight() && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				player.useDirBuffer();

			break;

		default:
			break;

		}
		switch (player.getDirection()) {
		case RIGHT:
			x = player.getX() + 1;
			y = player.getY();
			if (x < map.getWidth() && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				player.setPos(x, y);

			break;

		case LEFT:
			x = player.getX() - 1;
			y = player.getY();
			if (x >= 0 && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				player.setPos(x, y);

			break;

		case UP:
			x = player.getX();
			y = player.getY() - 1;
			if (y >= 0 && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				player.setPos(x, y);

			break;

		case DOWN:
			x = player.getX();
			y = player.getY() + 1;
			if (y < map.getHeight() && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				player.setPos(x, y);

			break;

		default:
			break;

		}

	}

	private static List<Node> ghostMovementUpdate(GhostData ghost, PlayerData player) {
		Node startNode = new Node(ghost.getY(), ghost.getX());
		Node endNode = new Node(player.getY(), player.getX());
		int rows = map.getHeight();
		int cols = map.getWidth();

		AStar aStar = new AStar(rows, cols, startNode, endNode);
		
		// set walls
		aStar.setBlocks(map.getWallCoords());

		// find end Node
		return aStar.findPath();
		
	}

}

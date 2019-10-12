package game;

import java.util.List;

import aStarAlgorithm.AStar;
import aStarAlgorithm.Node;
import game.Enumeration.BlockType;

public class GameEngine {
	private static GameMap map = new GameMap();

	public static void updateGame(GameData gameState) {

		int minDistance = 1000;
		List<Node> minPath = null;
		for (PlayerData player : gameState.getPlayers()) {
			movementUpdate(player);

			List<Node> currentPath = ghostMovementUpdate(gameState.getGhost(), player);
			if (currentPath.size() < minDistance) {
				minDistance = currentPath.size();
				minPath = currentPath;
			}

			gameState.getGhost().setPos(minPath.get(1));
			
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
		Node startNode = new Node(ghost.getX(), ghost.getY());
		Node endNode = new Node(player.getX(), player.getY());
		int rows = map.getHeight();
		int cols = map.getWidth();

		AStar aStar = new AStar(rows, cols, startNode, endNode);

		// set walls
		aStar.setBlocks(map.getWallCoords());

		// find end Node
		return aStar.findPath();
		
	}

}

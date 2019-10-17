package game;

import java.util.Collection;
import java.util.List;

import AStar.AStar;
import AStar.Node;
import game.Enumeration.BlockType;
import game.Enumeration.PlayerState;

public class GameEngine {
	private static GameMap map = new GameMap();

	public static void updateGame(GameData gameState) {
		GhostData ghost = gameState.getGhost();
		ghost.updateFrozen();

		
		
		int minDistance = map.getHeight()*map.getWidth();
		List<Node> minPath = null;
		for (PlayerData player : gameState.getPlayers()) {
			if(player.state == PlayerState.ALIVE) {
				movementUpdate(gameState.getPlayers(),player);
				
				//Checking if player hits ghost
				if(player.getX() == ghost.getX() && player.getY() == ghost.getY()) {
					player.state = PlayerState.DEAD;
					ghost.setFrozen();
					
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
		
		
		//Updating ghost if not frozen
		if(!ghost.getFrozen()) {
			ghost.setPos(minPath.get(1));

			//Checking if player hits ghost after ghost moves
			for (PlayerData player : gameState.getPlayers()) {
				if(player.getX() == ghost.getX() && player.getY() == ghost.getY()) {
					player.state = PlayerState.DEAD;
					ghost.setFrozen();
				}
			}
		}

		gameState.gameTick();
	}

	private static void movementUpdate(Collection<PlayerData> players, PlayerData player) {
		int x;
		int y;

		//Moving buffered direction to direction if player can now move in that direction
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
		
		//Updating players position based on direction
		boolean empty = false;
		
		switch (player.getDirection()) {
		case RIGHT:
			x = player.getX() + 1;
			y = player.getY();
			if (x < map.getWidth() && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				empty = true;
			break;

		case LEFT:
			x = player.getX() - 1;
			y = player.getY();
			if (x >= 0 && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				empty = true;
			break;

		case UP:
			x = player.getX();
			y = player.getY() - 1;
			if (y >= 0 && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				empty = true;

			break;

		case DOWN:
			x = player.getX();
			y = player.getY() + 1;
			if (y < map.getHeight() && map.getBlockAt(x, y) == BlockType.EMPTY_PATH)
				empty = true;

			break;

		default:
			x= -1;
			y= -1;
			break;
			

		}
		
		//If target tile is a path
		if(empty) {
			//Testing that player is not moving into another player
			boolean emptyOfPlayers = true;
			for(PlayerData otherPlayer: players) {
				if(player.getPlayerNum() != otherPlayer.getPlayerNum()) {
					
					if(otherPlayer.state == PlayerState.ALIVE && 
							otherPlayer.getX() == x && otherPlayer.getY() == y) {
						emptyOfPlayers = false;
						break;
					}
				}
			}
			
			//Moving player if target tile is free
			if(emptyOfPlayers && x >= 0 && y >= 0)
				player.setPos(x, y);
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

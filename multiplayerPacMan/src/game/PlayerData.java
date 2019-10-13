package game;

import java.io.Serializable;

import game.Enumeration.Direction;
import game.Enumeration.PlayerNum;
import game.Enumeration.PlayerState;

public class PlayerData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private Direction direction = Direction.NO_DIRECTION;
	private Direction bufferDirection = Direction.NO_DIRECTION;
	private PlayerNum playerNum;
	public PlayerState state;
	
	public PlayerData(PlayerNum playerNum, int x, int y, PlayerState state) {
		this.playerNum = playerNum;
		this.x = x;
		this.y = y;
		this.state = state;
	}

	public void setBufferDirection(Direction direction) {
		this.bufferDirection = direction;
	}
	
	public Direction getBufferDirection() {
		return bufferDirection;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void  useDirBuffer() {
		direction = bufferDirection;
		bufferDirection = Direction.NO_DIRECTION;
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setPos(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public PlayerNum getPlayerNum() {
		return playerNum;
	}
}

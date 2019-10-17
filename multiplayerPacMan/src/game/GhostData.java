package game;

import java.io.Serializable;

import AStar.Node;
import game.Enumeration.Direction;

public class GhostData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FROZEN_TIME = 10;
	private int x;
	private int y;
	private Direction direction = Direction.NO_DIRECTION;
	private int timeToUnfrozen;
	
	public GhostData(int x,int y) {
		this.x = x;
		this.y = y;
		timeToUnfrozen = 0;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setFrozen() {
		timeToUnfrozen = FROZEN_TIME;
	}
	
	public void updateFrozen() {
		timeToUnfrozen -= Math.signum(timeToUnfrozen);
	}
	
	public boolean getFrozen() {
		if(timeToUnfrozen != 0)
			return true;
		else
			return false;
	}
	
	public void setPos(Node node) {
		y = node.getRow();
		x = node.getCol();
	}
}

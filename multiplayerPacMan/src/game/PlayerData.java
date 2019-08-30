package game;

import game.Enumeration.Direction;

public class PlayerData {
	Direction direction = Direction.NO_DIRECTION;

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return direction;
	}
}

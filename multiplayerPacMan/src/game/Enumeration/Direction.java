package game.Enumeration;

import java.awt.event.KeyEvent;

public enum Direction {
	UP,LEFT,DOWN,RIGHT,NO_DIRECTION;
	
	public static Direction keyCode2Direction(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP:
			return UP;
		case KeyEvent.VK_DOWN:
			return DOWN;
		case KeyEvent.VK_LEFT:
			return LEFT;
		case KeyEvent.VK_RIGHT:
			return RIGHT;
		default: return NO_DIRECTION;
		}
	}
}

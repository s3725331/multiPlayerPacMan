package game.Enumeration;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public enum Direction{
	UP{
		@Override
		public int toAngle() {
			return 90;
		}
	},
	
	LEFT{
		@Override
		public int toAngle() {
			return 180;
		}
	},
	
	DOWN{
		@Override
		public int toAngle() {
			return 270;
		}
	},
	
	RIGHT{
		@Override
		public int toAngle() {
			return 0;
		}
	},
	
	NO_DIRECTION;
	
	public int toAngle() {
		return -1;
	}
	
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

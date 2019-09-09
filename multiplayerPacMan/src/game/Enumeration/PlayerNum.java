package game.Enumeration;

import java.io.Serializable;

public enum PlayerNum{
	PLAYER_ONE,PLAYER_TWO,PLAYER_THREE,PLAYER_FOUR,INVALID_PLAYER;
	
	public static PlayerNum numToPlayerNum(int num) {
		switch(num) {
		case 1:
			return PLAYER_ONE;
		case 2:
			return PLAYER_TWO;
		case 3:
			return PLAYER_THREE;
		case 4:
			return PLAYER_FOUR;
		default:
			return INVALID_PLAYER;
		}
	}

}

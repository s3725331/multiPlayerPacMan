package gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import game.GameMap;
import game.GameState;
import networking.Client;

public class SimpleOutPut extends JFrame {
	JLabel gameText;
	
	public SimpleOutPut(Client client) {
		setTitle("Multiplayer PacMan");
		setBounds(100,100,400,200);
		setVisible(true);
		
		new JLabel("Multiplayer PacMan");
		
		gameText = new JLabel("\n\n\n\n\n\n\n\n\n\n\n\n");
		
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent keyCode) {
				client.updateKeyInput(keyCode.getKeyCode());
				
			}

			/*@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}*/
			
		});
	}
	
	public void updateGameText(GameState gameState) {
		String output = "";
		
		GameMap gameMap = gameState.getGameMap();
		
		for(int i = 0; i < gameMap.getHeight(); i++) {
			for(int j = 0; j <gameMap.getWidth(); j++) {
				
				switch(gameMap.getBlockAt(j,i)) {
				case COIN:
					output += "•";
					break;
				case EMPTY_PATH:
					output += " ";
					break;
				case WALL:
					output += "=";
					break;
				default:
					break;
				
				}
			}
			output += "\n";
		}
		
		gameText.setText(output);
	}
}

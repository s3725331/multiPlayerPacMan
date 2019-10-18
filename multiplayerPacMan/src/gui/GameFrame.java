package gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import game.GameData;
import game.interfaces.GameOutput;
import networking.Client;

public class GameFrame extends JFrame implements GameOutput{
	private GamePanel gamePanel;
	
	
	public GameFrame(Client client, String title) {
		//Setting game window tile
		setTitle(title);
		//Sets size/position of game window (x1,y1,x2,y2)
		setBounds(100,100,530,520);
		
		
		//Adding gamePanel to center of screen
		gamePanel = new GamePanel(client.getGameData());
		add(gamePanel);
		
		//Catches keyboard inputs and gives them to client
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent keyCode) {
				try {
				client.updateKeyInput(keyCode.getKeyCode());
				} catch(Exception e) {
					
				}	
			}
		});
		
		//Adding close game functionality
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        client.quitGame();
		    }
		});
		
		
		setVisible(true);	
	}
	
	public void updateGame(GameData gameData) {
		//Updating gameData for panel
		gamePanel.updateGameData(gameData);
		//Forces game panel to call paint()
		gamePanel.repaint();
	}

	@Override
	public void setWinState(boolean win) {
		if(win)
			gamePanel.updateDisplayString("You Win!!");
		else
			gamePanel.updateDisplayString("You Lose");
		
	}

	@Override
	public void genericPushMessage(String message) {
		gamePanel.updateDisplayString(message);
		
	}
		

}

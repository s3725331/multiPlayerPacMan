package gui;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import game.GameMap;
import game.GameData;
import game.PlayerData;
import game.interfaces.GameOutput;
import networking.Client;

public class SimpleOutPut extends JFrame implements GameOutput{
	private final int FRAME_RATE = 30;
	JLabel gameText;
	
	public SimpleOutPut(Client client, String title) {
		setTitle(title);
		setBounds(100,100,330,500);
		setVisible(true);
		
		gameText = new JLabel("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		gameText.setFont(new Font("Courier", Font.PLAIN, 18));
		add(gameText);
		
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent keyCode) {
				try {
				client.updateKeyInput(keyCode.getKeyCode());
				} catch(Exception e) {
					
				}	
			}
		});
		
		/*
		//defining guiUpdater
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				updateGameText(client.getGameData());
			}
		}, 0, 1000/FRAME_RATE);*/
	}
	
	private void updateGameText(GameData gameState) {
		String output ="";
		
		GameMap gameMap = new GameMap();
		
		int mapHeight = gameMap.getHeight();
		int mapWidth = gameMap.getWidth();
		
		for(int i = 0; i < mapHeight; i++) {
			for(int j = 0; j < mapWidth; j++) {
				
				switch(gameMap.getBlockAt(j,i)) {
				case COIN:
					output += "•";
					break;
				case EMPTY_PATH:
					output += ".";
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
		
		for(PlayerData player: gameState.getPlayers()) {
			int stringIndex = player.getX() + (mapWidth + 1) * player.getY();
			String playerNum = " ";
			switch (player.getPlayerNum()) {
			case INVALID_PLAYER:
				break;
			case PLAYER_FOUR:
				playerNum = "4";
				break;
			case PLAYER_ONE:
				playerNum = "1";
				break;
			case PLAYER_THREE:
				playerNum = "3";
				break;
			case PLAYER_TWO:
				playerNum = "2";
				break;
			default:
				break;
			}
			
			output = output.substring(0,stringIndex) + playerNum + output.substring(stringIndex+1);
			
		}
		
		//int ghostIndex = game
		gameText.setText(getHtml(output));
		gameText.repaint();
	}

	@Override
	public void updateGame(GameData gameState) {
		updateGameText(gameState);
	}

	@Override
	public void setWinState(boolean win) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void genericPushMessage(String message) {
		//Dialog box
		
	}
	
	private String getHtml(String string) {
		String html = "<html>" + string;
		html.replaceAll("\n", "<br/>");
		html += "</html>";
		
		return html;
	}
}

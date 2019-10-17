package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.GameData;
import game.GameMap;
import game.PlayerData;
import game.Enumeration.BlockType;
import game.Enumeration.PlayerState;
import game.GhostData;

public class GamePanel extends JPanel{
	private GameData gameData;
	private GameMap map;
	
	private final int FONT_SIZE = 110;
	private final int LETTER_OFFSET = 51;
	private String displayText;
	private int opacity;
	
	//Images
	//TODO add other images and change what image pacman is (its wall.png right now)
	private BufferedImage pacMan;
	private BufferedImage pacMan2;
	private BufferedImage pacMan3;
	private BufferedImage pacMan4;
	private BufferedImage ghost;
	private BufferedImage wall;
	
	public GamePanel(GameData gameData) {
		this.gameData = gameData;
		map = new GameMap();
		opacity = 0;
		
		//image loads
		try {
			//example image load
			pacMan = ImageIO.read(new File("player1.png"));
			//TODO add other images for game
			
		} catch (IOException e) {
			System.out.println("Image not found");
		}
		
		try {

			pacMan2 = ImageIO.read(new File("player2.png"));

		} catch (IOException e) {
			System.out.println("Image not found");
		}
		
		try {

			pacMan3 = ImageIO.read(new File("player3.png"));

		} catch (IOException e) {
			System.out.println("Image not found");
		}
		
		try {

			pacMan4 = ImageIO.read(new File("player4.png"));

		} catch (IOException e) {
			System.out.println("Image not found");
		}
		
		try {

			ghost = ImageIO.read(new File("ghost.png"));

		} catch (IOException e) {
			System.out.println("Image not found");
		}
		
		try {

			wall = ImageIO.read(new File("wall.png"));

		} catch (IOException e) {
			System.out.println("Image not found");
		}
	}
	
	public void updateGameData(GameData data) {
		gameData = data;
	}
	
	public void updateDisplayString(String text) {
		displayText = text;
		opacity = 255;
	}
	
	/*This method controls what the game panel looks like
	 * All modifications to the game panel can be added here
	 */
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Cast graphics object to graphics2d which has some additional functionality
		Graphics2D g2D = (Graphics2D) g;
		//Set some rendering options, taken from further programming, other rendering settings could be also added here 
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		
		//example of how to draw images to the panel
		//drawImage(Image, x, y, horizontal size, vert size, background color, Image Observer)
		//g2D.drawImage(wall, 10, 10, 32, 32, getBackground(), this);
		
		/*There a lots of over versions of drawImage() that you may find useful
		 * There are also a lot of other draw methods you can use
		 * (including fillRect, fillOval ect)
		 * Look up the java docs for Graphics and Graphics2D to find this stuff
		 * */
		
		//Getting game data
		//Use getters in gameData to get the current state of the game
		
		
		try {
			/* Test code to draw players and walls (I just wanted to make sure it was all working)
			 * 
			 * For now if you want to get anything from gameData it needs to be in a try/catch block as this is,
			 * Needing the try blocks is kinda dumb and ill fix it at some point but for now its good enough
			 */
		
			//Draws each player
			for(PlayerData player:gameData.getPlayers()) {
				Image playerImage = pacMan;
				
				if(player.state != PlayerState.DEAD) {
					switch(player.getPlayerNum()) {
					case INVALID_PLAYER:
						break;
					case PLAYER_ONE:
						playerImage = pacMan;
						break;
					case PLAYER_TWO:
						playerImage = pacMan2;
						break;
					case PLAYER_THREE:
						playerImage = pacMan3;
						break;
					case PLAYER_FOUR:
						playerImage = pacMan4;
						break;
					default:
						break;
					
					}
					//Drawing player
					g2D.drawImage(playerImage, player.getX()*32, player.getY()*32, 32, 32, getBackground(), this);
				}
			}
			
			/*
			for(PlayerData player:gameData.getPlayers()) {
				g2D.drawImage(pacMan2, player.getX()*448, player.getY()*32, 32, 32, getBackground(), this);
			}
			
			for(PlayerData player:gameData.getPlayers()) {
				g2D.drawImage(pacMan3, player.getX()*32, player.getY()*416, 32, 32, getBackground(), this);
			}
			
			for(PlayerData player:gameData.getPlayers()) {
				g2D.drawImage(pacMan4, player.getX()*448, player.getY()*416, 32, 32, getBackground(), this);
			}*/
			
			//Drawing ghost
			//for(GhostData player:gameData.getGhosts()) {
			//	g2D.drawImage(pacMan, gameData.getGhost().getX()*224, gameData.getGhost().getY()*224, 32, 32, getBackground(), this);
			//}
			
			//Draw ghost
			g2D.drawImage(ghost, gameData.getGhost().getX()*32, gameData.getGhost().getY()*32, 32, 32, getBackground(), this);
			
			//Draws walls
			for(int i = 0; i< map.getHeight();i++) {
				for(int j = 0; j< map.getWidth();j++) {
					BlockType block = map.getBlockAt(j, i);
					if(block == BlockType.WALL)
						g2D.drawImage(wall, j*32, i*32, 32, 32, getBackground(), this);
				}
			}
			
			
			//Drawing text
			if(opacity > 0) {
				g.setColor(new Color(255,0,0,opacity));
				g.setFont(new Font("Courier", Font.BOLD, FONT_SIZE));
				//g.drawRect(0,0, 100, 100);
				g.drawString(displayText,200 - (displayText.length()/2) * LETTER_OFFSET,250);
				System.out.println(displayText + ", " + opacity);
				opacity -= 255*0.1;
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
	}

}

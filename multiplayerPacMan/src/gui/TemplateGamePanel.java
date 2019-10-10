package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
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

public class TemplateGamePanel extends JPanel{
	private GameData gameData;
	private GameMap map;
	
	//Images
	//TODO add other images and change what image pacman is (its wall.png right now)
	private BufferedImage pacMan;
	
	public TemplateGamePanel(GameData gameData) {
		this.gameData = gameData;
		map = new GameMap();
		
		//image loads
		try {
			//example image load
			pacMan = ImageIO.read(new File("wall.png"));
			//TODO add other images for game
			
		} catch (IOException e) {
			System.out.println("Image not found");
		}
	}
	
	public void updateGameData(GameData data) {
		gameData = data;
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
		g2D.drawImage(pacMan, 10, 10, 32, 32, getBackground(), this);
		
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
		
			//Draws each player (as a wall)
			for(PlayerData player:gameData.getPlayers()) {
				g2D.drawImage(pacMan, player.getX()*32, player.getY()*32, 32, 32, getBackground(), this);
			}
			
			g2D.drawImage(pacMan, gameData.getGhost().getX()*32, gameData.getGhost().getY()*32, 32, 32, getBackground(), this);
			
			//Draws walls
			for(int i = 0; i< map.getHeight();i++) {
				for(int j = 0; j< map.getWidth();j++) {
					BlockType block = map.getBlockAt(j, i);
					if(block == BlockType.WALL)
						g2D.drawImage(pacMan, j*32, i*32, 32, 32, getBackground(), this);
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
	}

}

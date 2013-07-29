package org.spinaltap.prison;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * A single Character that moves and animates via sprites.
 * 
 * @author Tyler Snowden
 */
public class Entity {
        private String name;
	private float x; // Initial X Position
	private float y; // Initial Y Position
	private BufferedImage image; // Sprite Sheet
	private Map map; // Map pertaining to character
        private float boundHeight = .6f; // Boundary Box Height
        private float boundWidth = .4f; // Boundary Box Width
        
        private int width; // Width of character's single sprite
        private int height; // Height of character's single sprite
        
        private BufferedImage[] frames; // Individual Images ripped from Sprite Sheet
        private int currentFrame; // Current Sprite
        private long timer = 0; // Sprite Animation Timing
	
	/**
	 * Create a new entity in the game. 
	 * 
	 * @param name Used to name character and specify sprite sheet
	 * @param map Passed from game
	 * @param x The initial x position of this entity in grid cells
	 * @param y The initial y position of this entity in grid cells
         * @param width Width of character's single sprite
         * @param height Height of character's single sprite
         * @param currentFrame Starting frame from Sprite Sheet
	 */
	public Entity(String name, Map map, float x, float y, int width, int height, int currentFrame) {
                this.currentFrame = currentFrame;
                this.name = name;
		this.image = image;
		this.map = map;
		this.x = x;
		this.y = y;
                this.frames = new BufferedImage[4 * 4];
                this.width = width;
                this.height = height;
                
                try { // Get Sprite Sheet
			URL url = Thread.currentThread().getContextClassLoader().getResource("res/"+name+".png");
			if (url == null) {
				System.err.println("Unable to find sprite: res/"+name+".png");
				System.exit(0);
			}
			this.image = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println("Unable to load sprite: res/"+name+".png");
			System.exit(0);
		}
                
                createFrames(4,4); // Split up Sprite Sheet
	}
	
	/**
	 * Move this entity a given amount depending on collisions.
	 * 
	 * @param dx The amount to move on the x axis
	 * @param dy The amount to move on the y axis
	 * @return True if the move succeeded
	 */
	public boolean move(float dx, float dy) {
		// New Positions
		float nx = x + dx;
		float ny = y + dy;
		
		// Determine Collision
		if (validLocation(nx, ny)) {
			// Assign new position
			x = nx;
			y = ny;
                    
			return true;
		}

		return false; // Do nothing
	}
	
	/**
	 * Check if the entity would be at a valid location if its position
	 * was as specified
	 * 
	 * @param nx The potential x position for the entity
	 * @param ny The potential y position for the entity
	 * @return True if the new position specified would be valid
	 */
	public boolean validLocation(float nx, float ny) {
		if (map.blocked(nx - boundWidth, ny - boundHeight)) {
			return false;
		}
		if (map.blocked(nx + boundWidth, ny - boundHeight)) {
			return false;
		}
		if (map.blocked(nx - boundWidth, ny + boundHeight)) {
			return false;
		}
		if (map.blocked(nx + boundWidth, ny + boundHeight)) {
			return false;
		}
		
		return true; // Not Blocked
	}
	
	/**
	 * Draw this entity to the graphics context provided.
	 * 
	 * @param g The graphics context to which the entity should be drawn
	 */
	public void paint(Graphics2D g) {
		int xp = (int) (Map.TILE_SIZE * x);
		int yp = (int) (Map.TILE_SIZE * y);
	
		g.drawImage(frames[currentFrame], (int) (xp - 16), (int) (yp - 16), null);
	}
        
        /**
	 * Split Sprite Sheet into Frames
	 * 
	 * @param rows Number of Rows in the sprite sheet
         * @param cols Number of Columns in the sprite sheet
	 */
	public void createFrames(int rows, int cols) {
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < cols; j++)
                {
                    this.frames[(i * cols) + j] = this.image.getSubimage(
                        j * Math.round(width),
                        i * Math.round(height),
                        Math.round(width),
                        Math.round(height)
                    );
                }
            }
	}
        
        /**
	 * Update Current Frame while Moving
	 * 
	 * @param delta The amount of time to update for (in milliseconds)
	 */
        public void updateFrame(float dx, float dy, long delta) {
            timer += delta;
            if (timer > 100) { 
                if (dy>0) { // Down
                    timer = 0; 
                    currentFrame++; 
                    if (currentFrame < 8 || currentFrame > 11) currentFrame = 8;
                }
                else if (dy<0) { // Up
                    timer = 0; 
                    currentFrame++; 
                    if (currentFrame > 3) currentFrame = 0;
                }
                else if (dx>0) { // Left
                    timer = 0; 
                    currentFrame++; 
                    if (currentFrame < 4 || currentFrame > 7) currentFrame = 4;
                }
                else if (dx<0) { // Right
                    timer = 0; 
                    currentFrame++; 
                    if (currentFrame < 12 || currentFrame > 15) currentFrame = 12; 
                }
            }
        }
        
        /*
         * Return Entity to Down Idle Sprite. All this does for now.
         */
        public void reset() {
            currentFrame = 9;
        }
        
        /*
         * Converts Class Variables to String for Storage as Array
         * Entity(String name, float x, float y, int width, int height, int currentFrame)
         * 
         * @return String[]
         */
        public String[] entityToString()
        {
             return new String[] { 
                 this.name, 
                 Float.toString(this.x), 
                 Float.toString(this.y), 
                 Integer.toString(this.width), 
                 Integer.toString(this.height),
                 Integer.toString(this.currentFrame)
             };
        }
               
}

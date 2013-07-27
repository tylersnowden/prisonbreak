package org.spinaltap.prison;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * A single entity moving round our map. It maintains its position 
 * in terms of grid cells. Note that the positions are floating 
 * point numbers allowing us to be partially across a cell.
 * 
 * @author Kevin Glass
 */
public class Entity {
	/** The x position of this entity in terms of grid cells */
	private float x;
	/** The y position of this entity in terms of grid cells */
	private float y;
	/** The image to draw for this entity */
	private BufferedImage image;
	/** The map which this entity is wandering around */
	private Map map;
	/** The angle to draw this entity at */
        private float boundHeight = .6f;
        private float boundWidth = .4f;
        
        private int width;
        private int height;
        
        private BufferedImage[] frames;
        private int currentFrame;
        private long timer = 0;
	
	/**
	 * Create a new entity in the game
	 * 
	 * @param image The image to represent this entity (needs to be 32x32)
	 * @param map The map this entity is going to wander around
	 * @param x The initial x position of this entity in grid cells
	 * @param y The initial y position of this entity in grid cells
	 */
	public Entity(String name, Map map, float x, float y, int width, int height, int currentFrame) {
                this.currentFrame = currentFrame;
		this.image = image;
		this.map = map;
		this.x = x;
		this.y = y;
                this.frames = new BufferedImage[4 * 4];
                this.width = width;
                this.height = height;
                
                try {
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
                
                createFrames(4,4);
	}
	
	/**
	 * Move this entity a given amount. This may or may not succeed depending
	 * on collisions
	 * 
	 * @param dx The amount to move on the x axis
	 * @param dy The amount to move on the y axis
	 * @return True if the move succeeded
	 */
	public boolean move(float dx, float dy) {
		// work out what the new position of this entity will be
		float nx = x + dx;
		float ny = y + dy;
		
		// check if the new position of the entity collides with
		// anything
		if (validLocation(nx, ny)) {
			// if it doesn't then change our position to the new position
			x = nx;
			y = ny;
                    
			return true;
		}
		
		// if it wasn't a valid move don't do anything apart from 
		// tell the caller
		return false;
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
		// here we're going to check some points at the corners of
		// the player to see whether we're at an invalid location
		// if any of them are blocked then the location specified
		// isn't valid
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
		
		// if all the points checked are unblocked then we're in an ok
		// location
		return true;
	}
	
	/**
	 * Draw this entity to the graphics context provided.
	 * 
	 * @param g The graphics context to which the entity should be drawn
	 */
	public void paint(Graphics2D g) {
		// work out the screen position of the entity based on the
		// x/y position and the size that tiles are being rendered at. So
		// if we're at 1.5,1.5 and the tile size is 10 we'd render on screen 
		// at 15,15.
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
        
        public void reset() {
            currentFrame = 9;
        }
}

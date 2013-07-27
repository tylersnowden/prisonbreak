package org.spinaltap.prison;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * The map holds the layout about game area. In this case its responsible
 * for both rendering the map and check collision against the grid cells
 * within. 
 * 
 * Our map is a simple WIDTHxHEIGHT grid containing value 0 to indicate
 * a clear cell and 1 to indicate a wall.
 * 
 * @author Kevin Glass
 */
public class Map {
        private static final int NONE = -1;
	private static final int CLEAR = 0;
	private static final int BLOCKED = 1;
        
        private static BufferedImage tileset;
        private static BufferedImage BLOCKED_IMAGE;
        private static BufferedImage CLEAR_IMAGE;
        	
	private static final int WIDTH = 20;
	private static final int HEIGHT = 15;
	public static final int TILE_SIZE = 32;
	
	private int[][] layout = new int[WIDTH][HEIGHT];
        private int[][] texture = new int[WIDTH][HEIGHT];

	/**
	 * Create a new map with some default contents
	 */
	public Map() {
            
            try {
                    URL url = Thread.currentThread().getContextClassLoader().getResource("res/tileset.png");
                    if (url == null) {
                            System.err.println("Unable to find sprite: res/tileset.png");
                            System.exit(0);
                    }
                    tileset = ImageIO.read(url);
            } catch (IOException e) {
                    System.err.println("Unable to load sprite: res/tileset.png");
                    System.exit(0);
            }

		for (int y=0;y<HEIGHT;y++) {
			layout[0][y] = BLOCKED;
                        layout[3][y] = BLOCKED;
			layout[WIDTH-1][y] = BLOCKED;
		}
		for (int x=0;x<WIDTH;x++) {
			layout[x][0] = BLOCKED;
                        layout[x][10] = BLOCKED;
			layout[x][HEIGHT-1] = BLOCKED;
		}
                
                layout[1][10] = CLEAR;
                layout[2][10] = CLEAR;
                layout[3][11] = CLEAR;
                layout[7][10] = CLEAR;
                layout[8][10] = CLEAR;
                layout[3][12] = CLEAR;
                layout[3][13] = CLEAR;
                
                BLOCKED_IMAGE = tileset.getSubimage(80,288,TILE_SIZE,TILE_SIZE);
                CLEAR_IMAGE = tileset.getSubimage(96,128,TILE_SIZE,TILE_SIZE);
                
                //saveMap(layout, "map.dat");
                //layout = loadMap("map.dat");
	}
	
	/**
	 * Render the map to the graphics context provided. The rendering
	 * is just simple fill rectangles
	 * 
	 * @param g The graphics context on which to draw the map
	 */
	public void paint(Graphics2D g) {
		// loop through all the tiles in the map rendering them
		// based on whether they block or not
            g.setColor(Color.darkGray);
            for (int x=0;x<WIDTH;x++) {
                    for (int y=0;y<HEIGHT;y++) {
                        switch(layout[x][y]) {
                            case BLOCKED:
                                g.drawImage(BLOCKED_IMAGE, null, x*TILE_SIZE, y*TILE_SIZE);
                                break;
                            case CLEAR:
                                g.drawImage(CLEAR_IMAGE, null, x*TILE_SIZE, y*TILE_SIZE);
                                break;
                            default: g.fillRect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE); break;  
                        }
                    }
            }
	}
	
	/**
	 * Check if a particular location on the map is blocked. Note
	 * that the x and y parameters are floating point numbers meaning
	 * that we can be checking partially across a grid cell.
	 * 
	 * @param x The x position to check for blocking
	 * @param y The y position to check for blocking
	 * @return True if the location is blocked
	 */
	public boolean blocked(float x, float y) {
		// look up the right cell (based on simply rounding the floating
		// values) and check the value
		return layout[(int) x][(int) y] == BLOCKED;
	}
        
        public void saveLayout(int[][] map, String filename) {
            try {
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
                oos.close();
                fos.close();  
            } catch (Exception e) {

            }
        }
        
        public int[][] loadLayout(String filename) {
            int[][] map = null;
            try {
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream iis = new ObjectInputStream(fis);
		map = (int[][]) iis.readObject();
                iis.close();
                fis.close();

            } catch (Exception e) {

            }
            
            return map;
        }
        
         public void saveTextures(int[][] map, String filename) {
            try {
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
                oos.close();
                fos.close();  
            } catch (Exception e) {

            }
        }
        
        public int[][] loadTextures(String filename) {
            int[][] map = null;
            try {
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream iis = new ObjectInputStream(fis);
		map = (int[][]) iis.readObject();
                iis.close();
                fis.close();

            } catch (Exception e) {

            }
            
            return map;
        }
}

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
 * Map consists of tiles with varying states.
 * 
 * @author Tyler Snowden
 */
public class Map {
	private static final int CLEAR = 0;
	private static final int BLOCKED = 1;
        
        private BufferedImage tileset;
        private BufferedImage[] textures = new BufferedImage[9];
        	
	private static final int WIDTH = 20;
	private static final int HEIGHT = 15;
	public static final int TILE_SIZE = 32;
	
	private int[][] layoutMap = new int[WIDTH][HEIGHT];
        private int[][] textureMap = new int[WIDTH][HEIGHT]; // Texture Indices

	/**
	 * Create a new map. Load Map from File. Load tileset.
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

            textures[0] = tileset.getSubimage(64,0,TILE_SIZE,TILE_SIZE); // Wood
            textures[1] = tileset.getSubimage(32,0,TILE_SIZE,TILE_SIZE); // Tiles
            textures[2] = tileset.getSubimage(32,64,TILE_SIZE,TILE_SIZE); // Wall Vert
            textures[3] = tileset.getSubimage(0,64,TILE_SIZE,TILE_SIZE); // Wall Horiz
            textures[4] = tileset.getSubimage(0,96,TILE_SIZE,TILE_SIZE); // Wall NE
            textures[5] = tileset.getSubimage(32,96,TILE_SIZE,TILE_SIZE); // Wall NW
            textures[6] = tileset.getSubimage(64,64,TILE_SIZE,TILE_SIZE); // Wall SE
            textures[7] = tileset.getSubimage(96,64,TILE_SIZE,TILE_SIZE); // Wall SW
            textures[8] = tileset.getSubimage(32,32,TILE_SIZE,TILE_SIZE); // Tiles Light
            
            // Map Creation --------------------------------
            
            for (int y=0;y<HEIGHT;y++) {
                    layoutMap[0][y] = BLOCKED;
                    layoutMap[10][y] = BLOCKED;
                    layoutMap[3][y] = BLOCKED;
                    layoutMap[WIDTH-1][y] = BLOCKED;
            }
            for (int x=0;x<WIDTH;x++) {
                    layoutMap[x][0] = BLOCKED;
                    layoutMap[x][10] = BLOCKED;
                    layoutMap[x][HEIGHT-1] = BLOCKED;
            }

            layoutMap[1][10] = CLEAR;
            layoutMap[2][10] = CLEAR;
            layoutMap[3][11] = CLEAR;
            layoutMap[10][11] = CLEAR;
            layoutMap[10][12] = CLEAR;
            layoutMap[10][13] = CLEAR;
            layoutMap[3][12] = CLEAR;
            layoutMap[3][13] = CLEAR;
            layoutMap[5][10] = CLEAR;
            layoutMap[6][10] = CLEAR;
            layoutMap[7][10] = CLEAR;
            layoutMap[8][10] = CLEAR;
            layoutMap[12][10] = CLEAR;
            layoutMap[13][10] = CLEAR;
            layoutMap[14][10] = CLEAR;
            layoutMap[15][10] = CLEAR;
                       
            for (int y=0;y<HEIGHT;y++) {
                    textureMap[0][y] = 2;
                    textureMap[1][y] = 1;
                    textureMap[2][y] = 1;
                    textureMap[3][y] = 2;
                    textureMap[4][y] = 1;
                    textureMap[5][y] = 1;
                    textureMap[6][y] = 1;
                    textureMap[7][y] = 1;
                    textureMap[8][y] = 1;
                    textureMap[9][y] = 1;
                    textureMap[10][y] = 2;
                    textureMap[11][y] = 1;
                    textureMap[12][y] = 1;
                    textureMap[13][y] = 1;
                    textureMap[14][y] = 1;
                    textureMap[15][y] = 1;
                    textureMap[16][y] = 1;
                    textureMap[17][y] = 1;
                    textureMap[18][y] = 1;
                    textureMap[WIDTH-1][y] = 2;
            }
            for (int x=0;x<WIDTH;x++) {
                    textureMap[x][0] = 3;
                    textureMap[x][10] = 3;
                    textureMap[x][HEIGHT-1] = 3;
            }

            textureMap[1][10] = 1;
            textureMap[2][10] = 1;
            textureMap[2][10] = 1;
            textureMap[3][11] = 1;
            textureMap[5][10] = 1;
            textureMap[6][10] = 1;
            textureMap[7][10] = 1;
            textureMap[8][10] = 1;
            textureMap[12][10] = 1;
            textureMap[13][10] = 1;
            textureMap[14][10] = 1;
            textureMap[15][10] = 1;
            textureMap[3][12] = 1;
            textureMap[3][13] = 1;
            textureMap[0][10] = 2;
            textureMap[10][11] = 1;
            textureMap[10][12] = 1;
            textureMap[10][13] = 1;
            textureMap[WIDTH-1][10] = 2;
            textureMap[3][10] = 7;
            textureMap[0][0] = 5;
            textureMap[0][HEIGHT-1] = 7;
            textureMap[WIDTH-1][0] = 4;
            textureMap[WIDTH-1][HEIGHT-1] = 6;
            
            assignSquare(textureMap,11,1,8,9,0);
            
            // End Map Creation ----------------------------

            //saveLayout(layoutMap, "layout.dat");
            //layoutMap = loadLayout("layout.dat");
            //saveTextures(textureMap, "texture.dat");
            //textureMap = loadTextures("texture.dat");
	}
        
        /*
         * Assign a value to a square for quicker map design
         * 
         * $param x Grid Position Start
         * @param y Grid Position Start
         * @param width Width in Tiles
         * @param height Height in Tiles
         * @param value Texture or Block property
         */
        private void assignSquare(int[][] map, int x, int y, int width, int height, int value) {
            for (int xc=x;xc<x+width;xc++) {
                for (int yc=y;yc<y+height;yc++) {
                    map[xc][yc] = value;
                }
            }
        }
	
	/**
	 * Render the map. Loop through blocks and render.
	 * 
	 * @param g Graphics Context
	 */
	public void paint(Graphics2D g) {

            g.setColor(Color.darkGray); // Default NULL Tile
            for (int x=0;x<WIDTH;x++) {
                    for (int y=0;y<HEIGHT;y++) {
                        switch(layoutMap[x][y]) {
                            case BLOCKED:
                                g.drawImage(textures[textureMap[x][y]], null, x*TILE_SIZE, y*TILE_SIZE);
                                break;
                            case CLEAR:
                                g.drawImage(textures[textureMap[x][y]], null, x*TILE_SIZE, y*TILE_SIZE);
                                break;
                            default: g.fillRect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE); break;  
                        }
                    }
            }
	}
	
	/**
	 * Check tile for boundary
	 * 
	 * @param x The x position to check for blocking
	 * @param y The y position to check for blocking
	 * @return True if the location is blocked
	 */
	public boolean blocked(float x, float y) {
		return layoutMap[(int) x][(int) y] == BLOCKED;
	}
        
        /*
         * Saves the tiles blocking properties.
         * 
         * @param map Multi-Dimensional array of tiles
         * @param filename Filename to save To
         */
        public void saveLayout(int[][] map, String filename) {
            try {
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
                oos.close();
                fos.close();  
            } catch (Exception e) {
                System.err.println("Unable to Save Map Layout");
                System.exit(0);
            }
        }
        
        /*
         * Loads the tiles blocking properties.
         * 
         * @param filename Filename to load from
         * @return Multi-Dimensional array of tiles
         */
        public int[][] loadLayout(String filename) {
            int[][] map = null;
            try {
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream iis = new ObjectInputStream(fis);
		map = (int[][]) iis.readObject();
                iis.close();
                fis.close();

            } catch (Exception e) {
                System.err.println("Unable to Load Map Layout");
                System.exit(0);
            }
            
            return map;
        }
        
         /*
         * Saves the tiles texture properties.
         * 
         * @param map Multi-Dimensional array of tiles
         * @param filename Filename to save To
         */
        public void saveTextures(int[][] map, String filename) {
            try {
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
                oos.close();
                fos.close();  
            } catch (Exception e) {
                System.err.println("Unable to Save Map Textures");
                System.exit(0);
            }
        }
        
        /*
         * Loads the tiles texture properties.
         * 
         * @param filename Filename to load from
         * @return Multi-Dimensional array of tiles
         */
        public int[][] loadTextures(String filename) {
            int[][] map = null;
            try {
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream iis = new ObjectInputStream(fis);
		map = (int[][]) iis.readObject();
                iis.close();
                fis.close();

            } catch (Exception e) {
                System.err.println("Unable to Load Map Textures");
                System.exit(0);
            }
            
            return map;
        }
}

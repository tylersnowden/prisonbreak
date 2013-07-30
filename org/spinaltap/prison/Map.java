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
import java.util.Arrays;
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
        private BufferedImage[] textures = new BufferedImage[10];
        	
	private static int WIDTH;
	private static int HEIGHT;
	public static final int TILE_SIZE = 32;
	
	private int[][] layoutMap;
        private int[][] textureMap; // Texture Indices
        private Item[] items;
        private Entity[] extras = new Entity[2];

	/**
	 * Create a new map. Load Map from File. Load tileset.
	 */
	public Map(String level, int width, int height) {
            WIDTH = width;
            HEIGHT = height;
            layoutMap = new int[WIDTH][HEIGHT];
            textureMap = new int[WIDTH][HEIGHT];
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

            // Textures --------------------------------------------------------------
            textures[0] = tileset.getSubimage(64,0,TILE_SIZE,TILE_SIZE); // Wood
            textures[1] = tileset.getSubimage(32,0,TILE_SIZE,TILE_SIZE); // Tiles
            textures[2] = tileset.getSubimage(32,64,TILE_SIZE,TILE_SIZE); // Wall Vert
            textures[3] = tileset.getSubimage(0,64,TILE_SIZE,TILE_SIZE); // Wall Horiz
            textures[4] = tileset.getSubimage(0,96,TILE_SIZE,TILE_SIZE); // Wall NE
            textures[5] = tileset.getSubimage(32,96,TILE_SIZE,TILE_SIZE); // Wall NW
            textures[6] = tileset.getSubimage(64,64,TILE_SIZE,TILE_SIZE); // Wall SE
            textures[7] = tileset.getSubimage(96,64,TILE_SIZE,TILE_SIZE); // Wall SW
            textures[8] = tileset.getSubimage(32,32,TILE_SIZE,TILE_SIZE); // Tiles Light
            textures[9] = tileset.getSubimage(0,32,TILE_SIZE,TILE_SIZE); // Tiles Broken
            // End Textures -----------------------------------------------------------
            
            loadMap(level);
            // Put Changes to Map Here and Save --
            
            //saveMap(level);
        }
        
        /*
         * Loads the Textures, Layout, Items, Extras from Folder
         * 
         * @param level Folder containing .dat files.
         */
        public void loadMap(String level) 
        {
            // TODO: Load Maps width and height from file
            layoutMap = loadLayout(level+"/layout.dat");
            textureMap = loadTextures(level+"/texture.dat");
            items = loadItems(level+"/items.dat");
            extras = loadEntities(level+"/entities.dat");
        }
        
        /*
         * Save the Textures, Layout, Items, Extras from Folder
         * 
         * @param level Folder containing .dat files.
         */
        public void saveMap(String level) 
        {
            // TODO: Save Maps width and height to file
            saveLayout(level+"/layout.dat");
            saveTextures(level+"/texture.dat");
            saveItems(level+"/items.dat");
            saveEntities(level+"/entities.dat");
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
            for (int x=0;x<textureMap.length;x++) {
                    for (int y=0;y<textureMap[x].length;y++) g.drawImage(textures[textureMap[x][y]], null, x*TILE_SIZE, y*TILE_SIZE);
            }
            for (int i=0; i<items.length;i++) items[i].paint(g);
            for (int i=0; i<extras.length;i++) extras[i].paint(g);
            
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
        public void saveLayout(String filename) {
            try {
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(layoutMap);
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
        public void saveTextures(String filename) {
            try {
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(textureMap);
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
        
         /*
         * Saves the Items
         * String[][] itemsData = new String[8][7];
         * temsData[0] = new String[] { "bookshelf", "211","111","64","62","17","1" };
         * itemsData[1] = new String[] { "bookshelf", "211","111","64","62","11","1" };
         * itemsData[2] = new String[] { "toilet", "0","0","32","50","4","1" };
         * itemsData[3] = new String[] { "rug", "3","178","85","57","14","6" };
         * itemsData[4] = new String[] { "plant", "138","184","27","46","11","8" };
         * itemsData[5] = new String[] { "bars", "238","275","32","32","6","10" };
         * itemsData[6] = new String[] { "bars", "238","275","32","32","7","10" };
         * itemsData[7] = new String[] { "bars", "238","275","32","32","8","10" };
         * 
         * @param items Array of Items
         * @param filename Filename to save To
         */
        public void saveItems(String filename) {
            String[][] data = new String[items.length][7];
            for (int i=0;i<items.length;i++) data[i] = items[i].itemToString();
            
            try {
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(data);
                oos.close();
                fos.close();  
            } catch (Exception e) {
                System.err.println("Unable to Save Items");
                System.exit(0);
            }
        }
        
        /*
         * Loads the items.
         * 
         * @param filename Filename to load from
         * @return Array Items
         */
        public Item[] loadItems(String filename) {
            String[][] items = null;
            try {
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream iis = new ObjectInputStream(fis);
		items = (String[][]) iis.readObject();
                iis.close();
                fis.close();

            } catch (Exception e) {
                System.err.println("Unable to Load Items");
                System.exit(0);
            }
            
            return itemsToArray(items);
        }
        
        /*
         * Returns an Item[] items. built from String[] of paramters
         * 
         * 
         * @param data String[] containing parameters for item class
         * itemsData[7] = new String[] { "bars", "238","275","32","32","8","10" };
         * 
         * @return Item[]
         * new Item("name",1,1,1,1,1) ;
         */
        public Item[] itemsToArray(String[][] data)
        {
            items = new Item[data.length];
            String name = ""; 
            int anchorX, anchorY, width, height, x, y = 0;
            for (int i=0; i<data.length; i++) 
            {
                name = data[i][0];
                anchorX = Integer.parseInt(data[i][1]);
                anchorY = Integer.parseInt(data[i][2]);
                width = Integer.parseInt(data[i][3]);
                height = Integer.parseInt(data[i][4]);
                x = Integer.parseInt(data[i][5]);
                y = Integer.parseInt(data[i][6]);
                items[i] = new Item(name, anchorX, anchorY, width, height, x, y);
            }
            return items;
        }
        
         /*
         * Saves the Entitys
         * extras[0] = new Entity("kristie", this, 14f, 3f, 34, 46, 9);
         * extras[1] = new Entity("daniel", this, 16f, 3f, 34, 46, 9);
         *   
         * String[][] itemsData = new String[2][6];
         * itemsData[0] = new String[] { "kristie", "14f", "3f", "34", "46", "9" };
         * itemsData[1] = new String[] { "daniel", "16f", "3f", "34", "46", "9" };
         * 
         * @param entities Array of Entities
         * @param filename Filename to save To
         */
        public void saveEntities(String filename) {
            String[][] data = new String[extras.length][7];
            for (int i=0;i<extras.length;i++) data[i] = extras[i].entityToString();
            
            try {
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(data);
                oos.close();
                fos.close();  
            } catch (Exception e) {
                System.err.println("Unable to Save Entities");
                System.exit(0);
            }
        }
        
        /*
         * Loads the entities.
         * 
         * @param filename Filename to load from
         * @return Array Entity
         */
        public Entity[] loadEntities(String filename) {
            String[][] entities = null;
            try {
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream iis = new ObjectInputStream(fis);
		entities = (String[][]) iis.readObject();
                iis.close();
                fis.close();

            } catch (Exception e) {
                System.err.println("Unable to Load Entities");
                System.exit(0);
            }
            
            return entitiesToArray(entities);
        }
        
        /*
         * Returns an Item[] items. built from String[] of paramters
         * 
         * 
         * @param data String[] containing parameters for item class
         * itemsData[7] = new String[] { "bars", "238","275","32","32","8","10" };
         * 
         * @return Item[]
         * new Item("name",1,1,1,1,1) ;
         */
        public Entity[] entitiesToArray(String[][] data)
        {
            extras = new Entity[data.length];
            String name = ""; 
            float x, y = 0;
            int width, height, currentFrame = 0;
            for (int i=0; i<data.length; i++) 
            {
                name = data[i][0];
                x = Float.parseFloat(data[i][1]);
                y = Float.parseFloat(data[i][2]);
                width = Integer.parseInt(data[i][3]);
                height = Integer.parseInt(data[i][4]);
                currentFrame = Integer.parseInt(data[i][5]);
                extras[i] = new Entity(name, this, x, y, width, height,currentFrame);
            }
            return extras;
        }
}

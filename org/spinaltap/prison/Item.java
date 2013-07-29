/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spinaltap.prison;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import static org.spinaltap.prison.Map.TILE_SIZE;

/**
 *
 * @author Tyler
 */
public class Item {
    private String name;
    private int x; // Initial X Position
    private int y; // Initial Y Position
    private BufferedImage image; // Sprite Sheet
    private Map map; // Map pertaining to character

    private int width; // Width of character's single sprite
    private int height; // Height of character's single sprite
    
    public Item (String name, int anchorx, int anchory, int width, int height, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        try { // Get Sprite Sheet
                URL url = Thread.currentThread().getContextClassLoader().getResource("res/items.png");
                if (url == null) {
                        System.err.println("Unable to find sprite: res/items.png");
                        System.exit(0);
                }
                BufferedImage tileset = ImageIO.read(url);
                image = tileset.getSubimage(anchorx,anchory,width,height);
        } catch (IOException e) {
                System.err.println("Unable to load sprite: res/items.png");
                System.exit(0);
        }
    }
            
    public void paint(Graphics2D g) {
        g.drawImage(image, null, x*TILE_SIZE, y*TILE_SIZE);
    }
    
}

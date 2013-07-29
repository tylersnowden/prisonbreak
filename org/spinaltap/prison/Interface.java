package org.spinaltap.prison;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import static org.spinaltap.prison.Map.TILE_SIZE;

/**
 * The User Interface which lets the user make choices and communicate.
 * 
 * @author Tyler
 */
public class Interface {
    private int WIDTH;
    private int HEIGHT;
    
    public Interface(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
    }
    
   /**
    * Render the map. Loop through blocks and render.
    * 
    * @param g Graphics Context
    */
   public void paint(Graphics2D g) {

        g.setColor(Color.darkGray); // Default NULL Tile
        g.fillRect(0,480,WIDTH,HEIGHT);
        g.setColor(Color.white);
        Font f = new Font("Monospaced", Font.BOLD, 12);
        g.setFont(f);
        g.drawString("Interface", 15, 500);

   }
}

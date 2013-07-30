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
    private int width;
    private int height;
    private Content[] paragraph;
    private Button[] buttons;
    
    public Interface(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
   /**
    * Render the Interface. Paint Content and Buttons
    * 
    * @param g Graphics Context
    */
   public void paint(Graphics2D g) {

        g.setColor(Color.darkGray); // Default NULL Tile
        g.fillRect(0,480,width,height);
        g.setColor(Color.white);
        Font f = new Font("Monospaced", Font.BOLD, 15);
        g.setFont(f);
        g.drawString("Welcome to PrisonBreak 1.0", 15, 500);
        f = new Font("Monospaced", Font.BOLD, 15);
        g.setFont(f);
        g.drawString("Start New Game", 35, 520);
        g.drawString("Quit", 35, 540);
        
        // TODO: Loop painting all content and buttons

   }
   
   public void hearClick(int x, int y)
   {
       // Loop Buttons for Valid Click
   }
   
}

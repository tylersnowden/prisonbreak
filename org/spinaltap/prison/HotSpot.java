/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spinaltap.prison;

import java.awt.Rectangle;

/**
 *
 * @author Tyler
 */
public class HotSpot {
    private int x;
    private int y;
    private String key; // What type of action
    private String value; // Action
    public Rectangle area;
    public Level current;
    
    public HotSpot(int x, int y, String key, String value, Level current)
    {
        this.x = x;
        this.y = y;
        this.area = new Rectangle(x,y,32,32);
        this.key = key;
        this.value = value;
        this.current = current;
    }
    
    public void perform()
    {
        
        if ("move".equals(key)) {
            current.loadLevel(value);
        }
    }
    
}

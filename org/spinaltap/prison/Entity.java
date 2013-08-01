package org.spinaltap.prison;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Rectangle;

/**
 * A single Character that moves and animates via sprites.
 * 
 * @author Tyler Snowden
 */
public class Entity {
        private String name;
        public SpriteBatch batch;
	
	
	/**
	 * Create a new entity in the game. 
	 * 
	 * @param name Used to name character
	 */
	public Entity(String name) {
                
                this.name = name;
                batch = new SpriteBatch();
                
                Rectangle bucket;
		bucket = new Rectangle();
                bucket.x = 800 / 2 - 64 / 2;
                bucket.y = 20;
                bucket.width = 64;
                bucket.height = 64;
	}
               
}

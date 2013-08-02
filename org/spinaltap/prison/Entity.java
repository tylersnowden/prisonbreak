package org.spinaltap.prison;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.awt.Rectangle;

/**
 * A single Character that moves and animates via sprites.
 * 
 * @author Tyler Snowden
 */
public class Entity {
        private String name;
        private SpriteBatch batch;
        private Rectangle shape; // The 'physical' body
        private Texture image;
	private static int FRAME_COLS;         
        private static int FRAME_ROWS;         
        
        Animation upAnim, downAnim, leftAnim, rightAnim;          
        TextureRegion[] up, down, left, right;             
        TextureRegion currentFrame;           
        
        float stateTime;             
	
	/**
	 * Create a new entity in the game. 
	 * 
	 * @param name Used to name character
	 */
	public Entity(String name, int cols, int rows, int width, int height, int x, int y) {
                
            this.name = name;
            FRAME_COLS = cols;
            FRAME_ROWS = rows;

            batch = new SpriteBatch();
                
            image = new Texture(Gdx.files.internal("res/"+name+".png"));
            shape = new Rectangle();
            shape.x = x;
            shape.y = y;
            shape.width = width;
            shape.height = width;
            
            // TODO: Make a file that specifys texture and animation info
            
            TextureRegion[][] tmp = TextureRegion.split(image, 34, 46);
            up = new TextureRegion[FRAME_COLS];
            down = new TextureRegion[FRAME_COLS];
            left = new TextureRegion[FRAME_COLS];
            right = new TextureRegion[FRAME_COLS];
            int index = 0;
            for (int j = 0; j < FRAME_COLS; j++) {
                    up[j] = tmp[0][j];
            }
            for (int j = 0; j < FRAME_COLS; j++) {
                    right[j] = tmp[1][j];
            }
            for (int j = 0; j < FRAME_COLS; j++) {
                    down[j] = tmp[2][j];
            }
            for (int j = 0; j < FRAME_COLS; j++) {
                    left[j] = tmp[3][j];
            }
            upAnim = new Animation(.15f, up);
            downAnim = new Animation(.15f, down);
            leftAnim = new Animation(.15f, left);
            rightAnim = new Animation(.15f, right);
            stateTime = 0f;              
	}
        
        public void update(int x, int y) {
            stateTime += Gdx.graphics.getDeltaTime();  
            
            if ((x == 0) && (y == 0)) currentFrame = down[1];      
            else {
                if (x > shape.x) currentFrame = rightAnim.getKeyFrame(stateTime, true); 
                else if (x < shape.x) currentFrame = leftAnim.getKeyFrame(stateTime, true); 
                else if (y > shape.y) currentFrame = upAnim.getKeyFrame(stateTime, true); 
                else currentFrame = downAnim.getKeyFrame(stateTime, true);
            }
            
            shape.x += x;
            shape.y += y;
            
            if(shape.x < 0) shape.x = 0;
            if(shape.x > 800 - 64) shape.x = 800 - 64;
            
            batch.begin();
            batch.draw(currentFrame, shape.x, shape.y); 
            batch.end();
            
        }
        
        public void dispose()
        {
            image.dispose();
        }
               
}

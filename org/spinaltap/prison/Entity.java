package org.spinaltap.prison;

import com.badlogic.gdx.Gdx;
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
        public Rectangle shape; // The 'physical' body
        private Texture image;
	private static int FRAME_COLS;         
        private static int FRAME_ROWS;
        
        Animation upAnim, downAnim, leftAnim, rightAnim;          
        TextureRegion[] up, down, left, right;             
        TextureRegion currentFrame;
        
        private int direction = 0;
        private final int UP_IDLE = 0;
        private final int DOWN_IDLE = 1;
        private final int LEFT_IDLE = 2;
        private final int RIGHT_IDLE = 3;
        
        float stateTime;             
	
	/**
	 * Create a new entity in the game. 
	 * 
	 * @param name Used to name character
	 */
	public Entity(String name, int cols, int rows, int width, int height, int x, int y, int direction) {
                
            this.name = name;
            FRAME_COLS = cols;
            FRAME_ROWS = rows;

            batch = new SpriteBatch();
                
            image = new Texture(Gdx.files.internal("res/"+name+".png"));
            shape = new Rectangle();
            shape.x = x;
            shape.y = y;
            shape.width = width;
            shape.height = height;
            
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
            
            this.direction = direction;
            switch (direction) {
                case RIGHT_IDLE: currentFrame = right[1]; break;
                case LEFT_IDLE: currentFrame = left[1]; break;
                case UP_IDLE: currentFrame = up[1]; break;
                case DOWN_IDLE:
                default: currentFrame = down[1]; break;
            }
	}
        
        public void update(Map map, Entity[] ai, int x, int y) {
            stateTime += Gdx.graphics.getDeltaTime();  
            
            if ((x == 0) && (y == 0)) {
                switch (direction) {
                    case RIGHT_IDLE: currentFrame = right[1]; break;
                    case LEFT_IDLE: currentFrame = left[1]; break;
                    case UP_IDLE: currentFrame = up[1]; break;
                    case DOWN_IDLE:
                    default: currentFrame = down[1]; break;
                }
                
            }      
            else {
                if (x > 0) { currentFrame = rightAnim.getKeyFrame(stateTime, true); direction = RIGHT_IDLE; } 
                else if (x < 0) { currentFrame = leftAnim.getKeyFrame(stateTime, true); direction = LEFT_IDLE; }
                else if (y > 0) { currentFrame = upAnim.getKeyFrame(stateTime, true); direction = UP_IDLE; }
                else if (y < 0) { currentFrame = downAnim.getKeyFrame(stateTime, true); direction = DOWN_IDLE; }
            }
            
            shape.x += x;
            shape.y += y;
            boolean moved = true;
            
            if (map.overlap(shape)) {
                shape.x -= x;
                shape.y -= y;
                moved = false;
            }
            
            for (Entity target: ai) {
                if (shape.intersects(target.shape)) {
                    shape.x -= x;
                    shape.y -= y;
                    moved = false;
                }   
            }
            
            if (moved) {
                if(shape.x < 100) map.camera.translate(x, y);
                if(shape.x > 700) map.camera.translate(x, y);

                if(shape.y < 100) map.camera.translate(x, y);
                if(shape.y > 500) map.camera.translate(x, y);
            }
            
            render(map);
            
        }
        
        public void render(Map map)
        {
            batch.setProjectionMatrix(map.camera.combined);
            batch.begin();
            batch.draw(currentFrame, shape.x, shape.y); 
            batch.end();
        }
        
        public void dispose()
        {
            image.dispose();
        }
        
        public Rectangle actionArea()
        {
            Rectangle actionArea = new Rectangle();
            switch (direction) {
                case RIGHT_IDLE: 
                    actionArea.setRect(shape.x+shape.width, shape.y, 32, 32);
                    break;
                case LEFT_IDLE:
                    actionArea.setRect(shape.x-shape.width, shape.y, 32, 32);
                    break;
                case UP_IDLE:  
                    actionArea.setRect(shape.x, shape.y+shape.height, 32, 32);
                    break;
                case DOWN_IDLE:
                default:   
                    actionArea.setRect(shape.x, shape.y-shape.height, 32, 32);
                    break;
            }
            return actionArea;
        }
               
}

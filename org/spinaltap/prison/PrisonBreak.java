package org.spinaltap.prison;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Rectangle;

/**
 * Prison Break Game
 * 
 * @author Tyler Snowden
 */
public class PrisonBreak implements ApplicationListener {
    
    private Entity hero;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Rectangle bucket;
    private Texture bucketImage;
    
        public void create () {
            
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);
            
            hero = new Entity("Tyler");
            batch = new SpriteBatch();
                
            bucketImage = new Texture(Gdx.files.internal("res/bucket.png"));
            bucket = new Rectangle();
            bucket.x = 800 / 2 - 64 / 2;
            bucket.y = 20;
            bucket.width = 64;
            bucket.height = 64;
        }

        public void render () {
            
            if(Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
            if(Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
            
            if(bucket.x < 0) bucket.x = 0;
            if(bucket.x > 800 - 64) bucket.x = 800 - 64;
            
            Gdx.gl.glClearColor(0, 0, 0.2f, 1);
            Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            
            camera.update();
            
            hero.batch.setProjectionMatrix(camera.combined);
            hero.batch.begin();
            hero.batch.draw(bucketImage, bucket.x, bucket.y);
            hero.batch.end();
        }

        public void resize (int width, int height) {
        }

        public void pause () {
        }

        public void resume () {
        }

        public void dispose () {
            bucketImage.dispose();
        }
}

package org.spinaltap.prison;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Prison Break Game
 * 
 * @author Tyler Snowden
 */
public class PrisonBreak implements ApplicationListener {
    
    private Entity hero;
    private OrthographicCamera camera;
    private Map map;
    
        public void create () {
           
            map = new Map(800, 600);
            hero = new Entity("Tyler", 4, 4, 32, 46, 50, 50);
        }

        public void render () {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            int x = 0;
            int y = 0;
            
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= 150 * Gdx.graphics.getDeltaTime();
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += 150 * Gdx.graphics.getDeltaTime();
            if(Gdx.input.isKeyPressed(Input.Keys.UP)) y += 150 * Gdx.graphics.getDeltaTime();
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) y -= 150 * Gdx.graphics.getDeltaTime();
            
            map.render();
            hero.update(map,x,y); 
            
            
            
        }

        public void resize (int width, int height) {
        }

        public void pause () {
        }

        public void resume () {
        }

        public void dispose () {
            hero.dispose();
            map.dispose();
        }
}

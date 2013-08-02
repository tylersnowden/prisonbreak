/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spinaltap.prison;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level implements Screen
{
        private SpriteBatch spriteBatch;
        private Texture splash;
        private PrisonBreak myGame;
        private Entity hero;
        private OrthographicCamera camera;
        private Map map;
        private Entity[] ai = new Entity[2];
        
        
        /**
         * Constructor for the splash screen
         * @param g Game which called this splash screen.
         */
        public Level(PrisonBreak g)
        {
                myGame = g;
                map = new Map(myGame.width, myGame.height);
                hero = new Entity("Tyler", 4, 4, 32, 46, 70, 400);
                ai[0] = new Entity("Daniel", 4, 4, 32, 46, 500, 400);
                ai[1] = new Entity("Kristie", 4, 4, 32, 46, 70, 50);
        }

        @Override
        public void render(float delta)
        {
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
            for(Entity user: ai) {
                user.render(map);
            }    
        }
        
        @Override
        public void show()
        {
                
        }

        @Override
        public void resize(int i, int i1) {
            
        }

        @Override
        public void hide() {
         
        }

        @Override
        public void pause() {
           
        }

        @Override
        public void resume() {
            
        }

        @Override
        public void dispose() {
            hero.dispose();
            map.dispose();
        }
}
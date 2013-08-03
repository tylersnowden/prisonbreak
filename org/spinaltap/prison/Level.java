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
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Level implements Screen
{
        private SpriteBatch spriteBatch;
        private Texture splash;
        private PrisonBreak myGame;
        private Entity hero;
        private Map map;
        private Entity[] ai = new Entity[2];
        private Ui stage;
        
        /**
         * Constructor for the splash screen
         * @param g Game which called this splash screen.
         */
        public Level(PrisonBreak g)
        {
                myGame = g;
                stage = new Ui();
                stage.setContent("You wake up in a cell. You vaguely remember what happened last night. What did you do this time? \nI guess you'll find out soon.");
                Gdx.input.setInputProcessor(stage);
                
                map = new Map(myGame.width, myGame.height);
                hero = new Entity("Tyler", 4, 4, 30, 40, 70, 400,1);
                ai[0] = new Entity("Daniel", 4, 4, 30, 40, 500, 400,1);
                ai[1] = new Entity("Kristie", 4, 4, 30, 40, 70, 50,3);
        }

        @Override
        public void render(float delta)
        {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            int x = 0;
            int y = 0;
            
            if(Gdx.input.isKeyPressed(Input.Keys.A)) x -= 150 * Gdx.graphics.getDeltaTime();
            if(Gdx.input.isKeyPressed(Input.Keys.D)) x += 150 * Gdx.graphics.getDeltaTime();
            if(Gdx.input.isKeyPressed(Input.Keys.W)) y += 150 * Gdx.graphics.getDeltaTime();
            if(Gdx.input.isKeyPressed(Input.Keys.S)) y -= 150 * Gdx.graphics.getDeltaTime();
            
            if(Gdx.input.isKeyPressed(Input.Keys.E)) {
                for (Entity target: ai) {
                    if (hero.actionArea().intersects(target.shape)) System.out.println("Hit");    
                }
            }
            
            map.render();
            hero.update(map,ai,x,y); 
            
            for(Entity user: ai) {
                user.render(map);
            }    
            stage.render();
        }
        
        @Override
        public void show()
        {
                
        }

        @Override
        public void resize(int i, int i1) {
            stage.resize(i,i1);
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
            stage.dispose();
        }
}
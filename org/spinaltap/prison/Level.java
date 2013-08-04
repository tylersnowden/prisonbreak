/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spinaltap.prison;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Level implements Screen
{
        private SpriteBatch spriteBatch;
        private Texture splash;
        private PrisonBreak myGame;
        private Entity hero;
        private Map map;
        private ArrayList<Entity> ai = new ArrayList<Entity>();
        private Ui hud;
        
        /**
         * Constructor for the splash screen
         * @param g Game which called this splash screen.
         */
        public Level(PrisonBreak g, String level)
        {
                myGame = g;
                myGame = g;
                hud = new Ui();
                Gdx.input.setInputProcessor(hud);
                
                loadLevel(level);
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
                    if (hero.actionArea().intersects(target.shape)) hud.speak(target.actions.get(0));    
                }
            }
            
            map.render();
            hero.update(map,ai,x,y); 
            
            for(Entity user: ai) {
                user.render(map);
            }    
            hud.render();
        }
        
        @Override
        public void show()
        {
                
        }

        @Override
        public void resize(int i, int i1) {
            hud.resize(i,i1);
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
            hud.dispose();
        }
        
        public void loadLevel(String level) {
            map = new Map(myGame.width, myGame.height,level);
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader("areas/"+level+"/info.json"));
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject init = (JSONObject) jsonObject.get("init");

                hud.speak(new Action(hud.skin, (String) init.get("ui")));
                hero = loadEntity((JSONObject) jsonObject.get("hero"));
                
                JSONArray pawn = (JSONArray) jsonObject.get("ai");
		Iterator<JSONObject> iterator = pawn.iterator();
		while (iterator.hasNext()) {
                        ai.add(loadAi(iterator.next()));
		}

                //JSONObject pawn = json.getJSONObject("ai");
            } catch (FileNotFoundException e) {
            } catch (    IOException | ParseException e) {}
        
        }
        
        public Entity loadEntity(JSONObject person) {
            String name = (String) person.get("name");
            long cols = (long) person.get("cols");
            long rows = (long) person.get("rows");
            long width = (long) person.get("width");
            long height = (long) person.get("height");
            long x = (long) person.get("x");
            long y = (long) person.get("y");
            long direction = (long) person.get("direction");
            
            return new Entity(name, (int) cols, (int) rows, (int) width, (int) height, (int) x, (int) y, (int) direction);
        }
        
        public Entity loadAi(JSONObject person) {
            Entity tmp = loadEntity(person);
            JSONArray actions = (JSONArray) person.get("action");
            Iterator<String> iterator = actions.iterator();
            while (iterator.hasNext()) {
                    tmp.actions.add(new Action(hud.skin, iterator.next()));
            }
            return tmp;
        }
}
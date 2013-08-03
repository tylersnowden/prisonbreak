package org.spinaltap.prison;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Prison Break Game
 * 
 * @author Tyler Snowden
 */
public class PrisonBreak extends Game {
    
    SplashScreen splash;
    Level level1;
    
    int width = 800;
    int height = 800;
    
        public void create () {
            
            splash = new SplashScreen(this);
            level1 = new Level(this);
            setScreen(splash);
        }

        public void render () {
            getScreen().render(Gdx.graphics.getDeltaTime());
        }

        public void resize (int width, int height) {
        }

        public void pause () {
        }

        public void resume () {
        }

        public void dispose () {
            
        }
}

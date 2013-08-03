/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spinaltap.prison;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen
{
        private SpriteBatch spriteBatch;
        private Texture splash;
        private PrisonBreak myGame;
        private final TweenManager tweenManager = new TweenManager();
        private Sprite splashSprite;
        
        /**
         * Constructor for the splash screen
         * @param g Game which called this splash screen.
         */
        public SplashScreen(PrisonBreak g)
        {
                myGame = g;
                
        }

        @Override
        public void render(float delta)
        {
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                spriteBatch.begin();
                spriteBatch.draw(splashSprite, 144, 44);
                spriteBatch.end();
                
                //Tween.to(splashSprite, SpriteAccessor.OPACITY, 1.0f).target(0).start(tweenManager);
                if(Gdx.input.justTouched()) myGame.setScreen(myGame.level1);
        }
        
        @Override
        public void show()
        {
                spriteBatch = new SpriteBatch();
                splash = new Texture(Gdx.files.internal("res/start.png"));
                splashSprite = new Sprite(splash);
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

        }
}
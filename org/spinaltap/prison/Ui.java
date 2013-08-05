/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spinaltap.prison;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Tyler
 */
public class Ui extends Stage {
    Skin skin;
    SpriteBatch batch;
    private final Texture bg;
    private final Sprite bgSprite;
    private final SpriteBatch bgBatch;
    private Table table;
    
    private Timer timer = new Timer();
    
    public Ui() {
        bgBatch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("res/bg.png"));
        bgSprite = new Sprite(bg);
        Gdx.input.setInputProcessor(this);

        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        textButtonStyle.fontColor = Color.LIGHT_GRAY;
        skin.add("default", textButtonStyle);
        
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

        table = new Table();
        table.top().left();
        table.setHeight(90);
        table.setWidth(650);
        table.setPosition(130,5);
        table.setSkin(skin);
        //table.debugTable();
        addActor(table);
        
    }
    
    public void render () {
            bgBatch.begin();
            bgBatch.draw(bgSprite, 0, 0);
            bgBatch.end();
            act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            draw();
            //table.drawDebug(this);
    }

    public void resize (int width, int height) {
            setViewport(width, height, false);
    }

    public void dispose () {
            dispose();
            skin.dispose();
    }
    
    public void choice(Action event)
    {
        table.debug();
        table.clearChildren();
        table.row();
        table.add(event.content);
        table.row();
        for(TextButton act: event.actions) table.add(act);
        
        table.layout();
        
    }
    
    public void speak(Action event) {
        table.clear();
        table.add(event.content);
        timer.schedule(new TimerTask() {  
            @Override  
            public void run() {  
                table.clear();
            }  
        }, 10000);  
    }
    
}

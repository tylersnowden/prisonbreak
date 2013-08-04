/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spinaltap.prison;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import java.util.ArrayList;

/**
 *
 * @author Tyler
 */
public class Action {
    public Label content;
    private Skin skin;
    public ArrayList<TextButton> actions;
    
    public Action(Skin skin,String phrase)
    {
        this.skin = skin;
        this.actions = new ArrayList<TextButton>();
        content = new Label(phrase, skin);
    }
    
    public void addButton(String name) {
        actions.add(new TextButton(name, skin));
    }
    
}

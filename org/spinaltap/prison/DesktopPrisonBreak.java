package org.spinaltap.prison;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.spinaltap.prison.PrisonBreak;

public class DesktopPrisonBreak {
        public static void main (String[] args) {
            
            LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
            cfg.title = "MyGame";
            cfg.useGL20 = false;
            cfg.width = 480;
            cfg.height = 320;
            
            new LwjglApplication(new PrisonBreak(), cfg);
        }
}
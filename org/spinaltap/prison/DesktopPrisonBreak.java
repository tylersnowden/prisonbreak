package org.spinaltap.prison;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopPrisonBreak {
        public static void main (String[] args) {
            
            LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
            cfg.title = "Prison Break";
            cfg.useGL20 = false;
            cfg.width = 800;
            cfg.height = 600;
            
            new LwjglApplication(new PrisonBreak(), cfg);
        }
}
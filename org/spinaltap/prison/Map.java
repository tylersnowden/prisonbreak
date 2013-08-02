package org.spinaltap.prison;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import java.awt.Rectangle;

/**
 * Map consists of tiles with varying states.
 * 
 * @author Tyler Snowden
 */
public class Map {
    public TiledMap map;
    public final OrthogonalTiledMapRenderer renderer;
    public OrthographicCamera camera;
    private float marginX;
    private float marginY;
    
	/**
	 * Create a new map. Load Map from File. Load tileset.
	 */
	public Map(int width, int height) {
            map = new TmxMapLoader().load("level1/office.tmx");
            renderer = new OrthogonalTiledMapRenderer(map);
            
            TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
            marginX = ((layer.getWidth()*32) - width) / 2;
            marginY = ((layer.getHeight()*32) - height) / 2;
            
            camera = new OrthographicCamera();
            camera.setToOrtho(false, width, height);
            camera.translate(marginX, marginY);
            camera.update();
        }
        
        public void render() 
        {
            renderer.setView(camera);
            renderer.render();
            camera.update();
        }
        
        public void dispose()
        {
            map.dispose();
        }
        
        public boolean overlap(Rectangle person)
        {
            TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(2);

            int col = Math.round(person.x / 32);
            int row = Math.round(person.y / 32);
            if(layer.getCell(col, row) != null) return true;
            
            col = (int) Math.ceil((person.x + person.width) / 32);
            row = (int) Math.ceil((person.y + person.height) / 32);
            if(layer.getCell(col, row) != null) return true;
            
            col = (int) Math.round(person.x / 32);
            row = (int) Math.ceil((person.y + person.height) / 32);
            if(layer.getCell(col, row) != null) return true;
            
            col = (int) Math.ceil((person.x + person.width) / 32);
            row = (int) Math.round(person.y / 32);
            if(layer.getCell(col, row) != null) return true;
            
            return false;
        }
        
}

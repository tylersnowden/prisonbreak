package org.spinaltap.prison;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import java.awt.Rectangle;

/**
 * Map consists of tiles with varying states.
 * 
 * @author Tyler Snowden
 */
public class Map {
    TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    
	/**
	 * Create a new map. Load Map from File. Load tileset.
	 */
	public Map() {
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 600);
            camera.update();
            
            map = new TmxMapLoader().load("level1/office.tmx");
            renderer = new OrthogonalTiledMapRenderer(map);
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
            Cell cell = layer.getCell(col, row);
            if(cell != null) return true;
            
            col = (int) Math.ceil((person.x + person.width) / 32);
            row = (int) Math.ceil((person.y + person.height) / 32);
            cell = layer.getCell(col, row);
            if(cell != null) return true;
            
            col = (int) Math.round(person.x / 32);
            row = (int) Math.ceil((person.y + person.height) / 32);
            cell = layer.getCell(col, row);
            if(cell != null) return true;
            
            col = (int) Math.ceil((person.x + person.width) / 32);
            row = (int) Math.round(person.y / 32);
            cell = layer.getCell(col, row);
            if(cell != null) return true;
            
            return false;
        }
        
}

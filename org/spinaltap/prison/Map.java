package org.spinaltap.prison;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.awt.Rectangle;

/**
 * Map consists of tiles with varying states.
 * 
 * @author Tyler Snowden
 */
public class Map extends Stage {
    public TiledMap map;
    public final OrthogonalTiledMapRenderer renderer;
    public OrthographicCamera camera;
    private float marginX;
    private float marginY;
    
	/**
	 * Create a new map. Load Map from File. Load tileset.
	 */
	public Map(int width, int height, String level) {
            map = new TmxMapLoader().load("areas/"+level+"/map.tmx");
            renderer = new OrthogonalTiledMapRenderer(map);
            
            TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
            
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 600);
            camera.translate(-300, 100);
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
            TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(3);

            int col = Math.round(person.x / 32);
            int row = Math.round(person.y / 32);
            if(layer.getCell(col, row) != null) return true;
            
            col = (int) Math.ceil((person.x + person.width) / 32);
            row = (int) Math.ceil((person.y + person.height) / 32);
            if(layer.getCell(col, row) != null) return true;
            
            col = (int) Math.ceil((person.x + person.width) / 32);
            row = (int) Math.ceil((person.y + (person.height / 2)) / 32);
            if(layer.getCell(col, row) != null) return true;
            
            col = (int) Math.round(person.x / 32);
            row = (int) Math.ceil((person.y + person.height) / 32);
            if(layer.getCell(col, row) != null) return true;
            
            col = (int) Math.round(person.x / 32);
            row = (int) Math.ceil((person.y + (person.height / 2)) / 32);
            if(layer.getCell(col, row) != null) return true;
            
            col = (int) Math.ceil((person.x + person.width) / 32);
            row = (int) Math.round(person.y / 32);
            if(layer.getCell(col, row) != null) return true;
            
            return false;
        }
        
}

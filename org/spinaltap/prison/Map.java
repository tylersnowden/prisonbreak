package org.spinaltap.prison;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

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
       
}

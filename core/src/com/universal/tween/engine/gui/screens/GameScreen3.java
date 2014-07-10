package com.universal.tween.engine.gui.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.universal.tween.engine.gui.ScreenType;
import com.universal.tween.engine.helpers.Objects;
import com.universal.tween.engine.objects.Player;
import com.universal.tween.engine.objects.Tool;
import com.universal.tween.engine.resources.Config;
import com.universal.tween.engine.resources.GameInstance;

public class GameScreen3 extends DefaultScreen implements InputProcessor {
	
	private SpriteBatch batch = null;
	private OrthographicCamera camera = null;
	private Objects objects;
	
	private static final String DESC = "LABIRINT RUN";
	private BitmapFont font = null; 
	private float captionX1 = 0;
	private float captionY = 0;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	private Player currentPlayer;
	
	private boolean isPlayerAlive = false;
	
	public GameScreen3() {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Game screen 3: init");
		setBatch(new SpriteBatch());
		setObjects(new Objects());
		setFont(new BitmapFont());
		setCamera(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
//		getCamera().position.set(Gdx.graphics.getWidth() / 2,  Gdx.graphics.getHeight() / 2, 0);
		
//		getCamera().setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		getCamera().update();
		
		captionX1 = 50;
		captionY = 50;
		
		map = new TmxMapLoader().load(Config.DATA_MAPS_MAP);
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
//		TiledMapTileSet tileset =  map.getTileSets().getTileSet("house");
//		Map<String,TiledMapTile> roadTiles = new HashMap<String,TiledMapTile>();
//        for(TiledMapTile tile:tileset){
//             Object property = tile.getProperties().get("isroad");
//            if(property != null)
//                roadTiles.put((String)property,tile);
//        }
		
        ArrayList<TiledMapTileLayer.Cell> roadCellsInScene = new ArrayList<TiledMapTileLayer.Cell>();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("main");
        for(int x = 0; x < layer.getWidth();x++){
            for(int y = 0; y < layer.getHeight();y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                Object property = cell.getTile().getProperties().get("isroad");
                if(property != null && ((String) property).equals("true")){
                    roadCellsInScene.add(cell);
                    System.out.println(x+" : "+y);
                    
//        			Tool tool = getObjects().getToolsPool().obtain();
//        	        tool.init(x*32, y*32, 65, 65);
//
//        	        getObjects().getActiveTools().add(tool);
                }
            }
        }
        System.out.println(roadCellsInScene.size());
	}

	@Override
	public void render(float delta) {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		GameInstance.getInstance().switchScreens(ScreenType.GAME3);
		
		if (Gdx.input.isTouched()) {
			spawnPlayer();
		}
		
		getCamera().update();
//		getBatch().setProjectionMatrix(getCamera().combined);

		mapRenderer.setView(getCamera());
		mapRenderer.render();
		
		getObjects().update();
		
		
		getBatch().begin();
		
		font.setColor(0f, 0.5f, 1f, 1f);
		font.draw(batch, DESC, captionX1, captionY);
		
		getObjects().render(getBatch(), delta);
		
        getBatch().end();
	}
	
	private void spawnPlayer() {
		if (TimeUtils.nanoTime() - getObjects().getLastPlayerTime() <= 500000000) return;
		
		if (isPlayerAlive()) {
			return;
//			getCurrentPlayer().setAlive(false);
		};
		setPlayerAlive(true);
		
//		Vector3 coordinates = new Vector3(Gdx.graphics.getWidth()/2-64/2, Gdx.graphics.getHeight()/2-64/2, 0);
		Vector3 coordinates = new Vector3(225, 490, 0);
	    Vector3 position = camera.unproject(coordinates);
		
		Player player = getObjects().getPlayersPool().obtain();
//		player.init(position.x, position.y, 64, 64);
        player.init(225, 490, 64, 64);
        
        player.setSpeed(200);
//        getCamera().position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        getCamera().position.set(225, 490, 0);
//        camera.translate(position.x, position.y);
        
        setCurrentPlayer(player);
    	
        getObjects().getActivePlayers().add(player);

        getObjects().setLastPlayerTime(TimeUtils.nanoTime());
	}
	
	@Override
	public void resize(int width, int height) {
//		setCamera(new OrthographicCamera());
		getCamera().setToOrtho(false, width, height);
		getCamera().update();
//		getBatch().setProjectionMatrix(getCamera().combined);
		camera.zoom = 5;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
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
		getBatch().dispose();
		getObjects().dispose();
		map.dispose();
		mapRenderer.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public Objects getObjects() {
		return objects;
	}

	public void setObjects(Objects objects) {
		this.objects = objects;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	@Override
	public boolean keyDown(int keycode) {
        return false;
	}

	@Override
	public boolean keyUp(int keycode) {
//  if(keycode == Input.Keys.NUM_1)
//      map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible());
//  if(keycode == Input.Keys.NUM_2)
//  	map.getLayers().get(1).setVisible(!map.getLayers().get(1).isVisible());
        return false;
	}

	@Override
	public boolean keyTyped(char character) {
		if (!isPlayerAlive() || getCurrentPlayer() == null) return false;
		
		switch(character) {
		case 'w': //up
			getCurrentPlayer().moveUp();
			camera.translate(0, getCurrentPlayer().getSpeed() * Gdx.graphics.getDeltaTime());
			break;
		case 's': //down
			getCurrentPlayer().moveDown();
			camera.translate(0, -getCurrentPlayer().getSpeed() * Gdx.graphics.getDeltaTime());
			break;
		case 'a': //left
			getCurrentPlayer().moveLeft();
			camera.translate(-getCurrentPlayer().getSpeed() * Gdx.graphics.getDeltaTime(), 0); 
			break;
		case 'd': //right
			getCurrentPlayer().moveRight();
			camera.translate(getCurrentPlayer().getSpeed() * Gdx.graphics.getDeltaTime(), 0);
			break;
		}
		
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("main");
		
		Vector3 cameraCoordinates = camera.unproject(new Vector3(getCurrentPlayer().getBounds().x, getCurrentPlayer().getBounds().y, 0));
		
		int cellX = (int) Math.ceil( (getCurrentPlayer().getBounds().x) / layer.getWidth());
		int cellY = (int) Math.ceil( ((layer.getHeight()*layer.getTileHeight()) - (getCurrentPlayer().getBounds().y+getCurrentPlayer().getBounds().height)) / layer.getTileHeight());
		
		
        for(int x = 0; x < layer.getWidth();x++){
            for(int y = 0; y < layer.getHeight();y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                Object property = cell.getTile().getProperties().get("isroad");
                if(x == cellX && y == cellY && property != null && ((String) property).equals("true")){
                    System.out.println(x+" : "+y);
                    
//        			Tool tool = getObjects().getToolsPool().obtain();
//        	        tool.init(x*32, y*32, 65, 65);
//
//        	        getObjects().getActiveTools().add(tool);
                }
            }
        }
		
		
//		int cellY = (int) (layer.getHeight() - 1 - cellX);
		
//		int cellX = 2+1;
//		int cellY = 1+1;
		
//		TiledMapTileLayer.Cell cell = layer.getCell(cellX, cellY);
//        if (cell != null) { // There is cell
//        	if (cell.getTile() != null) { // tile inside cell
////        		boolean type = (Boolean) map.getTileSets().getTile(cell.getTile().getId()).getProperties().get("road");
////        		MapProperties properties = cell.getTile().getProperties();
//        		Object property = cell.getTile().getProperties().get("isroad");
//        		String isRoad = "false";
//        		if (property != null) isRoad = (String) property;
//        		if (isRoad.equals("true")) {
//        			
//        		}
//        		System.out.println(cellX+":"+cellY+", "+getCurrentPlayer().getBounds().x+":"+getCurrentPlayer().getBounds().y+", "+camera.position.x+":"+camera.position.y+", road:" +isRoad+", id:" +cell.getTile().getId()+", "+cameraCoordinates.x+":"+cameraCoordinates.y); // Get the ID.
//        	}
//        }
//		
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
//		Vector3 coordinates = new Vector3(screenX, screenY, 0);
//	    Vector3 position = camera.unproject(coordinates);
//	    camera.position.set(position);
//		System.out.println(position.x +" : "+position.y);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPlayerAlive() {
		return isPlayerAlive;
	}

	public void setPlayerAlive(boolean isPlayerAlive) {
		this.isPlayerAlive = isPlayerAlive;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}

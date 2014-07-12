package com.universal.tween.engine.objects;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.universal.tween.engine.resources.Resources;

public class Player extends SpritedGameObject implements InputProcessor {

	private TiledMapTileLayer collisionLayer;
	private float animationTime = 0;
	
	private Animation leftMovement;
	private Animation rightMovement;
	private Animation upMovement;
	private Animation downMovement;
	
	public Player() {
		super();
		setLeftMovement(new Animation(1 / 6f, Resources.getInstance().femaleAtlas.findRegions("left")));
		setRightMovement(new Animation(1 / 6f, Resources.getInstance().femaleAtlas.findRegions("right")));
		setUpMovement(new Animation(1 / 6f, Resources.getInstance().femaleAtlas.findRegions("up")));
		setDownMovement(new Animation(1 / 6f, Resources.getInstance().femaleAtlas.findRegions("down")));
		
		getLeftMovement().setPlayMode(Animation.PlayMode.LOOP);
		getRightMovement().setPlayMode(Animation.PlayMode.LOOP);
		getUpMovement().setPlayMode(Animation.PlayMode.LOOP);
		getDownMovement().setPlayMode(Animation.PlayMode.LOOP);
	}
	
	public void init(float x, float y, float width, float height, TiledMapTileLayer collisionLayer) {
		getBounds().set(x, y, width, height);
		setCollisionLayer(collisionLayer);
        setAlive(true);
        setCurrentSpriteIndex(1);
        getSprite().setRegion(Resources.getInstance().femaleAtlas.findRegion("right", getCurrentSpriteIndex()));
    }

	@Override
	public void update(float delta) {
		if (getVelocity().x == 0 && getVelocity().y == 0) return;
			
		float oldX = getBounds().x;
		float oldY = getBounds().y;
		boolean collisionX = false;
		boolean collisionY = false;

		if (getVelocity().x+getVelocity().y != 0) System.out.println("delta = "+delta);
		if (getVelocity().x+getVelocity().y != 0) System.out.println("delta * velocity x = "+getVelocity().x * delta);
		if (getVelocity().x+getVelocity().y != 0) System.out.println("delta * velocity y = "+getVelocity().y * delta);
		if (getVelocity().x+getVelocity().y != 0) System.out.println("old x = "+oldX+", old y = "+oldY);
		
		if( getVelocity().y > getSpeed() )
			getVelocity().y = getSpeed();
		else if( getVelocity().y < -getSpeed() )
			getVelocity().y = -getSpeed();
		
		if( getVelocity().x > getSpeed() )
			getVelocity().x = getSpeed();
		else if( getVelocity().x < -getSpeed() )
			getVelocity().x = -getSpeed();
		
		if (getVelocity().x+getVelocity().y != 0) System.out.println("after clamp velocity x = "+getVelocity().x+", velocity y = "+getVelocity().y);
		
		// move on x
		getBounds().setX(getBounds().x + getVelocity().x * delta);

		if (getVelocity().x+getVelocity().y != 0) System.out.println("new x = "+getBounds().getX());
		
		if(getVelocity().x < 0) // going left
			collisionX = collidesLeft();
		else if(getVelocity().x > 0) // going right
			collisionX = collidesRight();

		if (getVelocity().x+getVelocity().y != 0) System.out.println("collision x = "+collisionX);
		
		// react to x collision
		if(collisionX) {
			getBounds().setX(oldX);
			getVelocity().x = 0;
			if (getVelocity().x+getVelocity().y != 0) System.out.println("returned old x");
		}

		// move on y
		getBounds().setY(getBounds().y + getVelocity().y * delta);
		if (getVelocity().x+getVelocity().y != 0) System.out.println("new y = "+getBounds().getY());
		
		if(getVelocity().y < 0) // going down
			collisionY = collidesBottom();
		else if(getVelocity().y > 0) // going up
			collisionY = collidesTop();

		if (getVelocity().x+getVelocity().y != 0) System.out.println("collision y = "+collisionY);
		
		// react to y collision
		if(collisionY) {
			getBounds().setY(oldY);
			getVelocity().y = 0;
			if (getVelocity().x+getVelocity().y != 0) System.out.println("returned old y");
		}
		
		updateSprite(delta);
		
//		getSprite().setRegion(getVelocity().x < 0 ? left.getKeyFrame(animationTime) : getVelocity().x > 0 ? right.getKeyFrame(animationTime) : still.getKeyFrame(animationTime));
//		updateCurrentSpriteIndex();
//		getBounds().y += getSpeed() * Gdx.graphics.getDeltaTime();
//		getSprite().setRegion(getSpriteRegionForDirection("up"));	
		
	}

	private void updateSprite(float delta) {
		setAnimationTime(getAnimationTime() + delta);
//		String regionName = "right";
		//up
		if ( (getVelocity().x > 0 && getVelocity().y > 0) || 
			(getVelocity().x == 0 && getVelocity().y > 0) || 
			(getVelocity().x < 0 && getVelocity().y > 0) ) {
//			regionName = "up";
//			updateCurrentSpriteIndex();
			getSprite().setRegion(getUpMovement().getKeyFrame(getAnimationTime()));
		//down
		} else if ( (getVelocity().x > 0 && getVelocity().y < 0) || 
				(getVelocity().x == 0 && getVelocity().y < 0) || 
				(getVelocity().x < 0 && getVelocity().y < 0) ) {
//			regionName = "down";
//			updateCurrentSpriteIndex();
			System.out.println("AAAAAAAA:"+getAnimationTime());
			getSprite().setRegion(getDownMovement().getKeyFrame(getAnimationTime()));
		//left
		} else if ( getVelocity().x < 0 && getVelocity().y == 0 ) {
//			regionName = "left";
//			updateCurrentSpriteIndex();
			getSprite().setRegion(getLeftMovement().getKeyFrame(getAnimationTime()));
		//right
		} else if ( getVelocity().x > 0 && getVelocity().y == 0 ) {
//			updateCurrentSpriteIndex();
			getSprite().setRegion(getRightMovement().getKeyFrame(getAnimationTime()));
		//still
		} else if (getVelocity().x == 0 && getVelocity().y == 0) {
			setCurrentSpriteIndex(1);
			getSprite().setRegion(getSpriteRegionForDirection("right"));
		}
//		getSprite().setRegion(getSpriteRegionForDirection(regionName));
		
	}

	private boolean isCellBlocked(float x, float y) {
		int cellX = (int) (x / collisionLayer.getTileWidth());
		int cellY = (int) (y / collisionLayer.getTileHeight());
		Cell cell = collisionLayer.getCell(cellX, cellY);
		if (getVelocity().x+getVelocity().y != 0) System.out.println("cellX = "+cellX+", cellY = "+cellY);
		if (getVelocity().x+getVelocity().y != 0) System.out.println("cell == null : "+(cell == null));
		if (getVelocity().x+getVelocity().y != 0) System.out.println("cell.getTile() == null : "+(cell.getTile() == null));
		if (getVelocity().x+getVelocity().y != 0) System.out.println("cell.getTile().getProperties().containsKey(isroad) : "+cell.getTile().getProperties().containsKey("isroad"));
		return cell == null || cell.getTile() == null || !cell.getTile().getProperties().containsKey("isroad");
	}

	private boolean collidesRight() {
		float playerHeight = getBounds().getHeight() / 2;
		float tileHeight = collisionLayer.getTileHeight() / 2;
		float width = getBounds().getX() + getBounds().getWidth();
		
		for(float step = 0; step < playerHeight; step += tileHeight)
			if(isCellBlocked(width, getBounds().getY() + step))
				return true;
		
		return false;
	}

	private boolean collidesLeft() {
		float playerHeight = getBounds().getHeight() / 2;
		float tileHeight = collisionLayer.getTileHeight() / 2;
		
		for(float step = 0; step < playerHeight; step += tileHeight)
			if(isCellBlocked(getBounds().getX(), getBounds().getY() + step))
				return true;
		
		return false;
	}

	private boolean collidesTop() {
		float playerWidth = getBounds().getWidth() - (getBounds().getWidth()/2+getBounds().getWidth()/3);
		float tileWidth = collisionLayer.getTileWidth() / 2;
		float height = getBounds().getY() + getBounds().getHeight()/2;
		
		for(float step = 0; step < playerWidth; step += tileWidth)
			if(isCellBlocked(getBounds().getX() + step, height))
				return true;
		
		return false;

	}

	private boolean collidesBottom() {
		float playerWidth = getBounds().getWidth() - (getBounds().getWidth()/2+getBounds().getWidth()/3);
		float tileWidth = collisionLayer.getTileWidth() / 2;
		
		for(float step = 0; step < playerWidth; step += tileWidth)
			if(isCellBlocked(getBounds().getX() + step, getBounds().getY()))
				return true;
		
		return false;
	}	
	
	@Override
	public void render(Batch spriteBatch) {
		spriteBatch.draw(getSprite(), getBounds().x-getBounds().width/2, getBounds().y);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

//	public void checkCollision() {
//		
//	}
	
//	public void moveUp() {
//		updateCurrentSpriteIndex();
//		getBounds().y += getSpeed() * Gdx.graphics.getDeltaTime();
//		getSprite().setRegion(getSpriteRegionForDirection("up"));		
//	}
//	
//	public void moveDown() {
//		updateCurrentSpriteIndex();
//		getBounds().y -= getSpeed() * Gdx.graphics.getDeltaTime();
//		getSprite().setRegion(getSpriteRegionForDirection("down"));		
//	}
//	
//	public void moveLeft() {
//		updateCurrentSpriteIndex();
//		getBounds().x -= getSpeed() * Gdx.graphics.getDeltaTime();
//		getSprite().setRegion(getSpriteRegionForDirection("left"));		
//	}
//
//	public void moveRight() {
//		updateCurrentSpriteIndex();
//		getBounds().x += getSpeed() * Gdx.graphics.getDeltaTime();
//        getSprite().setRegion(getSpriteRegionForDirection("right"));		
//	}

	private AtlasRegion getSpriteRegionForDirection(String direction) {
		return Resources.getInstance().femaleAtlas.findRegion(direction, getCurrentSpriteIndex());
	}

	private void updateCurrentSpriteIndex() {
		setCurrentSpriteIndex(getCurrentSpriteIndex()+1);
		if (getCurrentSpriteIndex() > 9) setCurrentSpriteIndex(1);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			System.out.println(">>>>>>>>>>>>>>>>pressed W");
			getVelocity().y = getSpeed();
			setAnimationTime(0);
			break;
		case Keys.S:
			System.out.println(">>>>>>>>>>>>>>>>pressed S");
			getVelocity().y = -getSpeed();
			setAnimationTime(0);
			break;
		case Keys.A:
			System.out.println(">>>>>>>>>>>>>>>>pressed A");
			getVelocity().x = -getSpeed();
			setAnimationTime(0);
			break;
		case Keys.D:
			System.out.println(">>>>>>>>>>>>>>>>pressed D");
			getVelocity().x = getSpeed();
			setAnimationTime(0);
		}
		System.out.println("velocity x = "+getVelocity().x+", velocity y = "+getVelocity().y);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		System.out.println("<<<<<<<<<<<<<<unpressed key");
		switch(keycode) {
		case Keys.A:
		case Keys.W:
		case Keys.S:
		case Keys.D:
			getVelocity().x = 0;
			getVelocity().y = 0;
			setAnimationTime(0);
		}
		System.out.println("velocity x = "+getVelocity().x+", velocity y = "+getVelocity().y);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
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

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	public float getAnimationTime() {
		return animationTime;
	}

	public void setAnimationTime(float animationTime) {
		this.animationTime = animationTime;
	}

	public Animation getLeftMovement() {
		return leftMovement;
	}

	public void setLeftMovement(Animation leftMovement) {
		this.leftMovement = leftMovement;
	}

	public Animation getRightMovement() {
		return rightMovement;
	}

	public void setRightMovement(Animation rightMovement) {
		this.rightMovement = rightMovement;
	}

	public Animation getUpMovement() {
		return upMovement;
	}

	public void setUpMovement(Animation upMovement) {
		this.upMovement = upMovement;
	}

	public Animation getDownMovement() {
		return downMovement;
	}

	public void setDownMovement(Animation downMovement) {
		this.downMovement = downMovement;
	}
}

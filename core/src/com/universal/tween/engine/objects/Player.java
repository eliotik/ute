package com.universal.tween.engine.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.universal.tween.engine.resources.Resources;

public class Player extends SpritedGameObject {

	public void init(float x, float y, float width, float height) {
		getBounds().set(x, y, width, height);
        setAlive(true);
        setCurrentSpriteIndex(1);
        getSprite().setRegion(Resources.getInstance().femaleAtlas.findRegion("right", getCurrentSpriteIndex()));
    }

	@Override
	public void update() {
		updatePosition();
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.draw(getSprite(), getBounds().x, getBounds().y);
	}

	private void updatePosition() {

	}

	@Override
	public void dispose() {

	}

	public void checkCollision() {
		
	}
	
	public void moveUp() {
		updateCurrentSpriteIndex();
		getBounds().y += getSpeed() * Gdx.graphics.getDeltaTime();
		getSprite().setRegion(getSpriteRegionForDirection("up"));		
	}
	
	public void moveDown() {
		updateCurrentSpriteIndex();
		getBounds().y -= getSpeed() * Gdx.graphics.getDeltaTime();
		getSprite().setRegion(getSpriteRegionForDirection("down"));		
	}
	
	public void moveLeft() {
		updateCurrentSpriteIndex();
		getBounds().x -= getSpeed() * Gdx.graphics.getDeltaTime();
		getSprite().setRegion(getSpriteRegionForDirection("left"));		
	}

	public void moveRight() {
		updateCurrentSpriteIndex();
		getBounds().x += getSpeed() * Gdx.graphics.getDeltaTime();
        getSprite().setRegion(getSpriteRegionForDirection("right"));		
	}

	private AtlasRegion getSpriteRegionForDirection(String direction) {
		return Resources.getInstance().femaleAtlas.findRegion(direction, getCurrentSpriteIndex());
	}

	private void updateCurrentSpriteIndex() {
		setCurrentSpriteIndex(getCurrentSpriteIndex()+1);
		if (getCurrentSpriteIndex() > 9) setCurrentSpriteIndex(1);
	}
}

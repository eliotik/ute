package com.universal.tween.engine.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpritedGameObject extends GameObject {
	private Sprite sprite;
	private int currentSpriteIndex;

	public SpritedGameObject() {
		super();
		setSprite(new Sprite());
		setCurrentSpriteIndex(1);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Batch spriteBatch) {

	}

	@Override
	public void dispose() {

	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public int getCurrentSpriteIndex() {
		return currentSpriteIndex;
	}

	public void setCurrentSpriteIndex(int currentSpriteIndex) {
		this.currentSpriteIndex = currentSpriteIndex;
	}

}

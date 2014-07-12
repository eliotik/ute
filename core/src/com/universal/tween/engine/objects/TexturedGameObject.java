package com.universal.tween.engine.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TexturedGameObject extends GameObject {
	private Texture texture;

	public TexturedGameObject(Texture texture) {
		super();
		setTexture(texture);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Batch spriteBatch) {

	}

	@Override
	public void dispose() {
		getTexture().dispose();
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}

package com.universal.tween.engine.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class GameObject implements Poolable {
	private Rectangle bounds;
	private int speed = 400;
	private int baseSpeed = 400;
	private boolean alive;

	public GameObject() {
		setBounds(new Rectangle(0, 0, 0, 0));
		setAlive(false);
	}

	public void init(float x, float y, float width, float height) {
		getBounds().set(x, y, width, height);
        setAlive(true);
    }

	public void reset() {
		getBounds().setPosition(0,0);
		setAlive(false);
    }


	public abstract void update();
	public abstract void render(SpriteBatch spriteBatch);
	public abstract void dispose();

	public Rectangle getBounds() {
		return bounds;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getBaseSpeed() {
		return baseSpeed;
	}

	public void setBaseSpeed(int baseSpeed) {
		this.baseSpeed = baseSpeed;
	}
}
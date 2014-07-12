package com.universal.tween.engine.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class GameObject implements Poolable {
	private Rectangle bounds;
	private Vector2 velocity; 
	private float speed = 400;
	private float baseSpeed = 400;
	private float gravity = 60 * 1.8f;
	private float baseGravity = 60 * 1.8f;
	
	private boolean alive;

	public GameObject() {
		setBounds(new Rectangle(0, 0, 0, 0));
		setVelocity(new Vector2());
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


	public abstract void update(float delta);
	public abstract void render(Batch spriteBatch);
	public abstract void dispose();

	public Rectangle getBounds() {
		return bounds;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
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

	public float getBaseSpeed() {
		return baseSpeed;
	}

	public void setBaseSpeed(float baseSpeed) {
		this.baseSpeed = baseSpeed;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public float getBaseGravity() {
		return baseGravity;
	}

	public void setBaseGravity(float baseGravity) {
		this.baseGravity = baseGravity;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
}
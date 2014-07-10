package com.universal.tween.engine.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.universal.tween.engine.resources.Config;

public class Effects {

    private ParticleEffect explosionPrototype;
	private ParticleEffectPool explosionsPool;
	private Array<PooledEffect> activeExplosions;

	private ParticleEffect smallExplosionPrototype;
	private ParticleEffectPool smallExplosionsPool;
	private Array<PooledEffect> activeSmallExplosions;

	public Effects() {
		setExplosionPrototype(new ParticleEffect());
		getExplosionPrototype().load(Gdx.files.internal(Config.DATA_EFFECTS_EXPLOSION), Gdx.files.internal(Config.DATA_EFFECTS));
		getExplosionPrototype().setPosition(0, 0);

		setSmallExplosionPrototype(new ParticleEffect());
		getSmallExplosionPrototype().load(Gdx.files.internal(Config.DATA_EFFECTS_SMALL_EXPLOSION), Gdx.files.internal(Config.DATA_EFFECTS));
		getSmallExplosionPrototype().setPosition(0, 0);

		setExplosionsPool(new ParticleEffectPool(getExplosionPrototype(), 0, 70));
		setActiveExplosions(new Array<PooledEffect>());

		setSmallExplosionsPool(new ParticleEffectPool(getSmallExplosionPrototype(), 0, 70));
		setActiveSmallExplosions(new Array<PooledEffect>());
	}

	public void dispose() {
		getExplosionPrototype().dispose();
		getSmallExplosionPrototype().dispose();
	}

	public ParticleEffect getExplosionPrototype() {
		return explosionPrototype;
	}

	public void setExplosionPrototype(ParticleEffect explosionPrototype) {
		this.explosionPrototype = explosionPrototype;
	}

	public ParticleEffectPool getExplosionsPool() {
		return explosionsPool;
	}

	public void setExplosionsPool(ParticleEffectPool explosionsPool) {
		this.explosionsPool = explosionsPool;
	}

	public Array<PooledEffect> getActiveExplosions() {
		return activeExplosions;
	}

	public void setActiveExplosions(Array<PooledEffect> activeExplosions) {
		this.activeExplosions = activeExplosions;
	}

	public ParticleEffect getSmallExplosionPrototype() {
		return smallExplosionPrototype;
	}

	public void setSmallExplosionPrototype(ParticleEffect smallExplosionPrototype) {
		this.smallExplosionPrototype = smallExplosionPrototype;
	}

	public ParticleEffectPool getSmallExplosionsPool() {
		return smallExplosionsPool;
	}

	public void setSmallExplosionsPool(ParticleEffectPool smallExplosionsPool) {
		this.smallExplosionsPool = smallExplosionsPool;
	}

	public Array<PooledEffect> getActiveSmallExplosions() {
		return activeSmallExplosions;
	}

	public void setActiveSmallExplosions(Array<PooledEffect> activeSmallExplosions) {
		this.activeSmallExplosions = activeSmallExplosions;
	}

	public void render(SpriteBatch batch, float delta) {
		for(PooledEffect effect : getActiveExplosions()) {
			effect.draw(batch, delta);
			if(effect.isComplete()) {
				getActiveExplosions().removeValue(effect, true);
				effect.free();
			}
		}
		for(PooledEffect effect : getActiveSmallExplosions()) {
			effect.draw(batch, delta);
			if(effect.isComplete()) {
				getActiveSmallExplosions().removeValue(effect, true);
				effect.free();
			}
		}
	}
}

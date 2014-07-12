package com.universal.tween.engine.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.universal.tween.engine.helpers.Effects;
import com.universal.tween.engine.resources.Resources;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;

public class Ball extends SpritedGameObject {

	private float xModificator = 0;
	private float yModificator = 0;
	private Effects effects;
	private Color color;
	private boolean changeColor = false;
	
	public void init(Effects effects, float x, float y, float width, float height) {
		if (getEffects() == null) setEffects(effects);
		getBounds().set(x, y, width, height);
        setAlive(true);
        getSprite().setRegion(Resources.getInstance().bubbleAtlas.findRegion("bubble", getCurrentSpriteIndex()));
        initSphereSprite();
        applyDirectionModificators();
        changeColor = MathUtils.random(3) == 2;
        setColor(new Color(MathUtils.random(1f), MathUtils.random(1f), MathUtils.random(1f), 1));
    }

	private void applyDirectionModificators() {
		int r = MathUtils.random(0,3);
		setxModificator((r == 2) ? -1 : ((r > 2) ? 1 : -1));
		setyModificator((r == 2) ? 1 : ((r < 2) ? -1 : 1));
	}

	private void initSphereSprite() {
		Timer.schedule(new Task(){
            @Override
            public void run() {
            	if (!isAlive()) {
            		cancel();
            		return;
            	}
            	setCurrentSpriteIndex(getCurrentSpriteIndex() + 1);
                if (getCurrentSpriteIndex() > 24)
                	setCurrentSpriteIndex(2);

                getSprite().setRegion(Resources.getInstance().bubbleAtlas.findRegion("bubble", getCurrentSpriteIndex()));
            }
        }
        , 0, 1/12.0f);
	}

	@Override
	public void update(float delta) {
		float nextX = getBounds().x + (getxModificator() * getSpeed() * Gdx.graphics.getDeltaTime());
		float nextY = getBounds().y + (getyModificator() * getSpeed() * Gdx.graphics.getDeltaTime());
		
		if (nextX > Gdx.graphics.getWidth()-getBounds().width) {
			setxModificator(-1);
			nextX = getBounds().x + (getxModificator() * getSpeed() * Gdx.graphics.getDeltaTime());
		} else if (nextX < 0) {
			setxModificator(1);
			nextX = getBounds().x + (getxModificator() * getSpeed() * Gdx.graphics.getDeltaTime());
		}
		
		if (nextY > Gdx.graphics.getHeight()-getBounds().height) {
			setyModificator(-1);
			nextY = getBounds().y + (getyModificator() * getSpeed() * Gdx.graphics.getDeltaTime());
		} else if (nextY < 0) {
			setyModificator(1);
			nextY = getBounds().y + (getyModificator() * getSpeed() * Gdx.graphics.getDeltaTime());
		}
		
		getBounds().x = nextX;
		getBounds().y = nextY;
	}

	@Override
	public void render(Batch spriteBatch) {
		if (changeColor) {
			Color c = new Color(spriteBatch.getColor());
			spriteBatch.setColor(getColor().r, getColor().g, getColor().b, 1); 
			spriteBatch.draw(getSprite(), getBounds().x, getBounds().y);
			spriteBatch.setColor(c);
		} else {
			spriteBatch.draw(getSprite(), getBounds().x, getBounds().y);
		}
	}

	@Override
	public void dispose() {

	}

	public float getxModificator() {
		return xModificator;
	}

	public void setxModificator(float xModificator) {
		this.xModificator = xModificator;
	}

	public float getyModificator() {
		return yModificator;
	}

	public void setyModificator(float yModificator) {
		this.yModificator = yModificator;
	}

	public Effects getEffects() {
		return effects;
	}

	public void setEffects(Effects effects) {
		this.effects = effects;
	}

	public void checkCollision(Array<Ball> activeBalls) {
		java.util.Iterator<Ball> ballIter = activeBalls.iterator();
		while (ballIter.hasNext()) {
			Ball ball = ballIter.next();
			if (!ball.isAlive() || ball == this) continue;
			
			if (ball.getBounds().overlaps(getBounds())) {
				ball.setAlive(false);
				setAlive(false);
				
				explode();
				ball.explode();
				explodeCollisionSide(ball);
				continue;
			}
		}
	}

	private void explodeCollisionSide(Ball ball) {
		Rectangle intersection = new Rectangle();                  
		Intersector.intersectRectangles(getBounds(), ball.getBounds(), intersection);     
		if (intersection.x > getBounds().x) {                                 
		    //Intersects with right side 
			bigExplode(getBounds().x + getBounds().width, getBounds().y + getBounds().height/2);
			return;
		}
		
		if (intersection.y > getBounds().y) {                                  
		    //Intersects with top side
			bigExplode(getBounds().x + getBounds().width/2, getBounds().y + getBounds().height);
			return;
		}
		
		if (intersection.x + intersection.width < getBounds().x + getBounds().width) { 
		    //Intersects with left side       
			bigExplode(getBounds().x, getBounds().y + getBounds().height/2);
			return;
		}
		
		if (intersection.y + intersection.height < getBounds().y + getBounds().height) {
		    //Intersects with bottom side	
			bigExplode(getBounds().x + getBounds().width/2, getBounds().y);
			return;
		}
	}

	public void bigExplode(float x, float y) {
		PooledEffect effect = getEffects().getExplosionsPool().obtain();
		effect.setPosition(x, y);
		getEffects().getActiveExplosions().add(effect);
	}
	
	public void explode() {
		PooledEffect effect = getEffects().getSmallExplosionsPool().obtain();
		effect.setPosition(getBounds().x+getBounds().width, getBounds().y+getBounds().height);
		getEffects().getActiveSmallExplosions().add(effect);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}		
}

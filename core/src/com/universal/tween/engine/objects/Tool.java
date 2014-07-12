package com.universal.tween.engine.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.universal.tween.engine.resources.Resources;

public class Tool extends SpritedGameObject {

	private Color color;
	
	public void init(float x, float y, float width, float height) {
		getBounds().set(x, y, width, height);
        setAlive(true);
        setCurrentSpriteIndex(MathUtils.random(Resources.getInstance().objectsAtlas.getRegions().size-1));
        getSprite().setRegion(Resources.getInstance().objectsAtlas.findRegion("object", getCurrentSpriteIndex()));
        setColor(getSprite().getColor().cpy());
//        setColor(new Color(MathUtils.random(1f), MathUtils.random(1f), MathUtils.random(1f), 1));
    }

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(Batch spriteBatch) {
		Color c = new Color(spriteBatch.getColor());
		spriteBatch.setColor(getColor().r, getColor().g, getColor().b, getColor().a); 
		spriteBatch.draw(getSprite(), getBounds().x, getBounds().y);
		spriteBatch.setColor(c);
	}

	@Override
	public void dispose() {

	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}		
}

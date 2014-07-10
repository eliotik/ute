package com.universal.tween.engine.objects;

import com.badlogic.gdx.graphics.Color;

import aurelienribon.tweenengine.TweenAccessor;

public class ToolAccessor implements TweenAccessor<Tool> {
    // The following lines define the different possible tween types.
    // It's up to you to define what you need :-)

	public static final int POS_XY = 1;
	public static final int CPOS_XY = 2;
	public static final int SCALE_XY = 3;
	public static final int ROTATION = 4;
	public static final int OPACITY = 5;
	public static final int TINT = 6;

    // TweenAccessor implementation

    @Override
    public int getValues(Tool target, int tweenType, float[] returnValues) {
        switch (tweenType) {
			case POS_XY:
				returnValues[0] = target.getBounds().getX();
				returnValues[1] = target.getBounds().getY();
				return 2;
	
			case CPOS_XY:
				returnValues[0] = target.getBounds().getX() + target.getBounds().getWidth()/2;
				returnValues[1] = target.getBounds().getY() + target.getBounds().getHeight()/2;
				return 2;
	
			case SCALE_XY:
				returnValues[0] = target.getSprite().getScaleX();
				returnValues[1] = target.getSprite().getScaleY();
				return 2;
	
			case ROTATION: returnValues[0] = target.getSprite().getRotation(); return 1;
			case OPACITY: returnValues[0] = target.getColor().a; return 1;
	
			case TINT:
				returnValues[0] = target.getColor().r;
				returnValues[1] = target.getColor().g;
				returnValues[2] = target.getColor().b;
				return 3;
	
			default: assert false; return -1;
		
        }
    }
    
    @Override
    public void setValues(Tool target, int tweenType, float[] newValues) {
        switch (tweenType) {
	        case POS_XY: target.getBounds().setPosition(newValues[0], newValues[1]); break;
			case CPOS_XY: target.getBounds().setPosition(newValues[0] - target.getBounds().getWidth()/2, newValues[1] - target.getBounds().getHeight()/2); break;
			case SCALE_XY: target.getSprite().setScale(newValues[0], newValues[1]); break;
			case ROTATION: target.getSprite().setRotation(newValues[0]); break;
	
			case OPACITY:
				Color c = target.getColor();
				c.set(c.r, c.g, c.b, newValues[0]);
				target.setColor(c);
				break;
	
			case TINT:
				c = target.getColor();
				c.set(newValues[0], newValues[1], newValues[2], c.a);
				target.setColor(c);
				break;
	
			default: assert false;
        }
    }
}

package com.universal.tween.engine.gui.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.TweenPaths;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Quad;
import aurelienribon.tweenengine.equations.Quart;
import aurelienribon.tweenengine.equations.Quint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.universal.tween.engine.gui.ScreenType;
import com.universal.tween.engine.helpers.Objects;
import com.universal.tween.engine.objects.Tool;
import com.universal.tween.engine.objects.ToolAccessor;
import com.universal.tween.engine.resources.Config;
import com.universal.tween.engine.resources.GameInstance;
import com.universal.tween.engine.resources.Resources;

public class GameScreen2 extends DefaultScreen implements InputProcessor {
	
	private SpriteBatch batch = null;
	private OrthographicCamera camera = null;
	private Objects objects;
	
	private static final String DESC = "TWEEN UNIVERSAL ENGINE";
	private BitmapFont font = null; 
	private float captionX1 = 0;
	private float captionY = 0;
	
	private TweenManager tweenManager;
	
	private boolean isToolAlive = false;
	
	public GameScreen2() {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Game screen 2: init");
		setBatch(new SpriteBatch());
		setObjects(new Objects());
		setFont(new BitmapFont());
		
		TextBounds wholeCaptionBounds = getFont().getBounds(DESC);
		captionX1 = Gdx.graphics.getWidth() / 2 - wholeCaptionBounds.width / 2;
		captionY = Gdx.graphics.getHeight() / 2 + wholeCaptionBounds.height / 2;
		
		Tween.registerAccessor(Tool.class, new ToolAccessor());
		setTweenManager(new TweenManager());
		Tween.setWaypointsLimit(10);
		Tween.setCombinedAttributesLimit(3);
	}

	@Override
	public void render(float delta) {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL20.GL_BLEND);
		gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		GameInstance.getInstance().switchScreens(ScreenType.GAME2);
		
		if (Gdx.input.isTouched()) {
			Vector3 position = new Vector3();
			position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			getCamera().unproject(position);
			spawnTool(position);
		}
		
		getObjects().update(delta);
		
		getBatch().begin();
		getBatch().draw(Resources.getInstance().background2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		font.setColor(0f, 0.5f, 1f, 1f);
		font.draw(batch, DESC, captionX1, captionY);
		
		getObjects().render(getBatch(), delta);
		
        getBatch().end();
        
        getTweenManager().update(delta);
	}

	private void spawnTool(Vector3 position) {
		if (isToolAlive()) return;
		setToolAlive(true);
		
		Tool tool = getObjects().getToolsPool().obtain();
        tool.init(position.x-65/2, position.y-65/2, 65, 65);
        tool.setSpeed(tool.getBaseSpeed()/MathUtils.random(1, 4));

        getObjects().getActiveTools().add(tool);

//        Tween.to(tool, ToolAccessor.POSITION_XY, 0.5f)
//        .target(0, 0)
//        .ease(Bounce.OUT)
//        .delay(1.0f)
//        .repeatYoyo(5, 0.5f)
//        .start(getTweenManager());
        
//        Tween.to(tool, ToolAccessor.CPOS_XY, 3.0f)
//			.waypoint(50, 50)
//			.waypoint(220, 150)
//			.waypoint(100, 480)
//			.target(600, 700)
//			.ease(Quad.INOUT)
//			.path(TweenPaths.catmullRom)
//			.repeatYoyo(1, 0.2f)
//			.delay(0.5f)
//			.setCallback(new TweenCallback() {
//				@Override 
//				public void onEvent(int type, BaseTween<?> source) {
//					if (type == TweenCallback.COMPLETE) {
//						((Tool)((Tween) source).getTarget()).setAlive(false);
//					}
//				}
//			})
//			.setCallbackTriggers(TweenCallback.COMPLETE)
//			.start(getTweenManager());
        
        Timeline.createSequence()
//		.push(Tween.set(tool, ToolAccessor.POS_XY).targetRelative(50, 0))
//		.push(Tween.set(tool, ToolAccessor.POS_XY).targetRelative(400, 0))
//		.push(Tween.set(tool, ToolAccessor.POS_XY).targetRelative(600, 0.5f))
//		.push(Tween.set(tool, ToolAccessor.SCALE_XY).target(7, 7))
//		.push(Tween.set(tool, ToolAccessor.OPACITY).target(0))
//		.push(Tween.set(tool, ToolAccessor.SCALE_XY).target(1, 0))
//		.push(Tween.set(tool, ToolAccessor.OPACITY).target(0))
//		.push(Tween.set(tool, ToolAccessor.OPACITY).target(0))

//		.pushPause(0.5f)
		.push(Tween.to(tool, ToolAccessor.POS_XY, 0.8f).target(100, 100).ease(Back.OUT))
		.push(Tween.to(tool, ToolAccessor.POS_XY, 0.5f).targetRelative(100, 0).ease(Quart.OUT))
		.push(Tween.to(tool, ToolAccessor.POS_XY, 2.5f).targetRelative(150, 0).ease(Quart.OUT))
		.push(Tween.to(tool, ToolAccessor.POS_XY, 0.6f).targetRelative(100, -0.5f).ease(Quint.OUT))
		.pushPause(1f)
//		.beginParallel()
//			.push(Tween.set(tool, ToolAccessor.OPACITY).target(0))
//			.push(Tween.to(tool, ToolAccessor.SCALE_XY, 0.5f).target(1, 1).ease(Back.OUT))
//		.end()
//		.push(Tween.to(tool, ToolAccessor.SCALE_XY, 0.5f).target(1, 1).ease(Back.IN))
//		.pushPause(0.3f)
//		.beginParallel()
//			.push(Tween.to(tool, ToolAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
//			.push(Tween.to(tool, ToolAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
//			.push(Tween.to(tool, ToolAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
//			.push(Tween.to(tool, ToolAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
//		.end()
//
//		.pushPause(-0.3f)
//		.push(Tween.to(tool, ToolAccessor.OPACITY, 0.3f).target(1))
//		.beginParallel()
			.push(Tween.to(tool, ToolAccessor.ROTATION, 2.0f).target(360*15).ease(Quad.OUT))
//		.end()
		.push(Tween.to(tool, ToolAccessor.TINT, 0.4f).target(1, 0, 0))
		.push(Tween.to(tool, ToolAccessor.TINT, 0.4f).target(0, 1, 0))
		.push(Tween.to(tool, ToolAccessor.TINT, 0.4f).target(0, 0, 1))
//		.pushPause(0.3f)
		.push(Tween.to(tool, ToolAccessor.CPOS_XY, 3.0f)
			.waypoint(50, 50)
			.waypoint(220, 150)
			.waypoint(100, 480)
			.target(650, 450)
			.ease(Cubic.OUT)
			.path(TweenPaths.catmullRom))
		.pushPause(-2f)
		.beginParallel()
			.push(Tween.to(tool, ToolAccessor.OPACITY, 2.0f).target(0))
		.end()			
		
//		.push(Tween.to(tool, ToolAccessor.SCALE_XY, 0.6f).waypoint(150, 480).target(600, 300).ease(Cubic.OUT))
//		.pushPause(0.3f)
//		.beginParallel()
//			.push(Tween.to(tool, ToolAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
//			.push(Tween.to(tool, ToolAccessor.POS_XY, 0.5f).targetRelative(1, 0).ease(Back.IN))
//		.end()
//		.pushPause(0.3f)

		.setCallback(new TweenCallback() {
				@Override 
				public void onEvent(int type, BaseTween<?> source) {
					if (type == TweenCallback.COMPLETE) {
//						((Tool)((Tween) source).getTarget()).setAlive(false);
						Timeline tl = (Timeline) source;
						Tween tw = (Tween) tl.getChildren().get(0);
						((Tool) tw.getTarget()).setAlive(false);
						setToolAlive(false);
					}
				}
			})
		.setCallbackTriggers(TweenCallback.COMPLETE)
		
		.start(getTweenManager());
        
        getObjects().setLastToolTime(TimeUtils.nanoTime());
	}
	
	@Override
	public void resize(int width, int height) {
		setCamera(new OrthographicCamera());
		getCamera().setToOrtho(false, width, height);
		getBatch().setProjectionMatrix(getCamera().combined);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		getBatch().dispose();
		getObjects().dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public Objects getObjects() {
		return objects;
	}

	public void setObjects(Objects objects) {
		this.objects = objects;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	public TweenManager getTweenManager() {
		return tweenManager;
	}

	public void setTweenManager(TweenManager tweenManager) {
		this.tweenManager = tweenManager;
	}

	public boolean isToolAlive() {
		return isToolAlive;
	}

	public void setToolAlive(boolean isToolAlive) {
		this.isToolAlive = isToolAlive;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
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

}

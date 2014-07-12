package com.universal.tween.engine.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.universal.tween.engine.gui.ScreenType;
import com.universal.tween.engine.helpers.Effects;
import com.universal.tween.engine.helpers.Objects;
import com.universal.tween.engine.objects.Ball;
import com.universal.tween.engine.resources.Config;
import com.universal.tween.engine.resources.GameInstance;
import com.universal.tween.engine.resources.Resources;

public class GameScreen extends DefaultScreen implements InputProcessor {
	
	private SpriteBatch batch = null;
	private OrthographicCamera camera = null;
	private Effects effects;
	private Objects objects;
	
	private static final String DESC = "ANIMATION, SPRITES, COLOR CHANGE, EFFECTS";
	private BitmapFont font = null; 
	private float captionX1 = 0;
	private float captionY = 0;
	
	public GameScreen() {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Game screen 1: init");
		setBatch(new SpriteBatch());
		setEffects(new Effects());
		setObjects(new Objects());
		setFont(new BitmapFont());
		
		TextBounds wholeCaptionBounds = font.getBounds(DESC);
		captionX1 = Gdx.graphics.getWidth() / 2 - wholeCaptionBounds.width / 2;
		captionY = Gdx.graphics.getHeight() / 2 + wholeCaptionBounds.height / 2;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		GameInstance.getInstance().switchScreens(ScreenType.GAME);
		
		if (Gdx.input.isTouched()) {
			Vector3 position = new Vector3();
			position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			getCamera().unproject(position);
			spawnBall(position);
		}
		
		getObjects().update(delta);
		
		getBatch().begin();
		getBatch().draw(Resources.getInstance().background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		font.setColor(0f, 0.5f, 1f, 1f);
		font.draw(batch, DESC, captionX1, captionY);
		
		getObjects().render(getBatch(), delta);
		getEffects().render(getBatch(), delta);
		
        getBatch().end();
	}

	private void spawnBall(Vector3 position) {
		if (TimeUtils.nanoTime() - getObjects().getLastBallTime() <= 500000000) return;

		Ball ball = getObjects().getBallsPool().obtain();
        ball.init(getEffects(), position.x-30, position.y-30, 30, 30);
        ball.setSpeed(ball.getBaseSpeed()/MathUtils.random(1, 4));

        getObjects().getActiveBalls().add(ball);

        getObjects().setLastBallTime(TimeUtils.nanoTime());
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
		getEffects().dispose();
		getObjects().dispose();
		getFont().dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public Effects getEffects() {
		return effects;
	}

	public void setEffects(Effects effects) {
		this.effects = effects;
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

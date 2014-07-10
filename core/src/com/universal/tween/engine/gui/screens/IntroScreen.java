package com.universal.tween.engine.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.universal.tween.engine.gui.ScreenType;
import com.universal.tween.engine.gui.ScreenManager;
import com.universal.tween.engine.resources.Config;
import com.universal.tween.engine.resources.GameInstance;
import com.universal.tween.engine.resources.Resources;

public class IntroScreen extends DefaultScreen {
	
	private static final String LIB = "Lib";
	private static final String GDX = "GDX";
	
	private SpriteBatch batch = null;
	private BitmapFont font = null; 
	private float captionX1 = 0;
	private float captionX2 = 0;
	private float captionY = 0;
	private Stage stage;
	
	public IntroScreen() {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Inter: create");
		stage = new Stage();
		batch = new SpriteBatch();
		font = new BitmapFont();
		TextBounds wholeCaptionBounds = font.getBounds(LIB + GDX);
		captionX1 = -wholeCaptionBounds.width/2;
		captionY = wholeCaptionBounds.height/2;
		captionX2 = captionX1 + font.getBounds(LIB).width + 1f;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.setColor(0f, 0.5f, 1f, 1f);
		font.draw(batch, LIB, captionX1, captionY);
		font.setColor(0f, 1f, 0f, 1f);
		font.draw(batch, GDX, captionX2, captionY);
		batch.end();
		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Inter: resize");
		batch.getProjectionMatrix().setToOrtho2D(-width/2, -height/2, width, height);
	}

	@Override
	public void show() {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Inter: show");
		stage.addAction(Actions.sequence(
			Actions.delay(0.5f), 
			new Action(){
				@Override
				public boolean act(float delta) {
					Resources.getInstance();
					GameInstance.getInstance();
					return true;
				}}, 
				Actions.delay(0.5f), 
				new Action() {
					@Override
					public boolean act(float delta) {
						ScreenManager.getInstance().show(ScreenType.GAME);
						return true;
					}}
			));
	}

	@Override
	public void hide() {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Inter: hide");
		/* dispose intro screen because it won't be needed anymore */
		ScreenManager.getInstance().dispose(ScreenType.INTRO);
	}

	@Override
	public void pause() {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Inter: pause");
	}

	@Override
	public void resume() {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Inter: resume");
	}

	@Override
	public void dispose() {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Inter: dispose");
		font.dispose();
		batch.dispose();
		stage.dispose();
	}
}

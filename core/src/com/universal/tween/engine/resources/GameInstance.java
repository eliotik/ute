package com.universal.tween.engine.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.universal.tween.engine.gui.ScreenManager;
import com.universal.tween.engine.gui.ScreenType;

public class GameInstance {

    public boolean debugMode = false;

	public static GameInstance instance;

	public static GameInstance getInstance() {
		if (instance == null) {
			if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Game instance: init from zero");
			instance = new GameInstance();
		} else {
			if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Game instance: already inited");
		}
		return instance;
	}

	public void dispose() {
		instance = null;
	}
	
	public void switchScreens(ScreenType screen) {
		switch (screen) {
		case GAME:
			if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
				ScreenManager.getInstance().show(ScreenType.GAME2);
			} else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
				ScreenManager.getInstance().show(ScreenType.GAME3);
			}
			break;
		case GAME2:
			if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
				ScreenManager.getInstance().show(ScreenType.GAME);
			} else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
				ScreenManager.getInstance().show(ScreenType.GAME3);
			}
			break;
		case GAME3:
			if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
				ScreenManager.getInstance().show(ScreenType.GAME);
			} else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
				ScreenManager.getInstance().show(ScreenType.GAME2);
			}
			break;			
		default:
		case INTRO:
			break;
		}
	}
}

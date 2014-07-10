package com.universal.tween.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.universal.tween.engine.gui.ScreenType;
import com.universal.tween.engine.gui.ScreenManager;
import com.universal.tween.engine.resources.Config;
import com.universal.tween.engine.resources.GameInstance;
import com.universal.tween.engine.resources.Resources;

public class UTEExample extends Game {

    @Override
    public void create() {
    	if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Game: create");
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().show(ScreenType.INTRO);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    	if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Game: dispose");
    	super.dispose();
        ScreenManager.getInstance().dispose();
        Resources.getInstance().dispose();
        GameInstance.getInstance().dispose();
    }
}

package com.universal.tween.engine.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Resources {
	public TextureAtlas bubbleAtlas = new TextureAtlas(Gdx.files.internal(Config.DATA_IMAGES_THEMES_MATURE_BUBBLE_ATLAS));
	public TextureAtlas objectsAtlas = new TextureAtlas(Gdx.files.internal(Config.DATA_IMAGES_THEMES_MATURE_OBJECTS_ATLAS));
	public TextureAtlas femaleAtlas = new TextureAtlas(Gdx.files.internal(Config.DATA_IMAGES_THEMES_MATURE_FEMALE_ATLAS));

	public Music beep = Gdx.audio.newMusic(Gdx.files.internal(Config.DATA_SOUNDS_BEEP));
	
	public Texture background = new Texture(Gdx.files.internal(Config.DATA_IMAGES_BACKGROUND));
	public Texture background2 = new Texture(Gdx.files.internal(Config.DATA_IMAGES_BACKGROUND2));

    public static Resources instance;

	public static Resources getInstance() {
		if (instance == null) {
			if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Resources: init from zero");
			instance = new Resources();
		} else {
			if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Resources: already inited");
		}
		return instance;
	}

	public void reInit() {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Resources: reinit");
		dispose();
		bubbleAtlas = new TextureAtlas(Gdx.files.internal(Config.DATA_IMAGES_THEMES_MATURE_BUBBLE_ATLAS));
		objectsAtlas = new TextureAtlas(Gdx.files.internal(Config.DATA_IMAGES_THEMES_MATURE_OBJECTS_ATLAS));
		femaleAtlas = new TextureAtlas(Gdx.files.internal(Config.DATA_IMAGES_THEMES_MATURE_FEMALE_ATLAS));
		beep = Gdx.audio.newMusic(Gdx.files.internal(Config.DATA_SOUNDS_BEEP));
		background = new Texture(Gdx.files.internal(Config.DATA_IMAGES_BACKGROUND));
		background2 = new Texture(Gdx.files.internal(Config.DATA_IMAGES_BACKGROUND2));
	}

	public void dispose() {
		if (Config.SHOW_LOG) Gdx.app.log(Config.LOG, "Resources: dispose");
		bubbleAtlas.dispose();
		objectsAtlas.dispose();
		femaleAtlas.dispose();
		beep.dispose();
		background.dispose();
		background.dispose();
		instance = null;
	}

}

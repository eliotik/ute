package com.universal.tween.engine.gui;

import com.universal.tween.engine.gui.screens.*;

public enum ScreenType {
	 
    INTRO {
        @Override
        protected DefaultScreen getScreenInstance() {
            return new IntroScreen();
        }
    },
    
    GAME {
        @Override
        protected DefaultScreen getScreenInstance() {
             return new GameScreen();
        }
    },
    
    GAME2 {
        @Override
        protected DefaultScreen getScreenInstance() {
             return new GameScreen2();
        }
    },
    
    GAME3 {
        @Override
        protected DefaultScreen getScreenInstance() {
             return new GameScreen3();
        }
    };
 
    protected abstract com.badlogic.gdx.Screen getScreenInstance();
 
}

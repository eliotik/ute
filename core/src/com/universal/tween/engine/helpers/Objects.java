package com.universal.tween.engine.helpers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.universal.tween.engine.objects.Ball;
import com.universal.tween.engine.objects.Player;
import com.universal.tween.engine.objects.Tool;

public class Objects {

	private Array<Ball> activeBalls;
    private Pool<Ball> ballsPool;

    private Array<Tool> activeTools;
    private Pool<Tool> toolsPool;
    
    private Array<Player> activePlayers;
    private Pool<Player> playersPool;
    
    private long lastBallTime;
    private long lastToolTime;
    private long lastPlayerTime;
    
	public Objects() {
		setActiveBalls(new Array<Ball>());
		setBallsPool(new Pool<Ball>() {
            @Override
            protected Ball newObject() {
                return new Ball();
            }
        });
		
		setActiveTools(new Array<Tool>());
		setToolsPool(new Pool<Tool>() {
			@Override
			protected Tool newObject() {
				return new Tool();
			}
		});
		
		setActivePlayers(new Array<Player>());
		setPlayersPool(new Pool<Player>() {
            @Override
            protected Player newObject() {
                return new Player();
            }
        });
	}

	public void dispose() {
	}

	public void update(float delta) {
		updateBalls(delta);
		updateTools(delta);
		updatePlayers(delta);
	}
	
	public void render(Batch batch, float delta) {
		for (Ball ball : getActiveBalls()) {
			ball.render(batch);
		}
		
		for (Tool tool : getActiveTools()) {
			tool.render(batch);
		}
		
		for (Player player : getActivePlayers()) {
			player.render(batch);
		}
	}

	private void updateBalls(float delta) {
		Ball ball;
		int balls = getActiveBalls().size;
		for (int i = balls; --i >= 0;) {
			ball = getActiveBalls().get(i);
			ball.checkCollision(getActiveBalls());
			if (!ball.isAlive()) {
				getActiveBalls().removeIndex(i);
				getBallsPool().free(ball);
			} else {
				ball.update(delta);
			}
		}
	}

	private void updateTools(float delta) {
		Tool tool;
		int tools = getActiveTools().size;
		for (int i = tools; --i >= 0;) {
			tool = getActiveTools().get(i);
			if (!tool.isAlive()) {
				getActiveTools().removeIndex(i);
				getToolsPool().free(tool);
			} else {
				tool.update(delta);
			}
		}
	}	
	
	private void updatePlayers(float delta) {
		Player player;
		int players = getActivePlayers().size;
		for (int i = players; --i >= 0;) {
			player = getActivePlayers().get(i);
			if (!player.isAlive()) {
				getActivePlayers().removeIndex(i);
				getPlayersPool().free(player);
			} else {
				player.update(delta);
			}
		}
	}	

	public Array<Ball> getActiveBalls() {
		return activeBalls;
	}

	public void setActiveBalls(Array<Ball> activeBalls) {
		this.activeBalls = activeBalls;
	}

	public Pool<Ball> getBallsPool() {
		return ballsPool;
	}

	public void setBallsPool(Pool<Ball> ballsPool) {
		this.ballsPool = ballsPool;
	}

	public long getLastBallTime() {
		return lastBallTime;
	}

	public void setLastBallTime(long lastBallTime) {
		this.lastBallTime = lastBallTime;
	}

	public Array<Tool> getActiveTools() {
		return activeTools;
	}

	public void setActiveTools(Array<Tool> activeTools) {
		this.activeTools = activeTools;
	}

	public Pool<Tool> getToolsPool() {
		return toolsPool;
	}

	public void setToolsPool(Pool<Tool> toolsPool) {
		this.toolsPool = toolsPool;
	}

	public long getLastToolTime() {
		return lastToolTime;
	}

	public void setLastToolTime(long lastToolTime) {
		this.lastToolTime = lastToolTime;
	}

	public Array<Player> getActivePlayers() {
		return activePlayers;
	}

	public void setActivePlayers(Array<Player> activePlayers) {
		this.activePlayers = activePlayers;
	}

	public Pool<Player> getPlayersPool() {
		return playersPool;
	}

	public void setPlayersPool(Pool<Player> playersPool) {
		this.playersPool = playersPool;
	}

	public long getLastPlayerTime() {
		return lastPlayerTime;
	}

	public void setLastPlayerTime(long lastPlayerTime) {
		this.lastPlayerTime = lastPlayerTime;
	}
}

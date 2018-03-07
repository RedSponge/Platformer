package com.redsponge.platformer.camera;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.entity.enemy.AbstractEnemy;
import com.redsponge.redutils.console.ConsoleMSG;

public class CameraManager {
	
	private float offsetX, offsetY;
	
	private Handler handler;
	private StateLevel stateLevel;
	public CameraUtils utils;

	private boolean moving;

	private int maxX, maxY;

	public CameraManager(Handler handler) {
		this.handler = handler;
		init();
	}

	private void init() {
		offsetX = 0;
		offsetY = 0;
		moving = false;
	}

	public void reset() {
		init();
	}
	
	public void tick() {
	    if(offsetX > maxX) {
	        offsetX = maxX;
        }
        /*if(offsetX < 0) {
	    	offsetX = 0;
	    	if() {

            }
		}*/

		tickWorldBlocks();
		tickWorldEnemies();
		tickPlayer();
	}
	
	public void init(StateLevel stateLevel) {
		this.stateLevel = stateLevel;
		utils = new CameraUtils(handler, this, stateLevel);
		maxX = stateLevel.getWorldXSize() - handler.getCanvasWidth();
		ConsoleMSG.INFO.info(Integer.toString(maxX), this);
	}

	private void tickWorldBlocks() {
		for(AbstractBlock b : stateLevel.getWorldBlocks()) {
			b.setX(b.getStartX() - offsetX);
			b.setX(b.getX()-b.getX()%1);
			b.getBoundingBox().setX(b.getX());
		}
	}

	private void tickWorldEnemies() {
	    if(!moving) {
	        return;
        }
		for(AbstractEnemy e : stateLevel.getWorldEnemies().values()) {
			e.updateCurrentPosition();
			e.setX(e.getCurrentX() - stateLevel.getPlayer().getSpeedX());
			e.getBoundingBox().setX(e.getX());
		}
	}
	
	private void tickPlayer() {
		
	}

	public int getMaxX() {
		return maxX;
	}

	public float getOffsetX() {
		return offsetX;
	}
	
	public float getOffsetY() {
		return offsetY;
	}

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}
	
	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}
	
}

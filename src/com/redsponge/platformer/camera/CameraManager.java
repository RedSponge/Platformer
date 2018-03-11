package com.redsponge.platformer.camera;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.entity.enemy.AbstractEnemy;
import com.redsponge.redutils.console.ConsoleMSG;

public class CameraManager {
	
	private float offsetX, offsetY;
	private float toMoveX, toMoveY;
	
	private Handler handler;
	private StateLevel stateLevel;
	public CameraUtils utils;

	private boolean movingX, movingY;

	private int maxX, maxY;

	public CameraManager(Handler handler) {
		this.handler = handler;
		init();
	}

	private void init() {
		offsetX = 0;
		offsetY = 150;
		toMoveX = 0;
		toMoveY = 0;
		movingX = false;
		movingY = false;
	}

	public void reset() {
		init();
	}
	
	public void tick() {
	    if(offsetX > maxX) {
	        offsetX = maxX;
        }
		if(offsetY > maxY) {
			offsetY = maxY;
		}
		tickMoving();
		tickWorldBlocks();
		tickWorldEnemies();
		tickPlayer();
	}

	public void tickMoving() {
		movingX = offsetX > 0;
		movingY = offsetY > 0;
	}

	public void init(StateLevel stateLevel) {
		this.stateLevel = stateLevel;
		utils = new CameraUtils(handler, this, stateLevel);
		maxX = stateLevel.getWorldXSize() - handler.getCanvasWidth();
		maxY = stateLevel.getWorldYSize();
		ConsoleMSG.INFO.info(Integer.toString(maxX), this);
	}

	private void tickWorldBlocks() {
		for(AbstractBlock b : stateLevel.getWorldBlocks()) {
			if(movingX) {
				b.setX(b.getStartX() - offsetX);
				b.setX(b.getX() - b.getX() % 1);
			} else {
				b.setX(b.getStartX());
			}

			b.getBoundingBox().setX(b.getX());

			if(movingY) {
				b.setY(b.getStartY() + offsetY);
			} else {
				b.setY(b.getStartY());
			}

			b.getBoundingBox().setY(b.getY());
		}
	}

	private void tickWorldEnemies() {
		if(!movingX && !movingY) {
			return;
		}
		for(AbstractEnemy e : stateLevel.getWorldEnemies().values()) {
			e.updateCurrentPosition();
			if(movingX) {
				e.setX(e.getCurrentX() - this.toMoveX);
			}
			if(movingY) {
				e.setY(e.getCurrentY() - this.toMoveY);
			}
			e.getBoundingBox().setX(e.getX());
			e.getBoundingBox().setY(e.getY());
			e.tickSunkInBlock();
		}
	}
	
	private void tickPlayer() {
		
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public float getOffsetX() {
		return offsetX;
	}
	
	public float getOffsetY() {
		return offsetY;
	}

    public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}
	
	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
		if(this.offsetY < 0) {
			this.offsetY = 0;
		}
	}

	public void setToMoveX(float toMoveX) {
		this.toMoveX = toMoveX;
	}

	public void setToMoveY(float toMoveY) {
		this.toMoveY = toMoveY;
	}

	public float getToMoveX() {
		return toMoveX;
	}

	public boolean isMovingX() {
		return movingX;
	}

	public boolean isMovingY() {
		return movingY;
	}
}

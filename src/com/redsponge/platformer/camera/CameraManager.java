package com.redsponge.platformer.camera;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.entity.player.EntityPlayer;

public class CameraManager {
	
	private float offsetX, offsetY;
	
	private Handler handler;
	private StateLevel stateLevel;
	
	public CameraManager(Handler handler, StateLevel stateLevel) {
		this.handler = handler;
		this.stateLevel = stateLevel;
		offsetX = 0;
		offsetY = 0;
	}
	
	public void tick() {
		tickWorldBlocks();
		tickPlayer();
	}
	
	private void tickWorldBlocks() {
		for(AbstractBlock b : stateLevel.getWorldBlocks()) {
			b.setX(b.getStartX() - offsetX);
			b.getBoundingBox().setX(b.getX());
		}
	}
	
	private void tickPlayer() {
		EntityPlayer p = stateLevel.getPlayer();
		if(p.getX() < handler.getCanvasWidth() / 2 || offsetX + p.getSpeedX() < 0) {
			p.moveX(p.getBoundingBox(), true);
			if(offsetX < 0) {
				offsetX = 0;
			}
		} else {
			if(p.touchingBlocks(p.getBoundingBox())) {
				return;
			}
			offsetX += p.getSpeedX();
			p.setX(handler.getCanvasWidth()/2);
			p.getBoundingBox().setX(handler.getCanvasWidth()/2 + p.getBoundingBox().getWidth()/2);
		}
	}
	
	public float getOffsetX() {
		return offsetX;
	}
	
	public float getOffsetY() {
		return offsetY;
	}
	
}

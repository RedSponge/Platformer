package com.redsponge.platformer.camera;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.world.block.AbstractBlock;

public class CameraManager {
	
	private float offsetX, offsetY;
	
	@SuppressWarnings("unused")
	private Handler handler;
	private StateLevel stateLevel;
	public CameraUtils utils;
	
	public CameraManager(Handler handler) {
		this.handler = handler;
		offsetX = 0;
		offsetY = 0;
	}
	
	public void tick() {
		tickWorldBlocks();
		tickPlayer();
	}
	
	public void init(StateLevel stateLevel) {
		this.stateLevel = stateLevel;
		utils = new CameraUtils(handler, this, stateLevel);
	}
	
	private void tickWorldBlocks() {
		for(AbstractBlock b : stateLevel.getWorldBlocks()) {
			b.setX(b.getStartX() - offsetX);
			b.getBoundingBox().setX(b.getX());
		}
	}
	
	private void tickPlayer() {
		
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
	}
	
}

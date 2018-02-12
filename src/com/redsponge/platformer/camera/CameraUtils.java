package com.redsponge.platformer.camera;

import java.util.ArrayList;
import java.util.List;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.world.block.AbstractBlock;

public class CameraUtils {
	
	private CameraManager cameraManager;
	private Handler handler;
	private StateLevel stateLevel;
	
	public CameraUtils(Handler handler, CameraManager cameraManager, StateLevel stateLevel) {
		this.handler = handler;
		this.cameraManager = cameraManager;
		this.stateLevel = stateLevel;
	}
	
	public List<AbstractBlock> getOnScreenWorldBlocks() {
		List<AbstractBlock> blocks = new ArrayList<AbstractBlock>();
		for(AbstractBlock b : stateLevel.getWorldBlocks()) {
			if(b.getX()+b.getWidth() > 0) {
				if((b.getX()) - cameraManager.getOffsetX() < handler.getCanvasWidth()) {
					blocks.add(b);
				}
			}
		}
		return blocks;
	}
	
}
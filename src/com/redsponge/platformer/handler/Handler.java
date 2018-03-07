package com.redsponge.platformer.handler;

import com.redsponge.platformer.Platformer;
import com.redsponge.platformer.camera.CameraManager;
import com.redsponge.platformer.input.KeyManager;
import com.redsponge.platformer.io.FileHandler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.world.entity.player.EntityPlayer;
import com.redsponge.redutils.display.GraphicsDisplay;

public class Handler {
	
	private Platformer game;
	
	public Handler(Platformer game) {
		this.game = game;
	}

	public GraphicsDisplay getDisplay() {
		return game.getDisplay();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public int getFrameWidth() {
		return game.getDisplay().getFrame().getWidth();
	}
	
	public int getFrameHeight() {
		return game.getDisplay().getFrame().getHeight();
	}
	
	public int getCanvasWidth() {
		return game.getDisplay().getCanvas().getWidth();
	}
	
	public int getCanvasHeight() {
		return game.getDisplay().getCanvas().getHeight();
	}
	
	public CameraManager getCameraManager() {
		return game.getCameraManager();
	}
	
	public FileHandler getFileHandler() {
		return game.getFileHandler();
	}
	
	public EntityPlayer getPlayer() {
		try {
			return ((StateLevel)StateManager.getCurrentState()).getPlayer();
		} catch (Exception e) {
			return null;
		}
	}
	
}

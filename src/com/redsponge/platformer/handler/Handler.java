package com.redsponge.platformer.handler;

import com.redsponge.platformer.Platformer;
import com.redsponge.platformer.input.KeyManager;
import com.redsponge.redutils.display.GameDisplay;

public class Handler {
	
	private Platformer game;
	
	public Handler(Platformer game) {
		this.game = game;
	}
	
	public GameDisplay getDisplay() {
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
	
}

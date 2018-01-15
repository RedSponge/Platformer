package com.redsponge.platformer.handler;

import com.redsponge.platformer.Platformer;
import com.redsponge.redutils.display.GameDisplay;

public class Handler {
	
	private Platformer game;
	
	public Handler(Platformer game) {
		this.game = game;
	}
	
	public GameDisplay getDisplay() {
		return game.getDisplay();
	}
	
}

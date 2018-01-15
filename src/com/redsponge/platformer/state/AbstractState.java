package com.redsponge.platformer.state;

import java.awt.Graphics;

import com.redsponge.platformer.handler.Handler;

public abstract class AbstractState {
	
	protected Handler handler;
	
	public AbstractState(Handler handler) {
		this.handler = handler;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
}

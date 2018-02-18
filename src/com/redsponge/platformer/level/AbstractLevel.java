package com.redsponge.platformer.level;

import com.redsponge.platformer.handler.Handler;

public abstract class AbstractLevel {
	protected Handler handler;
	
	public AbstractLevel(Handler handler) {
		this.handler = handler;
	}
	
	public abstract int[][] getLevelBlocks();

	public abstract int getBlockSize();
	
	public int PLAYER_START_X;
	public int PLAYER_START_Y;
}

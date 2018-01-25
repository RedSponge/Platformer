package com.redsponge.platformer.world.block;

import com.redsponge.platformer.handler.Handler;

public class BlockBrickPassable extends BlockBrick {

	public BlockBrickPassable(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
	}
	
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public boolean isInFront() {
		return true;
	}

}

package com.redsponge.platformer.world.block;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.material.BlockMaterial;

public class BlockGlass extends AbstractBlock {

	public BlockGlass(Handler handler, int x, int y, int width, int height) {
		super(handler, BlockMaterial.GLASS, "block_glass", x, y, width, height);
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public boolean isInFront() {
		return true;
	}
	
}

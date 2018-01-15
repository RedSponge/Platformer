package com.redsponge.platformer.world.block;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.material.BlockMaterial;

public class BlockBrick extends AbstractBlock {

	public BlockBrick(Handler handler, int x, int y, int width, int height) {
		super(handler, BlockMaterial.BRICK, "block_brick", x, y, width, height);
	}
	
}

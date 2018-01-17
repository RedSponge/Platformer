package com.redsponge.platformer.world.block;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.material.BlockMaterial;

public class BlockDirt extends AbstractBlock {

	public BlockDirt(Handler handler, int x, int y, int width, int height) {
		super(handler, BlockMaterial.GRASS, "block_grass", x, y, width, height);
	}

}

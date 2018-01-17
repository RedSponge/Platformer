package com.redsponge.platformer.world.block;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.material.BlockMaterial;

public class BlockGrass extends AbstractBlock {

	public BlockGrass(Handler handler, int x, int y, int width, int height) {
		super(handler, BlockMaterial.DIRT, "block_dirt", x, y, width, height);
	}

}

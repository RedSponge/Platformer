package com.redsponge.platformer.world.block;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.material.BlockMaterial;

public class BlockLeaves extends AbstractBlock{
	
	public BlockLeaves(Handler handler, int x, int y, int width, int height) {
		super(handler, BlockMaterial.LEAVES, "block_leaves", x, y, width, height);
	}
	
}

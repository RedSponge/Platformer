package com.redsponge.platformer.world.block;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.material.BlockMaterial;

public class BlockLog extends AbstractBlock {
	
	public BlockLog(Handler handler, int x, int y, int width, int height) {
		super(handler, BlockMaterial.LOG, "block_log", x, y, width, height);
	}
	
}

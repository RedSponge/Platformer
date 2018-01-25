package com.redsponge.platformer.world.block;

import java.awt.Graphics;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.material.BlockMaterial;

public class BlockAir extends AbstractBlock {

	public BlockAir(Handler handler, int x, int y, int width, int height) {
		super(handler, BlockMaterial.AIR, "air", x, y, width, height);
	}
	
	public boolean isSolid() {
		return false;
	}
	public void render(Graphics g) {
		return;
	}

}

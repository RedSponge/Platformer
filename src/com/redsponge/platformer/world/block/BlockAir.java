package com.redsponge.platformer.world.block;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.material.BlockMaterial;

import java.awt.*;

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
	public boolean isRenderBoundingBox() {return false;}
}

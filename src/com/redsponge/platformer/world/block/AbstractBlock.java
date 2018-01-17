package com.redsponge.platformer.world.block;

import java.awt.Graphics;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.BoundingBoxUser;
import com.redsponge.platformer.world.entity.BoundingBox;
import com.redsponge.platformer.world.material.BlockMaterial;

public abstract class AbstractBlock extends BoundingBoxUser {
	
	protected BlockMaterial material;
	protected Handler handler;
	
	protected static boolean renderTextures = true;
	
	protected String blockId;
	
	public AbstractBlock(Handler handler, BlockMaterial material, String blockId, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		this.handler = handler;
		this.material = material;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.blockId = blockId;
	}
	
	public void render(Graphics g) {
		if(renderTextures) {
			g.drawImage(material.getTexture(), (int) x, (int) y, width, height, null);
		} else {
			g.setColor(material.getColor());
			g.fillRect((int) x, (int) y, width, height);
		}
	}
	
	public boolean isSolid() {
		return true;
	}
	
	public BlockMaterial getMaterial() {
		return material;
	}
	
	public String getBlockId() {
		return blockId;
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
}

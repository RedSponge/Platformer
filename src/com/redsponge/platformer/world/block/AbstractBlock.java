package com.redsponge.platformer.world.block;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.BoundingBox;
import com.redsponge.platformer.world.BoundingBoxUser;
import com.redsponge.platformer.world.material.BlockMaterial;

import java.awt.*;

public abstract class AbstractBlock extends BoundingBoxUser {
	
	protected BlockMaterial material;
	protected Handler handler;
	
	private float startX, startY;
	
	protected static boolean renderTextures = true;
	
	protected String blockId;
	
	public AbstractBlock(Handler handler, BlockMaterial material, String blockId, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		this.handler = handler;
		this.material = material;
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
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
	
	public boolean isInFront() {
		return false;
	}

	public boolean isRenderBoundingBox() {
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
	
	public float getStartX() {
		return startX;
	}
	
	public float getStartY() {
		return startY;
	}
}

package com.redsponge.platformer.world.block;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.material.BlockMaterial;

public abstract class AbstractBlock {
	
	protected int x, y, width, height;
	protected BlockMaterial material;
	protected Handler handler;
	
	protected static boolean renderTextures = true;
	
	protected String blockId;
	
	public AbstractBlock(Handler handler, BlockMaterial material, String blockId, int x, int y, int width, int height) {
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
			g.drawImage(material.getTexture(), x, y, width, height, null);
		} else {
			g.setColor(material.getColor());
			g.fillRect(x, y, width, height);
		}
	}
	
	public boolean isSolid() {
		return true;
	}
	
	public BlockMaterial getMaterial() {
		return material;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getBlockId() {
		return blockId;
	}
	
	public Rectangle asRectangle() {
		return new Rectangle(x, y, width, height);
	}
}

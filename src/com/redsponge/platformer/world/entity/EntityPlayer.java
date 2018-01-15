package com.redsponge.platformer.world.entity;

import java.awt.Graphics;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.io.AssetsHandler;

public class EntityPlayer extends AbstractLivingEntity {

	private int size;
	
	public EntityPlayer(Handler handler, int x, int y, int size) {
		super(handler, x, y, size, size);
		direction = Facing.RIGHT;
		this.size = size;
	}

	public void tick() {
		super.tick();
	}

	public void render(Graphics g) {
		g.drawImage(AssetsHandler.getPlayerAssets().get("facing_right"), x, y, size, size*2, null);
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}

}

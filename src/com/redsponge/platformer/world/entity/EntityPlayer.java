package com.redsponge.platformer.world.entity;

import java.awt.Graphics;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.io.AssetsHandler;

public class EntityPlayer extends AbstractLivingEntity {

	private int size;
	
	public EntityPlayer(Handler handler, int x, int y, int size) {
		super(handler, x, y, size, size*2);
		direction = Facing.RIGHT;
		this.size = size;
		this.jumpHeight = 50;
		this.isGravityApplied = true;
	}

	public void tick() {
		tickKeys();
		super.tick();
	}
	
	public void tickKeys() {
		speedX = 0;
		speedY = 0;
		if(handler.getKeyManager().keyList.get("jump")) {
			jump();
		} else if(handler.getKeyManager().keyList.get("move_right")) {
			speedX += 5;
		} else if(handler.getKeyManager().keyList.get("move_left")) {
			speedX -= 5;
		}
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

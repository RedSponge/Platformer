package com.redsponge.platformer.world.entity;

import java.awt.Graphics;

import com.redsponge.platformer.handler.Handler;

public abstract class AbstractEntity {
	
	protected int x, y, width, height;
	protected float speedX, speedY;
	protected boolean onGround;
	protected float fallingSpeed;
	protected float fallingMultiplier;
	
	protected boolean isGravityApplied;
	
	protected Handler handler;
	
	public AbstractEntity(Handler handler, int x, int y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isGravityApplied = true;
		this.fallingMultiplier = 1.05F;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
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
	
}

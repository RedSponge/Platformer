package com.redsponge.platformer.world;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.entity.BoundingBox;

public class BoundingBoxUser {
	
	protected float x, y;
	protected int width, height;
	protected Handler handler;
	
	protected BoundingBox boundingBox;
	
	public BoundingBoxUser(Handler handler, float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		boundingBox = new BoundingBox(handler, this);
	}
	
	public void tick() {
		boundingBox.tick();
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
}

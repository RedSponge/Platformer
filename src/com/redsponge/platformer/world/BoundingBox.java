package com.redsponge.platformer.world;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.redsponge.platformer.handler.Handler;

public class BoundingBox {
	
	private float x, y;
	private int width, height;
	private Color c;
	
	private Handler handler;
	private BoundingBoxUser user;
	
	public BoundingBox(Handler handler, BoundingBoxUser user) {
		this.x = user.getX();
		this.y = user.getY();
		this.width = user.getWidth();
		this.height = user.getHeight();
		this.handler = handler;
		this.user = user;
		this.c = Color.RED;
	}
	
	public BoundingBox(Handler handler, BoundingBoxUser user, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		this.user = user;
		this.c = Color.RED;
	}
	
	public void tick() {
		this.x = user.getX();
		this.y = user.getY();
		this.width = user.getWidth();
		this.height = user.getHeight();
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(c);
		g2.setStroke(new BasicStroke(1));
		g2.drawRect((int) x, (int) y, width, height);
	}
	
	public int getLeft() {
		return (int) x;
	}
	
	public int getTop() {
		return (int) y;
	}
	
	public int getRight() {
		return (int) x + width;
	}
	
	public int getBottom() {
		return (int) y + height;
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
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Rectangle asRectangle() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void setColor(Color c) {
		this.c = c;
	}
	
	public BoundingBox clone() {
		return new BoundingBox(handler, user, (int) x, (int) y, width, height);
	}
	
}

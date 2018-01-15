package com.redsponge.platformer.world.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.utils.MathUtils;
import com.redsponge.platformer.world.block.AbstractBlock;

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
	
	public void tick() {
		StateLevel state = (StateLevel) StateManager.getCurrentState();
		updateOnGround(state.getWorldBlocks());
	}
	
	public void updateOnGround(List<AbstractBlock> worldBlocks) {
		onGround = true;
		for(AbstractBlock b : worldBlocks) {
			if(MathUtils.twoRectCollision(b.asRectangle(), this.asRectangle())) {
				this.y = b.getY() - this.height;
				return;
			}
		}
		onGround = false;
		//System.out.println(onGround);		
	}
	
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
	
	public Rectangle asRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
}

package com.redsponge.platformer.world.entity;

import java.awt.Graphics;
import java.util.List;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.utils.MathUtils;
import com.redsponge.platformer.world.BoundingBox;
import com.redsponge.platformer.world.BoundingBoxUser;
import com.redsponge.platformer.world.block.AbstractBlock;

public abstract class AbstractEntity extends BoundingBoxUser {
	
	protected float speedX, speedY;
	protected boolean onGround;
	protected float fallingSpeed;
	protected float fallingMultiplier;
	
	protected boolean isGravityApplied;
	
	protected int dontTickOnGroundFor;
	
	public AbstractEntity(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		this.isGravityApplied = true;
		this.boundingBox = new BoundingBox(handler, this);
		this.fallingMultiplier = 1.05F;
	}
	
	public void tick() {
		super.tick();
		if(dontTickOnGroundFor == 0) {
			StateLevel state = (StateLevel) StateManager.getCurrentState();
			updateOnGround(state.getWorldBlocks());
		}
	}
	
	public void updateOnGround(List<AbstractBlock> worldBlocks) {
		for(AbstractBlock b : worldBlocks) {
			if(MathUtils.onTopOfBlock(this, b)) {
				if(!onGround) {
					this.y = b.getBoundingBox().getTop()-this.height;
					System.out.println("DETECT!");
				}
				onGround = true;
				return;
			}
		}
		onGround = false;		
	}
	
	public abstract void render(Graphics g);

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	
}

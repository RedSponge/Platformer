package com.redsponge.platformer.world.entity;

import com.redsponge.platformer.handler.Handler;

public abstract class AbstractLivingEntity extends AbstractEntity {
	
	protected Facing direction;
	
	protected boolean jumping;
	
	public AbstractLivingEntity(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
	}
	
	public void tick() {
		tickGravity();
	}
	
	public void tickGravity() {
		
		
		if(onGround) {
			fallingSpeed = 0;
		} else {
			if(fallingSpeed == 0) {
				fallingSpeed += 0.5;
			} else {
				fallingSpeed *= fallingMultiplier;
			}
		}
		if(!jumping) {
			this.y += fallingSpeed;
		}
	}
	
}

package com.redsponge.platformer.world.entity;

import com.redsponge.platformer.handler.Handler;

public abstract class AbstractLivingEntity extends AbstractEntity {
	
	protected Facing direction;
	
	protected boolean jumping;
	protected float jumpHeight;
	protected float jumpingSpeed;
	
	protected float jumpingMultiplier;
	
	protected int jumpStartY;
	
	public AbstractLivingEntity(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		jumpHeight = 20;
		jumpingSpeed = 0;
		jumpingMultiplier = 1.1F;
	}
	
	public void tick() {
		move();
		tickGravity();
		if(jumping) {
			tickJumping();
		}
	}
	
	public void tickJumping() {
		if(jumpStartY - y < jumpHeight) {
			if(jumpingSpeed == 0) {
				jumpingSpeed += 0.5;
			} else {
				jumpingSpeed *= jumpingMultiplier;
			}
			this.y += jumpingSpeed;
		} else {
			jumping = false;
		}
	}

	public void move() {
		x += speedX;
	}
	
	public void tickGravity() {	
		if(onGround || jumping) {
			fallingSpeed = 0;
		} else if(!jumping) {
			if(fallingSpeed == 0) {
				fallingSpeed += 0.5;
			} else {
				fallingSpeed *= fallingMultiplier;
			}
			this.y += fallingSpeed;
		}
	}
	
	public void jump() {
		if(jumping) {
			return;
		}
		jumping = true;
		jumpStartY = y;
	}
	
}

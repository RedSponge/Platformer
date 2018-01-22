package com.redsponge.platformer.world.entity;

import java.util.List;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.utils.MathUtils;
import com.redsponge.platformer.world.BoundingBox;
import com.redsponge.platformer.world.block.AbstractBlock;

public abstract class AbstractLivingEntity extends AbstractEntity {
	
	protected Facing direction;
	protected Action action;
	
	protected boolean jumping;
	protected float jumpHeight;
	protected float jumpingSpeed;
	
	protected float jumpingMultiplier;
	
	protected int jumpStartY;
	
	public AbstractLivingEntity(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		jumpHeight = 20;
		jumpingSpeed = 0;
		jumpingMultiplier = 1.05F;
	}
	
	public void tick() {
		super.tick();
		//move();
		tickGravity();
		if(jumping) {
			tickJumping();
		}
	}
	
	public void tickJumping() {
		if(jumpStartY - boundingBox.getY() < jumpHeight) {
			if(jumpingSpeed == 0) {
				jumpingSpeed += (jumpHeight/5);
			} else {
				jumpingSpeed -= (jumpingSpeed*jumpingMultiplier)/4;
			}
			if(jumpingSpeed < 0.1) {
				stopJumping();
			} else if(MathUtils.blockAbove(handler, this) != null) {
				stopJumping();
				dontTickOnGroundFor = 10;
			}
			speedY = jumpingSpeed * -1;
		} else {
			stopJumping();
		}
	}
	
	private void stopJumping() {
		jumpingSpeed = 0;
		jumping = false;
	}
	
	@Override
	public void updateOnGround(List<AbstractBlock> worldBlocks) {
		if(jumping) {
			return;
		}
		super.updateOnGround(worldBlocks);
	}

	public void move() {
		moveX();
		moveY();
	}
	
	private void moveX() {
		BoundingBox xTester = boundingBox.clone();
		xTester.setX(xTester.getX() + speedX);
		for(AbstractBlock b : ((StateLevel)StateManager.getCurrentState()).getWorldBlocks()) {
			if(MathUtils.twoRectCollision(xTester.asRectangle(), b.getBoundingBox().asRectangle())) {
				return;
			}
		}
		x += speedX;
	}
	
	private void moveY() {
		BoundingBox yTester = boundingBox.clone();
		yTester.setX(yTester.getX() + speedY);
		for(AbstractBlock b : ((StateLevel)StateManager.getCurrentState()).getWorldBlocks()) {
			if(MathUtils.twoRectCollision(yTester.asRectangle(), b.getBoundingBox().asRectangle())) {
				return;
			}
		}
		y += speedY;
	}
	
	public void tickGravity() {	
		if(!isGravityApplied) {
			return;
		}
		if(onGround || jumping) {
			fallingSpeed = 0;
			return;
		} else if(!jumping) {
			if(fallingSpeed == 0) {
				fallingSpeed += 0.5;
			} else {
				fallingSpeed *= fallingMultiplier;
			}
			speedY = fallingSpeed;
		}
	}
	
	public void jump() {
		if(!onGround) {
			return;
		}
		speedY = 0;
		jumping = true;
		jumpStartY = boundingBox.getTop();
		onGround = false;
	}
	
}

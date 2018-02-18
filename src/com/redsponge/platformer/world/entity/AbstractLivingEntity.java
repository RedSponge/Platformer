package com.redsponge.platformer.world.entity;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.settings.Settings;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.utils.MathUtils;
import com.redsponge.platformer.world.BoundingBox;
import com.redsponge.platformer.world.block.AbstractBlock;

import java.awt.*;
import java.util.List;

public abstract class AbstractLivingEntity extends AbstractEntity {
	
	protected Facing direction;
	protected Action action;
	
	protected boolean jumping;
	protected float jumpHeight;
	protected float jumpingSpeed;
	
	protected float speed;
	
	protected float jumpingMultiplier;
	
	protected int jumpStartY;
	protected boolean renderBoundingBox;
	protected String currentAnimationId;
	
	protected boolean outsideOfWorld;
	
	public AbstractLivingEntity(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		jumpHeight = 1;
		jumpingSpeed = 0;
		jumpingMultiplier = 1.05F;
		speed = 2;
		currentAnimationId = "";
		renderBoundingBox = false;
	}
	
	public void tick() {
		super.tick();
		move();
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
			} else if(MathUtils.blockAbove(handler, this.boundingBox) != null) {
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
		moveX(boundingBox);
		moveY();
		tickOutsideOfWorld();
	}
	
	private void tickOutsideOfWorld() {
		if(y > handler.getCanvasHeight()) {
			outsideOfWorld = true;
			return;
		}
		outsideOfWorld = false;
	}

	public void moveX(BoundingBox box) {
		moveX(box, true);
	}
	
	public void moveX(BoundingBox box, boolean move) {
		//ConsoleMSG.INFO.info(Float.toString(x), this);
		if(touchingBlocks(box)) {
			return;
		}
		if(move) {
			this.x += this.speedX;
		}
	}
	
	public boolean touchingBlocks(BoundingBox box, boolean checkScreen) {
		BoundingBox xTester = box.clone();
		xTester.setX(xTester.getX() + speedX);
		if(this.x < 0 && checkScreen) {
			return true;
		}
		for(AbstractBlock b : ((StateLevel)StateManager.getCurrentState()).getWorldBlocks()) {
			if(MathUtils.twoRectCollision(xTester.asRectangle(), b.getBoundingBox().asRectangle()) && b.isSolid()) {
				return true;
			}
		}
		return false;
	}

	public boolean touchingBlocks(BoundingBox box) {
	    return touchingBlocks(box, true);
    }
	
	private void moveY() {
		y += speedY;
		if(onGround && onTopOf != null && onTopOf.isSolid()) {
			y = onTopOf.getBoundingBox().getTop() - height; 
		}
	}
	
	public void render(Graphics g) {
		if(renderBoundingBox) {
			boundingBox.render(g);
		}
	}
	
	public void tickGravity() {	
		updateOnGround(((StateLevel)StateManager.getCurrentState()).getWorldBlocks());
		if(!isGravityApplied) {
			fallingSpeed = 0;
			return;
		}
		if(onGround || jumping) {
			fallingSpeed = 0;
			return;
		} else if(!jumping) {
			if(fallingSpeed == 0) {
				fallingSpeed += 1;
			} else {
				fallingSpeed *= fallingMultiplier;
				if(fallingSpeed > fallingSpeedMax) {
					fallingSpeed = fallingSpeedMax;
				}
			}
			speedY = fallingSpeed;
		}
	}
	
	public void jump() {
		if(!onGround && !Settings.allowFlyJump) {
			return;
		}
		speedY = 0;
		jumping = true;
		jumpStartY = boundingBox.getTop();
		onGround = false;
	}
	
	public Facing getDirection() {
		return direction;
	}
	
	public Action getAction() {
		return action;
	}
	
	public void doRenderBoundingBox(boolean renderBoundingBox) {
		this.renderBoundingBox = renderBoundingBox;
	}
	
	public boolean isRenderBoundingBox() {
		return renderBoundingBox;
	}
	
}

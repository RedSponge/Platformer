package com.redsponge.platformer.world.entity.enemy;

import java.util.List;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.utils.MathUtils;
import com.redsponge.platformer.world.BoundingBox;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.entity.AbstractLivingEntity;
import com.redsponge.platformer.world.entity.Facing;

public abstract class AbstractEnemy extends AbstractLivingEntity {

	protected float currentX;
	protected float currentY;
	
	protected boolean hasBeenOnGround;
	
	public AbstractEnemy(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		isGravityApplied = true;
		speedX = 3;
		direction = Facing.RIGHT;
		updateCurrentPosition();
		hasBeenOnGround = false;
	}
	
	public void tick() {
		boundingBox.tick();
		move();
		tickGravity();
	}
	
	private void turn() {
		speedX *= -1;
		direction = direction.getOpposite();
	}
	
	public void moveX(BoundingBox box) {
		if(touchingBlocks(box)) {
			if(onGround) {
				turn();
			}
		}
		x += speedX;
	}
	
	public void updateOnGround(List<AbstractBlock> worldBlocks) {
		if(jumping) {
			return;
		}
		for(AbstractBlock b : worldBlocks) {
			if(MathUtils.onTopOfBlock(this, b)) {
				onTopOf = b;
				if(!hasBeenOnGround) {
					turn();
				}
				hasBeenOnGround = true;
				onGround = true;
				return;
			}
		}
		onTopOf = null;
		onGround = false;
		hasBeenOnGround = false;
	}
	
	public void updateCurrentPosition() {
		currentX = x;
		currentY = y;
	}
	
	public float getCurrentX() {
		return currentX;
	}
	
	public float getCurrentY() {
		return currentY;
	}
}

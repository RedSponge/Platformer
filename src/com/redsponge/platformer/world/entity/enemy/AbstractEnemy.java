package com.redsponge.platformer.world.entity.enemy;

import java.awt.Graphics;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.entity.AbstractLivingEntity;

public abstract class AbstractEnemy extends AbstractLivingEntity {

	public AbstractEnemy(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		isGravityApplied = true;
		speedX = 3;
	}
	
	public void tick() {
		if(touchingBlocks(boundingBox)) {
			direction = direction.getOpposite();
			speedX *= -1;
		}
		tickGravity();
		move();
	}
	
	public void render(Graphics g) {
		//
	}

}

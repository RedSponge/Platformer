package com.redsponge.platformer.world.entity.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.io.AssetsHandler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.world.entity.AbstractLivingEntity;
import com.redsponge.platformer.world.entity.Action;
import com.redsponge.platformer.world.entity.Facing;

public class EntityPlayer extends AbstractLivingEntity {

	private int size;
	
	public EntityPlayer(Handler handler, int x, int y, int size) {
		super(handler, x, y, size, size*2);
		direction = Facing.RIGHT;
		this.size = size;
		this.jumpHeight = 100;
		this.isGravityApplied = true;
		this.action = Action.IDLE;
		boundingBox.setColor(Color.GREEN);
	}

	public void tick() {
		boundingBox.tick();
		tickKeys();
		PlayerUtils.updateBoundingBox(this);
		move();
		tickGravity();
		updateOnGround(((StateLevel)StateManager.getCurrentState()).getWorldBlocks());
		if(jumping) {
			tickJumping();
		}
	}
	
	public void tickKeys() {
		speedX = 0;
		speedY = 0;
		if(handler.getKeyManager().keyList.get("jump")) {
			jump();
		}
		if(handler.getKeyManager().keyList.get("move_right")) {
			speedX += 5;
			direction = Facing.RIGHT;
			action = Action.WALKING;
		} else if(handler.getKeyManager().keyList.get("move_left")) {
			speedX -= 5;
			direction = Facing.LEFT;
			action = Action.WALKING;
		} else {
			action = Action.IDLE;
		}
	}

	public void render(Graphics g) {
		g.drawImage(PlayerUtils.getSpriteByFacing(direction, action), (int) x, (int) y, size, size*2, null);
		boundingBox.render(g);
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public Action getAction() {
		return action;
	}
	
	public Facing getFacing() {
		return direction;
	}
}


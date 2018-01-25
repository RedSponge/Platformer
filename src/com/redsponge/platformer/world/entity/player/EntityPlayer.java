package com.redsponge.platformer.world.entity.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.redsponge.platformer.gfx.animation.Animation;
import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.io.AssetsHandler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.world.entity.AbstractLivingEntity;
import com.redsponge.platformer.world.entity.Action;
import com.redsponge.platformer.world.entity.Facing;
import com.redsponge.redutils.console.ConsoleMSG;

public class EntityPlayer extends AbstractLivingEntity {

	private int size;
	
	private Animation ANIMATION_IDLE;
	private final int ANIMATION_IDLE_SPEED = 30;
	
	private Animation currentAnimation;
	
	public EntityPlayer(Handler handler, int x, int y, int size) {
		super(handler, x, y, size, size*2);
		direction = Facing.RIGHT;
		this.size = size;
		this.jumpHeight = 100;
		this.isGravityApplied = true;
		boundingBox.setColor(Color.RED);
		registerAnimationAssets();
		setCurrentAnimation(Action.IDLE);
		speed = 3;
	}

	private void registerAnimationAssets() {
		
		ConsoleMSG.ADD.info("Registering Player Animation Assets!");
		
		String pathMain = "/assets/textures/entities/player";
		
		ANIMATION_IDLE = new Animation(this, new BufferedImage[]{
				AssetsHandler.getImage(pathMain + "/idle/right/idle_1.png"),
				AssetsHandler.getImage(pathMain + "/idle/right/idle_2.png"),
				AssetsHandler.getImage(pathMain + "/idle/right/idle_3.png"),
				AssetsHandler.getImage(pathMain + "/idle/right/idle_4.png")
		}, new BufferedImage[]{
				AssetsHandler.getImage(pathMain + "/idle/left/idle_1.png"),
				AssetsHandler.getImage(pathMain + "/idle/left/idle_2.png"),
				AssetsHandler.getImage(pathMain + "/idle/left/idle_3.png"),
				AssetsHandler.getImage(pathMain + "/idle/left/idle_4.png")
		}, ANIMATION_IDLE_SPEED);
		
		ConsoleMSG.ADD.info("Successfully Registered Player Animation Assets!");
		
	}
	
	public void tick() {
		boundingBox.tick();
		tickKeys();
		PlayerUtils.updateBoundingBox(this);
		currentAnimation.tick();
		tickGravity();
		updateOnGround(((StateLevel)StateManager.getCurrentState()).getWorldBlocks());
		if(jumping) {
			tickJumping();
		}
		move();
		if(outsideOfWorld) {
			x = ((StateLevel)StateManager.getCurrentState()).getLoadedLevel().PLAYER_START_X;
			y = ((StateLevel)StateManager.getCurrentState()).getLoadedLevel().PLAYER_START_Y;
		}
	}
	
	public void tickKeys() {
		speedX = 0;
		speedY = 0;
		if(handler.getKeyManager().keyList.get("jump")) {
			jump();
		}
		if(handler.getKeyManager().keyList.get("move_right")) {
			speedX += speed;
			direction = Facing.RIGHT;
			action = Action.WALKING;
		} else if(handler.getKeyManager().keyList.get("move_left")) {
			speedX -= speed;
			direction = Facing.LEFT;
			setCurrentAnimation(Action.WALKING);
			action = Action.WALKING;
		} else {
			setCurrentAnimation(Action.IDLE);
			action = Action.IDLE;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(currentAnimation.getCurrentFrame(), (int) x, (int) y, width, height, null);
		boundingBox.render(g);
	}
	
	private void setCurrentAnimation(Action a) {
		if(a == action) {
			return;
		}
		if(a == Action.IDLE) {
			currentAnimation = ANIMATION_IDLE;
		} else if(a == Action.WALKING) {
			currentAnimation = ANIMATION_IDLE;
		}
		currentAnimation.reset();
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


package com.redsponge.platformer.world.entity.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.redsponge.platformer.gfx.animation.Animation;
import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.io.AssetsHandler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.utils.MathUtils;
import com.redsponge.platformer.world.BoundingBox;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.entity.AbstractLivingEntity;
import com.redsponge.platformer.world.entity.Action;
import com.redsponge.platformer.world.entity.Facing;
import com.redsponge.redutils.console.ConsoleMSG;

public class EntityPlayer extends AbstractLivingEntity {

	private int size;
	
	private Animation ANIMATION_IDLE;
	private final int ANIMATION_IDLE_SPEED = 30;
	
	private Animation ANIMATION_WALK;
	private final int ANIMATION_WALK_SPEED = 5;
	
	private Animation ANIMATION_RUN;
	private final int ANIMATION_RUN_SPEED = 5;
	
	protected float speedRunAmplifier;
	protected float runAmplifier;
	protected float maxSpeed;
	
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
		speedRunAmplifier = 0;
		runAmplifier = 1.005f;
		maxSpeed = 4;
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
		
		ANIMATION_WALK = new Animation(this, new BufferedImage[] {
				AssetsHandler.getImage(pathMain + "/walk/right/walk_1.png"),
				AssetsHandler.getImage(pathMain + "/walk/right/walk_2.png"),
				AssetsHandler.getImage(pathMain + "/walk/right/walk_3.png"),
				AssetsHandler.getImage(pathMain + "/walk/right/walk_4.png"),
				AssetsHandler.getImage(pathMain + "/walk/right/walk_5.png"),
				AssetsHandler.getImage(pathMain + "/walk/right/walk_6.png"),
				AssetsHandler.getImage(pathMain + "/walk/right/walk_7.png"),
				AssetsHandler.getImage(pathMain + "/walk/right/walk_8.png"),
		}, new BufferedImage[] {
				AssetsHandler.getImage(pathMain + "/walk/left/walk_1.png"),
				AssetsHandler.getImage(pathMain + "/walk/left/walk_2.png"),
				AssetsHandler.getImage(pathMain + "/walk/left/walk_3.png"),
				AssetsHandler.getImage(pathMain + "/walk/left/walk_4.png"),
				AssetsHandler.getImage(pathMain + "/walk/left/walk_5.png"),
				AssetsHandler.getImage(pathMain + "/walk/left/walk_6.png"),
				AssetsHandler.getImage(pathMain + "/walk/left/walk_7.png"),
				AssetsHandler.getImage(pathMain + "/walk/left/walk_8.png"),
		}, ANIMATION_WALK_SPEED);
		
		ANIMATION_RUN = new Animation(this, new BufferedImage[] {
				AssetsHandler.getImage(pathMain + "/run/right/run_1.png"),
				AssetsHandler.getImage(pathMain + "/run/right/run_2.png"),
				AssetsHandler.getImage(pathMain + "/run/right/run_3.png"),
				AssetsHandler.getImage(pathMain + "/run/right/run_4.png"),
				AssetsHandler.getImage(pathMain + "/run/right/run_5.png"),
				AssetsHandler.getImage(pathMain + "/run/right/run_6.png"),
				AssetsHandler.getImage(pathMain + "/run/right/run_7.png"),
				AssetsHandler.getImage(pathMain + "/run/right/run_8.png"),
		}, new BufferedImage[] {
				AssetsHandler.getImage(pathMain + "/run/left/run_1.png"),
				AssetsHandler.getImage(pathMain + "/run/left/run_2.png"),
				AssetsHandler.getImage(pathMain + "/run/left/run_3.png"),
				AssetsHandler.getImage(pathMain + "/run/left/run_4.png"),
				AssetsHandler.getImage(pathMain + "/run/left/run_5.png"),
				AssetsHandler.getImage(pathMain + "/run/left/run_6.png"),
				AssetsHandler.getImage(pathMain + "/run/left/run_7.png"),
				AssetsHandler.getImage(pathMain + "/run/left/run_8.png"),
		}, ANIMATION_RUN_SPEED);
		
		
		ConsoleMSG.ADD.info("Successfully Registered Player Animation Assets!");
	}
	
	public void tick() {
		tickKeys();
		boundingBox.tick();
		PlayerUtils.updateBoundingBox(this);
		currentAnimation.tick();
		tickRunning();
		tickGravity();
		updateOnGround(((StateLevel)StateManager.getCurrentState()).getWorldBlocks());
		if(jumping) {
			tickJumping();
		}
		move();
		if(outsideOfWorld) {
			y = ((StateLevel)StateManager.getCurrentState()).getLoadedLevel().PLAYER_START_Y;
		}
	}
	
	private void tickKeys() {
		speedX = 0;
		speedY = 0;
		if(handler.getKeyManager().keyList.get("jump")) {
			jump();
		}
		if(handler.getKeyManager().keyList.get("run")) {
			 setCurrentAnimation(Action.RUNNING);
			action = Action.RUNNING;
		} else if(action == Action.RUNNING){
			action = Action.NONE;
		}
		if(handler.getKeyManager().keyList.get("move_right")) {
			speedX = (speed + speedRunAmplifier);
			direction = Facing.RIGHT;
			if(action != Action.RUNNING) {
				setCurrentAnimation(Action.WALKING);
				action = Action.WALKING;
			}
		} else if(handler.getKeyManager().keyList.get("move_left")) {
			speedX = (speed + speedRunAmplifier) * -1;
			direction = Facing.LEFT;
			if(action != Action.RUNNING) {
				setCurrentAnimation(Action.WALKING);
				action = Action.WALKING;
			}
		} else {
			setCurrentAnimation(Action.IDLE);
			action = Action.IDLE;
		}
	}
	
	private void tickRunning() {
		if(action != Action.RUNNING) {
			speedRunAmplifier = 0;
			return;
		}
		if(speedRunAmplifier == 0) {
			speedRunAmplifier = speed;
			return;
		}
		speedRunAmplifier *= runAmplifier;
		BoundingBox testB = boundingBox.clone();
		testB.setX(testB.getX() + speedX + speedRunAmplifier);
		if(touchingBlocks(testB)) {
			speedRunAmplifier /= runAmplifier;
		}
		if(speedRunAmplifier - speed > maxSpeed) {
			speedRunAmplifier = maxSpeed - speed;
		}
	}
	
	public boolean touchingBlocks(BoundingBox box, boolean doTester) {
		BoundingBox xTester = box;
		if(doTester) {
			xTester.setX(xTester.getX()+((action == Action.RUNNING)?speed+speedRunAmplifier:speedX));
		}
		if(xTester.getX() <= 0) {
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
		}
		if(a == Action.WALKING) {
			currentAnimation = ANIMATION_WALK;
		}
		if(a == Action.RUNNING) {
			currentAnimation = ANIMATION_RUN; 
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


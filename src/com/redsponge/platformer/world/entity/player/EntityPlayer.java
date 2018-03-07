package com.redsponge.platformer.world.entity.player;

import com.redsponge.platformer.GameManager;
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
import com.redsponge.platformer.world.entity.KillCause;
import com.redsponge.platformer.world.entity.enemy.AbstractEnemy;
import com.redsponge.redutils.console.ConsoleMSG;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityPlayer extends AbstractLivingEntity {

	private int size;
	
	private Animation ANIMATION_IDLE;
	private final int ANIMATION_IDLE_SPEED = 30;
	
	private Animation ANIMATION_WALK;
    private final int ANIMATION_WALK_SPEED = 5;
	
	private Animation ANIMATION_RUN;
	private final int ANIMATION_RUN_SPEED = 5;
	
	private Animation ANIMATION_DUCK;
	private final int ANIMATION_DUCK_SPEED = 30;
	
	protected float speedRunAmplifier;
	protected float runAmplifier;
	protected float maxSpeed;

	private float absoluteX;
	private float absoluteY;

	private int health;
	private int maxHealth;

	private int maxInvulnerabilityFrames;
	private int invulnerabilityFrames;
	private boolean invulnerable;

	private float opacity;
	private float invulnerabilityOpacityAdd;

	private Animation currentAnimation;
	
	private boolean running;
	private BoundingBox tester;
	
	
	public EntityPlayer(Handler handler, int x, int y, int size) {
		super(handler, x, y, size, size*2);
		direction = Facing.RIGHT;
		this.size = size;
		this.jumpHeight = 100;
		this.isGravityApplied = true;
		boundingBox.setColor(Color.RED);
		opacity = 1;
		invulnerabilityOpacityAdd = 0.1f;
		invulnerabilityFrames = 0;
		maxInvulnerabilityFrames = 100;
		invulnerable = false;
		registerAnimationAssets();
		setCurrentAnimation(Action.IDLE);
		speed = 3;
		speedRunAmplifier = 0;
		this.maxHealth = 10;
		this.health = maxHealth;
		runAmplifier = 1.015f;
		maxSpeed = 10;
		running = false;
		renderBoundingBox = false;
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
		}, ANIMATION_IDLE_SPEED, "animationIdlePlayer");
		
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
		}, ANIMATION_WALK_SPEED, "animationWalkPlayer");
		
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
		}, ANIMATION_RUN_SPEED, "animationRunPlayer");
		
		ANIMATION_DUCK = new Animation(this, new BufferedImage[] {AssetsHandler.getImage(pathMain + "/duck/right/duck_0.png")}, new BufferedImage[] {AssetsHandler.getImage(pathMain + "/duck/left/duck_0.png")}, ANIMATION_DUCK_SPEED, "animationDuckPlayer");
		
		
		ConsoleMSG.ADD.info("Successfully Registered Player Animation Assets!");
	}
	
	public void tick() {
		tickKeys();
		boundingBox.tick();
		PlayerUtils.updateBoundingBox(this);
		tickMovement();
		tickRunning();
		currentAnimation.tick();
		tickGravity();
		tickInvulnerability();
		updateOnGround(((StateLevel)StateManager.getCurrentState()).getWorldBlocks());
		if(jumping) {
			tickJumping();
		}
		move();
		updateOnGround(((StateLevel)StateManager.getCurrentState()).getWorldBlocks());
        tickFalling();
		if(outsideOfWorld) {
			y = ((StateLevel)StateManager.getCurrentState()).getLoadedLevel().PLAYER_START_Y;
		}
	}

	public void tickBoundingBox() {
		super.tickBoundingBox();
		PlayerUtils.updateBoundingBox(this);
	}

	private void tickInvulnerability() {
		if(invulnerabilityFrames > 0) {
			invulnerabilityFrames--;
		} else {
			opacity = 1;
		}
		invulnerable = invulnerabilityFrames > 0;
		if(invulnerable) {
			opacity += invulnerabilityOpacityAdd;
			if(opacity < 0 || opacity > 1) {
				opacity -= invulnerabilityOpacityAdd;
				invulnerabilityOpacityAdd *= -1;
			}
		}
	}

	public void hurt(AbstractEnemy hurter) {
        if(!invulnerable) {
        	health-=hurter.getPropertyMap().damage;
        	invulnerabilityFrames = maxInvulnerabilityFrames;
        	if(health <= 0) {
        		kill(null);
			}
		}
    }

	public void kill(KillCause cause) {
        GameManager.resetLevelState();
    }
	
	private void tickMovement() {
		if(action == Action.IDLE || action == Action.DUCKING) {
			speedX = 0;
			return;
		}
		if(action == Action.WALKING || running) {
			speedX = speed;
			if(running) {
				speedX += speedRunAmplifier;
			}
			if(direction == Facing.LEFT) {
				speedX *= -1;
			}
			return;
		}
	}
	
	public void moveX(BoundingBox box) {
		if(touchingBlocks()) { // IF TOUCHING A BLOCK
		    speedX = 0;
		    speedRunAmplifier = 0;
		    action = Action.IDLE;
		    setCurrentAnimation(action);
		    currentAnimation.tick();
			return;
		}

		if(x < handler.getCanvasWidth()/2) { // IF BEFORE SCROLLING STARTS (BEFORE MIDDLE)
            if(handler.getCameraManager().getOffsetX() >= handler.getCameraManager().getMaxX() && x < handler.getCanvasWidth()/2 && direction == Facing.RIGHT) {
                x = handler.getCanvasWidth()/2;
            }
		    else if(handler.getCameraManager().getOffsetX() > 0) {
		        handler.getCameraManager().setOffsetX(handler.getCameraManager().getOffsetX() + speedX);
		        handler.getCameraManager().setMoving(true);
            } else {
                x += speedX;
                handler.getCameraManager().setMoving(false);
                handler.getCameraManager().setOffsetX(0);
            }
		} else if(x >= handler.getCanvasWidth()/2) {
            if(handler.getCameraManager().getOffsetX() >= handler.getCameraManager().getMaxX()) {
                x += speedX;
                handler.getCameraManager().setMoving(false);
            } else if(handler.getCameraManager().getOffsetX() < 0) {
                handler.getCameraManager().setOffsetX(0);
                handler.getCameraManager().setMoving(false);
                x += speedX;
            }
            else {
                handler.getCameraManager().setOffsetX(handler.getCameraManager().getOffsetX() + speedX);
                handler.getCameraManager().setMoving(true);
            }
		}
		absoluteX = handler.getCameraManager().getOffsetX() + x;
	}

	private void tickKeys() {
		speedX = 0;
		speedY = 0;
		if(handler.getKeyManager().keyList.get("jump")) {
			jump();
		}
		if(handler.getKeyManager().keyList.get("duck")) {
			if(handler.getKeyManager().keyList.get("move_right")) {
				direction = Facing.RIGHT;
			} else if(handler.getKeyManager().keyList.get("move_left")) {
				direction = Facing.LEFT;
			}
			if(onGround) {
				action = Action.DUCKING;
				setCurrentAnimation(Action.DUCKING);
				return;
			}
		}
		if(handler.getKeyManager().keyList.get("run") && (action == Action.WALKING || running)) {
			 setCurrentAnimation(Action.RUNNING);
			 action = Action.RUNNING;
			running = true;
		} else if(running){
			action = Action.NONE;
			running = false;
		}
		if(handler.getKeyManager().keyList.get("move_right")) {
			direction = Facing.RIGHT;
			if(!running) {
				setCurrentAnimation(Action.WALKING);
				action = Action.WALKING;
			}
		} else if(handler.getKeyManager().keyList.get("move_left")) {
			direction = Facing.LEFT;
			if(!running) {
				setCurrentAnimation(Action.WALKING);
				action = Action.WALKING;
			}
		} else {
			setCurrentAnimation(Action.IDLE);
			action = Action.IDLE;
		}
	}
	
	private void tickRunning() {
		if(!running || action != Action.RUNNING) {
			speedRunAmplifier = 0;
			return;
		}
		if(speedRunAmplifier == 0) {
			speedRunAmplifier += 1;
		}
		if(speedRunAmplifier > maxSpeed - speed) {
			speedRunAmplifier = maxSpeed - speed;
		}
		speedRunAmplifier *= runAmplifier;
		BoundingBox testB = boundingBox.clone();
		testB.setX(testB.getX() + speedX + speedRunAmplifier);
		if(touchingBlocks(testB)) {
			speedRunAmplifier /= runAmplifier;
		}
	}
	
	public boolean touchingBlocks() {
		BoundingBox xTester = boundingBox.clone();
		int directionMultiplier = (direction == Facing.RIGHT || direction == Facing.NONE) ? 1 : (direction == Facing.LEFT) ? -1 : 1;
		xTester.setX(xTester.getX() + speedX);
		if (running) {
			xTester.setX(xTester.getX() + speedRunAmplifier * directionMultiplier);
		}
		tester = xTester;
		if (xTester.getX() <= 0 || xTester.getRight() >= handler.getCanvasWidth()) {
			return true;
		}
		for (AbstractBlock b : ((StateLevel) StateManager.getCurrentState()).getWorldBlocks()) {
			if (MathUtils.twoRectCollision(xTester.asRectangle(), b.getBoundingBox().asRectangle()) && b.isSolid()) {
				return true;
			}
		}
		return false;
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g.drawImage(currentAnimation.getCurrentFrame(), (int) x, (int) y, width, height, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		if(renderBoundingBox) {
			boundingBox.render(g);
			tester.setColor(Color.GREEN);
			tester.render(g);
		}
		renderHPBar(g);
	}

	private void renderHPBar(Graphics g) {
		int rx = boundingBox.getLeft()-10;
		int ry = boundingBox.getTop()-20;
		int rwidth = boundingBox.getWidth()+20;
		int rheight = 5;
		g.setColor(Color.RED);
		g.fillRect(rx, ry, rwidth, rheight);
		g.setColor(Color.GREEN);
		g.fillRect(rx, ry, rwidth/maxHealth*health, rheight);
	}
	
	private void setCurrentAnimation(Action a) {
		if (a == Action.IDLE) {
			currentAnimation = ANIMATION_IDLE;
		}
		if (a == Action.WALKING) {
			currentAnimation = ANIMATION_WALK;
		}
		if (running && a != Action.IDLE) {
			currentAnimation = ANIMATION_RUN;
		}
		if (a == Action.DUCKING) {
			currentAnimation = ANIMATION_DUCK;
		}
	}
	
	public Action getAction() {
		return action;
	}

	@SuppressWarnings("unused")
	public boolean isRunning() {
		return running;
	}
	
	public Facing getFacing() {
		return direction;
	}

	public float getAbsoluteX() {
		return absoluteX;
	}

	public float getAbsoluteY() {
		return absoluteY;
	}

	public void setAbsoluteX(float absoluteX) {
		this.absoluteX = absoluteX;
	}

	public void setAbsoluteY(float absoluteY) {
		this.absoluteY = absoluteY;
	}
}


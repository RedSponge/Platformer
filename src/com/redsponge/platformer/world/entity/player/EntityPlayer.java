package com.redsponge.platformer.world.entity.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.io.AssetsHandler;
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
		boundingBox.setColor(Color.YELLOW);
	}

	public void tick() {
		super.tick();
		tickKeys();
		PlayerUtils.updateBoundingBox(this);
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

class PlayerUtils {
	public static BufferedImage getSpriteByFacing(Facing f, Action a) {
		return AssetsHandler.getPlayerAssets().get(f.getId() + "_" + a.getId());
	}
	
	public static void updateBoundingBox(EntityPlayer me) {
		Action a = me.getAction();
		Facing f = me.getFacing();
		if(a == Action.IDLE) {
			if(f == Facing.LEFT) {
				me.getBoundingBox().setX(me.getBoundingBox().getX()+5);
				me.getBoundingBox().setWidth(me.getBoundingBox().getWidth()-8);
			} else if(f == Facing.RIGHT) {
				me.getBoundingBox().setX(me.getBoundingBox().getX()+5);
				me.getBoundingBox().setWidth(me.getBoundingBox().getWidth()-8);
			}
			me.getBoundingBox().setY(me.getBoundingBox().getY()+18);
			me.getBoundingBox().setHeight(me.getBoundingBox().getHeight()-18);
		} else if(a == Action.WALKING) {
			if(f == Facing.LEFT) {
				me.getBoundingBox().setX(me.getBoundingBox().getX()+5);
				me.getBoundingBox().setWidth(me.getBoundingBox().getWidth()-8);
			} else if(f == Facing.RIGHT) {
				me.getBoundingBox().setX(me.getBoundingBox().getX()+5);
				me.getBoundingBox().setWidth(me.getBoundingBox().getWidth()-8);
			}
			me.getBoundingBox().setY(me.getBoundingBox().getY()+15);
			me.getBoundingBox().setHeight(me.getBoundingBox().getHeight()-15);
		}
	}
}



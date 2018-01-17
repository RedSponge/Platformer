package com.redsponge.platformer.world.entity.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.io.AssetsHandler;
import com.redsponge.platformer.world.entity.AbstractLivingEntity;
import com.redsponge.platformer.world.entity.Facing;

public class EntityPlayer extends AbstractLivingEntity {

	private int size;
	
	public EntityPlayer(Handler handler, int x, int y, int size) {
		super(handler, x, y, size, size*2);
		direction = Facing.RIGHT;
		this.size = size;
		this.jumpHeight = 100;
		this.isGravityApplied = true;
	}

	public void tick() {
		super.tick();
		tickKeys();
	}
	
	public void tickKeys() {
		speedX = 0;
		speedY = 0;
		if(handler.getKeyManager().keyList.get("jump")) {
			jump();
		}
		else if(handler.getKeyManager().keyList.get("move_right")) {
			speedX += 5;
			direction = Facing.RIGHT;
		} else if(handler.getKeyManager().keyList.get("move_left")) {
			speedX -= 5;
			direction = Facing.LEFT;
		}
	}

	public void render(Graphics g) {
		g.drawImage(PlayerUtils.getSpriteByFacing(direction), (int) x, (int) y, size, size*2, null);
		boundingBox.render(g);
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
}

class PlayerUtils {
	public static BufferedImage getSpriteByFacing(Facing f) {
		return AssetsHandler.getPlayerAssets().get((f == Facing.LEFT)?"facing_left":(f == Facing.RIGHT)?"facing_right":null);
	}
}



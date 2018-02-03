package com.redsponge.platformer.world.entity;

import java.awt.Graphics;
import java.util.List;
import java.util.UUID;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.utils.MathUtils;
import com.redsponge.platformer.world.BoundingBox;
import com.redsponge.platformer.world.BoundingBoxUser;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.entity.enemy.AbstractEnemy;
import com.redsponge.platformer.world.entity.player.EntityPlayer;

public abstract class AbstractEntity extends BoundingBoxUser {
	
	protected float speedX, speedY;
	protected boolean onGround;
	protected float fallingSpeed;
	protected float fallingMultiplier;
	
	protected float fallingSpeedMax;
	
	protected boolean isGravityApplied;
	
	protected int dontTickOnGroundFor;
	
	protected AbstractBlock onTopOf;
	
	protected UUID uuid;
	
	public AbstractEntity(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		this.isGravityApplied = true;
		this.boundingBox = new BoundingBox(handler, this);
		this.fallingMultiplier = 1.05F;
		this.isGravityApplied = false;
		fallingSpeedMax = 20;
		uuid = UUID.randomUUID();
	}
	
	public void tick() {
		super.tick();
		if(dontTickOnGroundFor == 0) {
			StateLevel state = (StateLevel) StateManager.getCurrentState();
			updateOnGround(state.getWorldBlocks());
		}
	}
	
	public void updateOnGround(List<AbstractBlock> worldBlocks) {
		for(AbstractBlock b : worldBlocks) {
			if(MathUtils.onTopOfBlock(this, b)) {
				onTopOf = b;
				onGround = true;
				return;
			}
		}
		onTopOf = null;
		onGround = false;		
	}
	
	public abstract void render(Graphics g);

	public boolean isEnemy() {
		return this instanceof AbstractEnemy;
	}
	
	public boolean isPlayer() {
		return this instanceof EntityPlayer;
	}
	
	public float getSpeedX() {
		return speedX;
	}
	
	public float getSpeedY() {
		return speedY;
	}
	
	public boolean onGround() {
		return onGround;
	}
	
	public UUID getUUID() {
		return uuid;
	}
}

package com.redsponge.platformer.gfx.animation;

import com.redsponge.platformer.world.BoundingBoxUser;
import com.redsponge.platformer.world.entity.AbstractLivingEntity;
import com.redsponge.platformer.world.entity.Facing;

import java.awt.image.BufferedImage;
import java.util.*;

public class Animation {
	
	private int animationSpeed;
	private int counter;
	
	private boolean canBeFacing;
	private boolean hasBeenDoneOnce;
	
	private BoundingBoxUser user;
	
	private Map<Facing, List<BufferedImage>> frames;
	
	private String animationId;
	private boolean isPartOfTransition;
	
	private int currentFrame;
	
	public Animation(BoundingBoxUser user, BufferedImage[] rightImg, BufferedImage[] leftImg, int animationSpeed, String animationId) {
		this.user = user;
		this.frames = new HashMap<Facing, List<BufferedImage>>();
		this.frames.put(Facing.RIGHT, Arrays.asList(rightImg));
		this.frames.put(Facing.LEFT, Arrays.asList(leftImg));
		this.counter = 0;
		this.currentFrame = 0;
		this.hasBeenDoneOnce = false;
		this.canBeFacing = true;
		this.animationSpeed = animationSpeed;
		this.animationId = animationId;
	}
	
	public Animation(BoundingBoxUser user, BufferedImage[] Images, int animationSpeed, String animationId) {
		this.user = user;
		this.frames = new HashMap<Facing, List<BufferedImage>>();
		this.frames.put(Facing.NONE, Arrays.asList(Images));
		this.counter = 0;
		this.currentFrame = 0;
		this.canBeFacing = false;
		this.hasBeenDoneOnce = false;
		this.animationSpeed = animationSpeed;
		this.animationId = animationId;
	}
	
	public void tick() {
		counter++;
		if(counter >= animationSpeed) {
			currentFrame++;
			counter = 0;
			if(currentFrame >= frames.get((canBeFacing)?((AbstractLivingEntity)user).getDirection():Facing.NONE).size()) {
				if(!isPartOfTransition) {
					currentFrame = 0;
				} else {
					currentFrame--;
				}
				hasBeenDoneOnce = true;
			}
		}
	}

	public BufferedImage getCurrentFrame() {
		if(canBeFacing) {
			return frames.get(((AbstractLivingEntity) user).getDirection()).get(currentFrame);
		}
		return frames.get(Facing.NONE).get(currentFrame);
	}

	public void reset() {
		currentFrame = 0;
		counter = 0;
		hasBeenDoneOnce = false;
	}
	
	public String getAnimationId() {
		return animationId;
	}
	
	public Animation clone() {
		return new Animation(user, frames.get(Facing.RIGHT).toArray(new BufferedImage[0]), frames.get(Facing.LEFT).toArray(new BufferedImage[0]), animationSpeed, animationId);
	}

	public boolean hasBeenDoneOnce() {
		return hasBeenDoneOnce;
	}

	public Animation reverse() {
		for(Facing f : Facing.values()) {
			if(frames.get(f) != null) {
				Collections.reverse(frames.get(f));
			}
		}
		return this;
	}

	public void setPartOfTransition(boolean partOfTransition) {
		isPartOfTransition = partOfTransition;
	}
}

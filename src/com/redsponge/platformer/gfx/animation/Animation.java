package com.redsponge.platformer.gfx.animation;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redsponge.platformer.world.BoundingBoxUser;
import com.redsponge.platformer.world.entity.AbstractLivingEntity;
import com.redsponge.platformer.world.entity.Facing;

public class Animation {
	
	private int animationSpeed;
	private int counter;
	
	private boolean canBeFacing;
	
	private BoundingBoxUser user;
	
	private Map<Facing, List<BufferedImage>> frames;
	
	private int currentFrame;
	
	public Animation(BoundingBoxUser user, BufferedImage[] rightImg, BufferedImage[] leftImg, int animationSpeed) {
		this.user = user;
		this.frames = new HashMap<Facing, List<BufferedImage>>();
		this.frames.put(Facing.RIGHT, Arrays.asList(rightImg));
		this.frames.put(Facing.LEFT, Arrays.asList(leftImg));
		this.counter = 0;
		this.currentFrame = 0;
		this.canBeFacing = true;
		this.animationSpeed = animationSpeed;
	}
	
	public Animation(BoundingBoxUser user, BufferedImage[] Images, int animationSpeed) {
		this.user = user;
		this.frames = new HashMap<Facing, List<BufferedImage>>();
		this.frames.put(Facing.NONE, Arrays.asList(Images));
		this.counter = 0;
		this.currentFrame = 0;
		this.canBeFacing = false;
		this.animationSpeed = animationSpeed;
	}
	
	public void tick() {
		counter++;
		if(counter >= animationSpeed) {
			currentFrame++;
			counter = 0;
			if(currentFrame >= frames.get((canBeFacing)?((AbstractLivingEntity)user).getDirection():Facing.NONE).size()) {
				currentFrame = 0;
			}
		}
	}
	
	public BufferedImage getCurrentFrame() {
		if(canBeFacing) {
			return frames.get(((AbstractLivingEntity)user).getDirection()).get(currentFrame);
		}
		return frames.get(Facing.NONE).get(currentFrame);
	}

	public void reset() {
		currentFrame = 0;
		counter = 0;
	}
	
}

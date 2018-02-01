package com.redsponge.platformer.world.entity.player;

import com.redsponge.platformer.world.BoundingBox;
import com.redsponge.platformer.world.entity.Action;
import com.redsponge.platformer.world.entity.Facing;

public class PlayerUtils {
	
	public static void updateBoundingBox(EntityPlayer me) {
		Action a = me.getAction();
		Facing f = me.getFacing();
		BoundingBox bb = me.getBoundingBox().clone();
		if(a == Action.IDLE) {
			if(f == Facing.LEFT) {
				bb.setX(bb.getX()+14);
				bb.setWidth(bb.getWidth()-25);
			} else if(f == Facing.RIGHT) {
				bb.setX(bb.getX()+15);
				bb.setWidth(bb.getWidth()-25);
			}
			bb.setY(bb.getY()+30);
			bb.setHeight(bb.getHeight()-30);
		} else if(a == Action.WALKING || me.isRunning()) {
			if(f == Facing.LEFT) {
				bb.setX(bb.getX() + 10);
				bb.setWidth(bb.getWidth()-25);
			} else if(f == Facing.RIGHT) {
				bb.setX(bb.getX()+15);
				bb.setWidth(bb.getWidth()-25);
			}
			bb.setY(bb.getY()+30);
			bb.setHeight(bb.getHeight()-30);
		}
		me.setBoundingBox(bb);
	}
	
	public static BoundingBox updateBoundingBox(BoundingBox b, Facing f, Action a) {
		BoundingBox bb = b;
		if(a == Action.IDLE) {
			if(f == Facing.LEFT) {
				bb.setX(bb.getX()+14);
				bb.setWidth(bb.getWidth()-25);
			} else if(f == Facing.RIGHT) {
				bb.setX(bb.getX()+15);
				bb.setWidth(bb.getWidth()-25);
			}
			bb.setY(bb.getY()+30);
			bb.setHeight(bb.getHeight()-30);
		} else if(a == Action.WALKING || a == Action.RUNNING) {
			if(f == Facing.LEFT) {
				bb.setX(bb.getX() + 25);
				bb.setWidth(bb.getWidth()-25);
			} else if(f == Facing.RIGHT) {
				bb.setX(bb.getX()+13);
				bb.setWidth(bb.getWidth()-25);
			}
			bb.setY(bb.getY()+30);
			bb.setHeight(bb.getHeight()-30);
		}
		return bb;
	}
}




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
		} else if(a == Action.WALKING || a == Action.RUNNING) {
			if(f == Facing.LEFT) {
				bb.setX(bb.getX()+14);
				bb.setWidth(bb.getWidth()-25);
			} else if(f == Facing.RIGHT) {
				bb.setX(bb.getX()+15);
				bb.setWidth(bb.getWidth()-25);
			}
			if(a == Action.RUNNING) {
				BoundingBox testCol = bb.clone();
				testCol.setX(me.getX());
				if(!me.touchingBlocks(testCol, false)) {
					bb.setX(testCol.getX());
				}
			}
			bb.setY(bb.getY()+30);
			bb.setHeight(bb.getHeight()-30);
		}
		if(bb.getX() < 0) {
			me.setX(0);
			bb.setX(0);
		}
		me.setBoundingBox(bb);
	}
}




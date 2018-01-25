package com.redsponge.platformer.world.entity.player;

import com.redsponge.platformer.world.entity.Action;
import com.redsponge.platformer.world.entity.Facing;

public class PlayerUtils {
	
	public static void updateBoundingBox(EntityPlayer me) {
		Action a = me.getAction();
		Facing f = me.getFacing();
		if(a == Action.IDLE) {
			if(f == Facing.LEFT) {
				me.getBoundingBox().setX(me.getBoundingBox().getX()+14);
				me.getBoundingBox().setWidth(me.getBoundingBox().getWidth()-25);
			} else if(f == Facing.RIGHT) {
				me.getBoundingBox().setX(me.getBoundingBox().getX()+15);
				me.getBoundingBox().setWidth(me.getBoundingBox().getWidth()-25);
			}
			me.getBoundingBox().setY(me.getBoundingBox().getY()+30);
			me.getBoundingBox().setHeight(me.getBoundingBox().getHeight()-30);
		} else if(a == Action.WALKING) {
			if(f == Facing.LEFT) {
				me.getBoundingBox().setX(me.getBoundingBox().getX()+14);
				me.getBoundingBox().setWidth(me.getBoundingBox().getWidth()-25);
			} else if(f == Facing.RIGHT) {
				me.getBoundingBox().setX(me.getBoundingBox().getX()+15);
				me.getBoundingBox().setWidth(me.getBoundingBox().getWidth()-25);
			}
			me.getBoundingBox().setY(me.getBoundingBox().getY()+30);
			me.getBoundingBox().setHeight(me.getBoundingBox().getHeight()-30);
		}
	}
}




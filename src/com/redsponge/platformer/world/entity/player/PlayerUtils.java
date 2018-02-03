package com.redsponge.platformer.world.entity.player;

import com.redsponge.platformer.world.BoundingBox;
import com.redsponge.platformer.world.entity.Action;

public class PlayerUtils {
	
	public static void updateBoundingBox(EntityPlayer me) {
		Action a = me.getAction();
		//Facing f = me.getFacing();
		BoundingBox bb = me.getBoundingBox().clone();
		
		bb.setX(bb.getX()+14);
		bb.setWidth(bb.getWidth()-25);
		
		bb.setY(bb.getY()+30);
		bb.setHeight(bb.getHeight()-30);
		if(a == Action.DUCKING) {
			bb.setY(bb.getY()+34);
			bb.setHeight(bb.getHeight()-34);
		}
		me.setBoundingBox(bb);
	}
}




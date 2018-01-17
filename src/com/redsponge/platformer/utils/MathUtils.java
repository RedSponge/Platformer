package com.redsponge.platformer.utils;

import java.awt.Rectangle;

import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.entity.AbstractEntity;
import com.redsponge.platformer.world.entity.BoundingBox;

public class MathUtils {
	
	public static boolean twoRectCollision(Rectangle rect1, Rectangle rect2) {
		return (rect1.x < rect2.x + rect2.width) && (rect1.x + rect1.width > rect2.x)
				&& (rect1.y < rect2.y + rect2.height) && (rect1.y + rect1.height > rect2.y);
	}
	
	public static boolean onTopOfBlock(AbstractEntity e, AbstractBlock b) {
		BoundingBox eb = e.getBoundingBox();
		BoundingBox bb = b.getBoundingBox();
		
		if((eb.getLeft() < bb.getRight()||eb.getRight()>bb.getLeft())
			&&eb.getBottom()+10>bb.getTop()
			
				) {
			System.out.println("I'm on ground!");
			return true;
		}
		
		return false;
	}
	
}

package com.redsponge.platformer.utils;

import java.awt.Rectangle;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.world.BoundingBox;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.entity.AbstractEntity;

public class MathUtils {
	
	private static int precision = 10;
	
	public static boolean twoRectCollision(Rectangle rect1, Rectangle rect2) {
		return (rect1.x < rect2.x + rect2.width) && (rect1.x + rect1.width > rect2.x)
				&& (rect1.y < rect2.y + rect2.height) && (rect1.y + rect1.height > rect2.y);
	}
	
	public static boolean onTopOfBlock(AbstractEntity e, AbstractBlock b) {
		BoundingBox eb = e.getBoundingBox();
		BoundingBox bb = b.getBoundingBox();
		if(bb.getTop() < eb.getBottom()) {
			return true;
		}
		return false;
	}

	public static AbstractBlock blockAbove(Handler handler, AbstractEntity e) {
		BoundingBox be = e.getBoundingBox().clone();
		be.setHeight(precision);
		for(AbstractBlock b : ((StateLevel)StateManager.getCurrentState()).getWorldBlocks()) {
			BoundingBox bb = b.getBoundingBox();
			if(bb.getBottom() > be.getTop() && bb.getTop() < be.getBottom()
				&& be.getLeft() < bb.getRight() && be.getRight() > bb.getLeft()
					) {
				return b;
			}
		}
		return null;
	}
	
}

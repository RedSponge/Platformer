package com.redsponge.platformer.utils;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.entity.AbstractEntity;
import com.redsponge.platformer.world.entity.BoundingBox;

public class MathUtils {
	
	private static int precision = 1;
	
	public static boolean twoRectCollision(BoundingBox rect1, BoundingBox rect2) {
		return (rect1.getRight() < rect2.getLeft()) && (rect1.getLeft() > rect2.getRight())
				&& (rect1.getTop() < rect2.getBottom()) && (rect1.getBottom() > rect2.getTop());
	}
	
	public static boolean onTopOfBlock(AbstractEntity e, AbstractBlock b) {
		BoundingBox eb = e.getBoundingBox().clone();
		BoundingBox bb = b.getBoundingBox().clone();
		bb.setY(bb.getY()+(bb.getHeight()-2));
		if((eb.getLeft() < bb.getRight()&&eb.getRight()>bb.getLeft())
			&&eb.getBottom()+precision>bb.getTop()&&eb.getTop()<bb.getTop()
			
				) {
			e.setY(b.getBoundingBox().getTop()-e.getBoundingBox().getHeight());
			return true;
		}
		return false;
	}

	public static AbstractBlock blockAbove(Handler handler, AbstractEntity e) {
		BoundingBox be = e.getBoundingBox();
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

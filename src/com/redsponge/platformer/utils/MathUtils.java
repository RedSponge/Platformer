package com.redsponge.platformer.utils;

import java.awt.Rectangle;

public class MathUtils {
	
	public static boolean twoRectCollision(Rectangle rect1, Rectangle rect2) {
		return (rect1.x < rect2.x + rect2.width) && (rect1.x + rect1.width > rect2.x)
				&& (rect1.y < rect2.y + rect2.height) && (rect1.y + rect1.height > rect2.y);
	}
	
}

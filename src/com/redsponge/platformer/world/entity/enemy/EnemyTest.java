package com.redsponge.platformer.world.entity.enemy;

import com.redsponge.platformer.handler.Handler;

import java.awt.*;

public class EnemyTest extends AbstractEnemy {

	public EnemyTest(Handler handler, int x, int y) {
		super(handler, x, y, 32, 32);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y, width, height);
	}
	
}

package com.redsponge.platformer.world.entity.enemy;

import java.awt.Color;
import java.awt.Graphics;

import com.redsponge.platformer.handler.Handler;

public class EnemyTest extends AbstractEnemy {

	public EnemyTest(Handler handler, int x, int y) {
		super(handler, x, y, 32, 32);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y, width, height);
	}
	
}

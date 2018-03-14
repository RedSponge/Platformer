package com.redsponge.platformer.world.entity.enemy;

import com.redsponge.platformer.gfx.animation.Animation;
import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.io.AssetsHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyTest extends AbstractEnemy {

    private Animation ANIMATION_MOVE;
    private int ANIMATION_MOVE_SPEED;

	public EnemyTest(Handler handler, int x, int y) {
	    super(handler, x, y, 32, 32);
	    ANIMATION_MOVE_SPEED = 30;

	    setupAnimation();
	}

	public void setupAnimation() {
        String pathMain = "/assets/textures/entities/enemy/test";
        ANIMATION_MOVE = new Animation(this, new BufferedImage[]{AssetsHandler.getImage(pathMain + "/right/right_0.png")}, new BufferedImage[]{AssetsHandler.getImage(pathMain + "/left/left_0.png")}, ANIMATION_MOVE_SPEED, "animationEnemyTest");
    }

    public void tick() {
	    super.tick();
	    ANIMATION_MOVE.tick();
    }

	public void render(Graphics g) {
		g.drawImage(ANIMATION_MOVE.getCurrentFrame(), (int)x, (int)y, width, height, null);
	}
	
}

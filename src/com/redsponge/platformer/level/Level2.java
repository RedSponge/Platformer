package com.redsponge.platformer.level;

import com.redsponge.platformer.handler.Handler;

public class Level2 extends AbstractLevel {

	public Level2(Handler handler) {
		super(handler);
		PLAYER_START_X = 20;
		PLAYER_START_Y = 300;
	}

	public int[][] getLevelBlocks() {
		return LevelParser.parseLevelFile(handler, "/data/levels/l02.platlev");
	}

}

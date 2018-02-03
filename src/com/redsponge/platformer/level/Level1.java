package com.redsponge.platformer.level;

import com.redsponge.platformer.handler.Handler;

public class Level1 extends AbstractLevel {

	 public Level1(Handler handler) {
		 super(handler);
		 PLAYER_START_X = 100;
		 PLAYER_START_Y = 100;
	}
	
	@Override
	public int[][] getLevelBlocks() {
		return LevelParser.parseLevelFile(handler, "/data/levels/l01.platlev");
	}

}

package com.redsponge.platformer.level;

public abstract class AbstractLevel {
	public abstract int[][] getLevelBlocks();
	
	public int PLAYER_START_X;
	public int PLAYER_START_Y;
}

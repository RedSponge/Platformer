package com.redsponge.platformer.level;

import com.redsponge.platformer.handler.Handler;

public class LevelParser {
	
	public static int[][] parseLevelFile(Handler handler, String path) {
		String text = handler.getFileHandler().readFile(path);
		String[] lines = text.split("\n");
		int[][] result = new int[lines.length][lines[0].length()];
		for(int i = 0; i < result.length; i++) {
			String line = lines[i];
			String[] characters = line.split(" ");
			for(int j = 0; j < characters.length; j++) {
				result[i][j] = Integer.parseInt(characters[j]);
			}
		}
		return result;
	}
	
}

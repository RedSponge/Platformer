package com.redsponge.platformer.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.block.BlockAir;
import com.redsponge.platformer.world.block.BlockBrick;
import com.redsponge.platformer.world.block.BlockBrickPassable;
import com.redsponge.platformer.world.block.BlockDirt;
import com.redsponge.platformer.world.block.BlockGlass;
import com.redsponge.platformer.world.block.BlockGrass;
import com.redsponge.redutils.console.ConsoleMSG;

public class LevelUtils {

	private static HashMap<Integer, Class<? extends AbstractBlock>> blockMap;
	
	private static void registerBlocks() {
		ConsoleMSG.ADD.info("Registering Classes To BlockMap");
		blockMap = new HashMap<Integer, Class<? extends AbstractBlock>>();
		blockMap.put(0, BlockAir.class);
		blockMap.put(1, BlockGrass.class);
		blockMap.put(2, BlockDirt.class);
		blockMap.put(3, BlockBrick.class);
		blockMap.put(4, BlockGlass.class);
		blockMap.put(5, BlockBrickPassable.class);
		ConsoleMSG.ADD.info("Successfully Registered Classes To BlockMap");
	}
	
	public static void init() {
		registerBlocks();
	}
	
	public static Class<? extends AbstractBlock> translateNumberToBlockClass(int id) {
		return blockMap.get(id);
	}
	
	public static List<List<Class<? extends AbstractBlock>>> translateNumbersLevel(int[][] numbersLevel) {
		List<List<Class<? extends AbstractBlock>>> list = new ArrayList<List<Class<? extends AbstractBlock>>>();
		for(int i = 0; i < numbersLevel.length; i++) {
			list.set(i, new ArrayList<Class<? extends AbstractBlock>>());
			for(int j = 0; j < blockMap.values().size(); j++) {
				list.get(i).set(j, blockMap.get(j));
			}
		}
		return list;
	}
	
}

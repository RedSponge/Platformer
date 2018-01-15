package com.redsponge.platformer.state;

import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.block.BlockBrick;
import com.redsponge.platformer.world.block.ITickingBlock;
import com.redsponge.platformer.world.entity.EntityPlayer;

public class StateLevel extends AbstractState {
	
	private List<AbstractBlock> worldBlocks;
	private EntityPlayer player;
	
	public StateLevel(Handler handler) {
		super(handler);
		registerBlocks();
		setupPlayer();
	}
	
	private void registerBlocks() {
		worldBlocks = new ArrayList<AbstractBlock>();
		
		addBlocks(WorldBuilder.createFloors(handler, 32, BlockBrick.class));
	}
	
	private void setupPlayer() {
		player = new EntityPlayer(handler, 500, 100, 32);
	}
	
	public void addBlock(AbstractBlock b) {
		worldBlocks.add(b);
	}
	
	public void addBlocks(List<AbstractBlock> b) {
		worldBlocks.addAll(b);
	}
	
	public void tick() {
		Ticking.tickWorldBlocks(worldBlocks);
		player.tick();
	}
	
	public void render(Graphics g) {
		Rendering.renderWorldBlocks(g, worldBlocks);
		player.render(g);
	}
	
	public List<AbstractBlock> getWorldBlocks() {
		return worldBlocks;
	}
}

class Rendering {
	public static void renderWorldBlocks(Graphics g, List<AbstractBlock> blocks) {
		for(AbstractBlock b : blocks) {
			b.render(g);
		}
	}
}

class Ticking {
	public static void tickWorldBlocks(List<AbstractBlock> blocks) {
		for(AbstractBlock b : blocks) {
			if(b instanceof ITickingBlock) {
				((ITickingBlock) b).tick();
			}
		}
	}
}

class WorldBuilder {
	
	public static List<AbstractBlock> createFloors(Handler handler, int blockSize, Class<? extends AbstractBlock> blockType) {
		ArrayList<AbstractBlock> ar = new ArrayList<AbstractBlock>();
		Constructor<?> con = blockType.getDeclaredConstructors()[0];
		for(int i = 0; i < handler.getDisplay().getCanvas().getWidth(); i+=blockSize) {
			try {
				ar.add((AbstractBlock) con.newInstance(handler, i, handler.getDisplay().getCanvas().getHeight()-blockSize, blockSize, blockSize));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ar;
	}
	
}

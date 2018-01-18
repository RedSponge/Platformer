package com.redsponge.platformer.state;

import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.block.BlockBrick;
import com.redsponge.platformer.world.block.BlockDirt;
import com.redsponge.platformer.world.block.BlockGrass;
import com.redsponge.platformer.world.block.ITickingBlock;
import com.redsponge.platformer.world.entity.player.EntityPlayer;

public class StateLevel extends AbstractState {
	
	private List<AbstractBlock> worldBlocks;
	private EntityPlayer player;
	
	public StateLevel(Handler handler) {
		super(handler);
		setupPlayer();
		registerBlocks();
	}
	
	private void registerBlocks() {
		worldBlocks = new ArrayList<AbstractBlock>();
		
		addBlock(new BlockBrick(handler, (int) player.getX(), 300, 20, 20));
		addBlock(new BlockBrick(handler, (int) player.getX(), 150, 20, 20));
		
		addBlock(new BlockDirt(handler, (int) player.getX(), 350, 32, 32));
		
		addBlocks(WorldBuilder.createFloors(handler, 32, BlockGrass.class));
	}
	
	private void setupPlayer() {
		player = new EntityPlayer(handler, 500, 0, 64);
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
		Rendering.renderWorldBlocks(g, worldBlocks, true);
		player.render(g);
	}
	
	public List<AbstractBlock> getWorldBlocks() {
		return worldBlocks;
	}
}

class Rendering {
	public static void renderWorldBlocks(Graphics g, List<AbstractBlock> blocks, boolean boundingBoxes) {
		for(AbstractBlock b : blocks) {
			b.render(g);
			if(boundingBoxes) {
				b.getBoundingBox().render(g);
			}
		}
	}
	
	public static void renderWorldBlocks(Graphics g, List<AbstractBlock> blocks) {
		renderWorldBlocks(g, blocks, false);
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

package com.redsponge.platformer.state;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.block.BlockBrick;
import com.redsponge.platformer.world.block.ITickingBlock;
import com.redsponge.platformer.world.entity.EntityPlayer;

public class StateLevel extends AbstractState {
	
	private List<AbstractBlock> blocks;
	private EntityPlayer player;
	
	public StateLevel(Handler handler) {
		super(handler);
		registerBlocks();
		setupPlayer();
	}
	
	private void registerBlocks() {
		blocks = new ArrayList<AbstractBlock>();
		
		addBlock(new BlockBrick(handler, 400, 400, 20, 20));
	}
	
	private void setupPlayer() {
		player = new EntityPlayer(handler, 500, 100, 32);
	}
	
	public void addBlock(AbstractBlock b) {
		blocks.add(b);
	}
	
	public void tick() {
		Ticking.tickWorldBlocks(blocks);
		player.tick();
	}
	
	public void render(Graphics g) {
		Rendering.renderWorldBlocks(g, blocks);
		player.render(g);
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

package com.redsponge.platformer.state;

import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.redsponge.platformer.gfx.color.CustomColor;
import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.level.AbstractLevel;
import com.redsponge.platformer.level.Level1;
import com.redsponge.platformer.level.LevelUtils;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.block.ITickingBlock;
import com.redsponge.platformer.world.entity.player.EntityPlayer;
import com.redsponge.redutils.console.ConsoleMSG;

public class StateLevel extends AbstractState {
	
	private List<AbstractBlock> worldBlocks;
	private EntityPlayer player;
	
	public StateLevel(Handler handler) {
		super(handler);
		setupPlayer();
		registerBlocks(new Level1());
	}
	
	private void registerBlocks(AbstractLevel l) {
		ConsoleMSG.ADD.info("Registering Blocks for Level \"" + l.getClass().getSimpleName() + "\"");
		worldBlocks = new ArrayList<AbstractBlock>();
		int[][] level = l.getLevelBlocks();
		for(int y = 0; y < handler.getCanvasHeight(); y += 32) {
			for(int x = 0; x < handler.getCanvasWidth(); x += 32) {
				try {
					addBlock((AbstractBlock) LevelUtils.translateNumberToBlockClass(level[y/32][x/32]).getDeclaredConstructors()[0].newInstance(handler, x, y, 32, 32));
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		ConsoleMSG.ADD.info("Successfully Registered Blocks for Level \"" + l.getClass().getSimpleName() + "\"");
	}
	
	private void setupPlayer() {
		ConsoleMSG.ADD.info("Setting Up Player!");
		player = new EntityPlayer(handler, 500, 0, 64);
		ConsoleMSG.ADD.info("Successfully Set Player Up!");
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
		Rendering.renderWorldSky(handler, g);
		Rendering.renderBackgroundWorldBlocks(g, worldBlocks);
		player.render(g);
		Rendering.renderForegroundWorldBlocks(g, worldBlocks);
	}
	
	public List<AbstractBlock> getWorldBlocks() {
		return worldBlocks;
	}
}

class Rendering {
	public static void renderBackgroundWorldBlocks(Graphics g, List<AbstractBlock> blocks, boolean boundingBoxes) {
		for(AbstractBlock b : blocks) {
			if(b.isInFront()) {
				continue;
			}
			b.render(g);
			if(boundingBoxes) {
				b.getBoundingBox().render(g);
			}
		}
	}
	
	public static void renderForegroundWorldBlocks(Graphics g, List<AbstractBlock> blocks, boolean boundingBoxes) {
		for(AbstractBlock b : blocks) {
			if(!b.isInFront()) {
				continue;
			}
			b.render(g);
			if(boundingBoxes) {
				b.getBoundingBox().render(g);
			}
		}
	}
	
	public static void renderWorldSky(Handler handler, Graphics g) {
		g.setColor(CustomColor.LIGHT_BLUE);
		g.fillRect(0, 0, handler.getCanvasWidth(), handler.getCanvasHeight());
	}

	public static void renderBackgroundWorldBlocks(Graphics g, List<AbstractBlock> blocks) {
		renderBackgroundWorldBlocks(g, blocks, false);
	}
	
	public static void renderForegroundWorldBlocks(Graphics g, List<AbstractBlock> blocks) {
		renderForegroundWorldBlocks(g, blocks, false);
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

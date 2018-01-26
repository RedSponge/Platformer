package com.redsponge.platformer.state;

import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.redsponge.platformer.camera.CameraManager;
import com.redsponge.platformer.gfx.color.CustomColor;
import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.level.AbstractLevel;
import com.redsponge.platformer.level.Level1;
import com.redsponge.platformer.level.LevelUtils;
import com.redsponge.platformer.world.IDontRenderBB;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.block.ITickingBlock;
import com.redsponge.platformer.world.entity.player.EntityPlayer;
import com.redsponge.redutils.console.ConsoleMSG;

public class StateLevel extends AbstractState {
	
	private List<AbstractBlock> worldBlocks;
	private EntityPlayer player;
	
	private AbstractLevel loadedLevel;
	
	private CameraManager cameraManager;
	
	public StateLevel(Handler handler) {
		super(handler);
		registerBlocks(new Level1());
		setupPlayer();
		registerCameraManager();
	}
	
	private void registerCameraManager() {
		cameraManager = new CameraManager(handler, this);
	}

	private void registerBlocks(AbstractLevel l) {
		ConsoleMSG.ADD.info("Registering Blocks for Level \"" + l.getClass().getSimpleName() + "\"");
		worldBlocks = new ArrayList<AbstractBlock>();
		int[][] level = l.getLevelBlocks();
		for(int y = 0; y < level.length; y++) {
			for(int x = 0; x < level[y].length; x++) {
				try {
					addBlock((AbstractBlock) LevelUtils.translateNumberToBlockClass(level[y][x]).getDeclaredConstructors()[0].newInstance(handler, (int) x*32, (int) y*32, 32, 32));
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		this.loadedLevel = l;
		ConsoleMSG.ADD.info("Successfully Registered Blocks for Level \"" + l.getClass().getSimpleName() + "\"");
	}
	
	private void setupPlayer() {
		ConsoleMSG.ADD.info("Setting Up Player!");
		player = new EntityPlayer(handler, loadedLevel.PLAYER_START_X, loadedLevel.PLAYER_START_Y,(int) (64/1.2));
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
		cameraManager.tick();
	}
	
	public void render(Graphics g) {
		Rendering.renderWorldSky(handler, g);
		Rendering.renderBackgroundWorldBlocks(g, worldBlocks, true);
		player.render(g);
		Rendering.renderForegroundWorldBlocks(g, worldBlocks);
	}
	
	public List<AbstractBlock> getWorldBlocks() {
		return worldBlocks;
	}

	public AbstractLevel getLoadedLevel() {
		return loadedLevel;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
}

class Rendering {
	public static void renderBackgroundWorldBlocks(Graphics g, List<AbstractBlock> blocks, boolean boundingBoxes) {
		for(AbstractBlock b : blocks) {
			if(b.isInFront()) {
				continue;
			}
			b.render(g);
			if(boundingBoxes && !(b instanceof IDontRenderBB)) {
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

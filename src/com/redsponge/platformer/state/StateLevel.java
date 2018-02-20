package com.redsponge.platformer.state;

import com.redsponge.platformer.camera.CameraManager;
import com.redsponge.platformer.gfx.color.CustomColor;
import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.level.AbstractLevel;
import com.redsponge.platformer.level.Level1;
import com.redsponge.platformer.level.LevelUtils;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.block.ITickingBlock;
import com.redsponge.platformer.world.entity.enemy.AbstractEnemy;
import com.redsponge.platformer.world.entity.enemy.EnemyTest;
import com.redsponge.platformer.world.entity.player.EntityPlayer;
import com.redsponge.redutils.console.ConsoleMSG;

import java.awt.*;
import java.util.*;
import java.util.List;

public class StateLevel extends AbstractState {
	
	private List<AbstractBlock> worldBlocks;
	private Map<UUID, AbstractEnemy> worldEnemies;
	private EntityPlayer player;

	private boolean reseting;
	
	private AbstractLevel loadedLevel;
	private boolean doRenderWorldBlockBoundingBoxes;
	private CameraManager cameraManager;

	private int blockSize;

	private int worldXSize;
	private int worldYSize;
	
	public StateLevel(Handler handler) {
		super(handler);
		init();
	}

	private void init() {
		doRenderWorldBlockBoundingBoxes = false;
		registerEnemies();
		registerBlocks(new Level1(handler));
        registerCameraManager();
		setupPlayer();
	}

	public void reset() {
		reseting = true;
		init();
		cameraManager.reset();
		reseting = false;
	}

	private void registerCameraManager() {
		cameraManager = handler.getCameraManager();
		cameraManager.reset();
		cameraManager.init(this);
	}
	
	private void registerEnemies() {
		worldEnemies = new HashMap<UUID, AbstractEnemy>();
		EnemyTest e = new EnemyTest(handler, 300, 100);
		worldEnemies.put(e.getUUID(), e);
	}

	private void registerBlocks(AbstractLevel l) {
		blockSize = l.getBlockSize();
		ConsoleMSG.ADD.info("Registering Blocks for Level \"" + l.getClass().getSimpleName() + "\"");
		worldBlocks = new ArrayList<AbstractBlock>();
		int[][] level = l.getLevelBlocks();
		for(int y = 0; y < level.length; y++) {
			for(int x = 0; x < level[y].length; x++) {
				try {
					addBlock((AbstractBlock) LevelUtils.translateNumberToBlockClass(level[y][x]).getDeclaredConstructors()[0].newInstance(handler, (int) x*blockSize, (int) y*blockSize, blockSize, blockSize));
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		this.loadedLevel = l;
		worldXSize = l.getLevelBlocks()[0].length/2 * blockSize ;
		worldYSize = l.getLevelBlocks().length;
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
	
	public void tick() {
	    if(reseting) return;
		Ticking.tickWorldBlocks(worldBlocks);
		Ticking.tickWorldEnemies(worldEnemies.values());
		cameraManager.tick();
		player.tick();
	}
	
	public void render(Graphics g) {
	    if(reseting) return;
		Rendering.renderWorldSky(handler, g);
		Rendering.renderBackgroundWorldBlocks(g, cameraManager.utils.getOnScreenWorldBlocks(), doRenderWorldBlockBoundingBoxes);
		Rendering.renderWorldEnemies(g, worldEnemies.values());
		player.render(g);
		Rendering.renderForegroundWorldBlocks(g, cameraManager.utils.getOnScreenWorldBlocks());
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
	
	public void doRenderWorldBlockBoundingBoxes(boolean doRenderWorldBlockBoundingBoxes) {
		this.doRenderWorldBlockBoundingBoxes = doRenderWorldBlockBoundingBoxes;
	}
	
	public boolean isDoRenderWorldBlockBoundingBoxes() {
		return doRenderWorldBlockBoundingBoxes;
	}

	public Map<UUID, AbstractEnemy> getWorldEnemies() {
		return worldEnemies;
	}

    public int getWorldXSize() {
        return worldXSize;
    }

    public int getWorldYSize() {
        return worldYSize;
    }

	public int getBlockSize() {
		return blockSize;
	}
}

class Rendering {
	public static void renderBackgroundWorldBlocks(Graphics g, Collection<AbstractBlock> blocks, boolean boundingBoxes) {
		for(AbstractBlock b : blocks) {
			if(b.isInFront()) {
				continue;
			}
			b.render(g);
			if(boundingBoxes && b.isRenderBoundingBox()) {
				b.getBoundingBox().render(g);
			}
		}
	}
	
	public static void renderForegroundWorldBlocks(Graphics g, Collection<AbstractBlock> blocks, boolean boundingBoxes) {
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
	
	public static void renderWorldEnemies(Graphics g, Collection<AbstractEnemy> worldEnemies) {
		for(AbstractEnemy e : worldEnemies) {
			e.render(g);
		}
	}
	
	public static void renderWorldSky(Handler handler, Graphics g) {
		g.setColor(CustomColor.LIGHT_BLUE);
		g.fillRect(0, 0, handler.getCanvasWidth(), handler.getCanvasHeight());
	}

	public static void renderBackgroundWorldBlocks(Graphics g, Collection<AbstractBlock> blocks) {
		renderBackgroundWorldBlocks(g, blocks, false);
	}
	
	public static void renderForegroundWorldBlocks(Graphics g, Collection<AbstractBlock> blocks) {
		renderForegroundWorldBlocks(g, blocks, false);
	}
	
}

class Ticking {
	public static void tickWorldBlocks(Collection<AbstractBlock> blocks) {
		for(AbstractBlock b : blocks) {
			if(b instanceof ITickingBlock) {
				((ITickingBlock) b).tick();
			}
		}
	}

	public static void tickWorldEnemies(Collection<AbstractEnemy> worldEnemies) {
		for(AbstractEnemy e : worldEnemies) {
			e.tick();
		}
	}
}
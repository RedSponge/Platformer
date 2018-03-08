package com.redsponge.platformer;

import com.redsponge.platformer.camera.CameraManager;
import com.redsponge.platformer.debug.DebugManager;
import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.input.KeyManager;
import com.redsponge.platformer.io.AssetsHandler;
import com.redsponge.platformer.io.FileHandler;
import com.redsponge.platformer.level.LevelUtils;
import com.redsponge.platformer.settings.Settings;
import com.redsponge.platformer.state.AbstractState;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.redutils.GraphicsApp;
import com.redsponge.redutils.console.ConsoleMSG;
import com.redsponge.redutils.display.GraphicsDisplay;

public class Platformer extends GraphicsApp {

	private boolean running;
	
	private Handler handler;
	private KeyManager keyManager;

	private CameraManager cameraManager;
	private FileHandler fileHandler;

	public Platformer(String title, int width, int height) {
		super(title, width, height, 60, 60);
	}

	public void init() {
		display.getFrame().setIconImages(AssetsHandler.getIconImages());

		ConsoleMSG.ADD.info("Registering Handler!");
		handler = new Handler(this);
		ConsoleMSG.ADD.info("Successfully Registered Handler!");
		
		ConsoleMSG.ADD.info("Registering Settings!");
		Settings.init();
		ConsoleMSG.ADD.info("Successfully Registered Settings!");

		ConsoleMSG.ADD.info("Initiating File Handler!");
		fileHandler = new FileHandler();
		ConsoleMSG.ADD.info("Successfully Initiated File Handler!");

		ConsoleMSG.ADD.info("Initiating Assets");
		AssetsHandler.init(handler);
		ConsoleMSG.ADD.info("Successfully Initiated State Manager");
		
		ConsoleMSG.ADD.info("Creating Key Manager!");
		keyManager = new KeyManager(handler);
		ConsoleMSG.ADD.info("Registering Key Manager!");
		display.getFrame().addKeyListener(keyManager);
		ConsoleMSG.ADD.info("Successfully Created and Registered Key Manager!");

		ConsoleMSG.ADD.info("Initiating Game Manager!");
		GameManager.init(this);
		ConsoleMSG.ADD.info("Successfully Initiated Game Manager!");

		ConsoleMSG.ADD.info("Initiating LevelUtils");
		LevelUtils.init();
		ConsoleMSG.ADD.info("Successfully Initiated LevelUtils");
		
		ConsoleMSG.ADD.info("Initiating Camera Manager!");
		cameraManager = new CameraManager(handler);
		ConsoleMSG.ADD.info("Successfully Initiated Camera Manager!");

		ConsoleMSG.ADD.info("Initiating State Manager");
		StateManager.init(handler);
		StateManager.setCurrentState("level");
		ConsoleMSG.ADD.info("Successfully Initiated State Manager");

	}
	
	public void tick() {
		keyManager.tick();
		AbstractState state = StateManager.getCurrentState();
		if(state != null) {
			state.tick();
		}
	}
	
	public void render() {
		//DRAW
		if(StateManager.getCurrentState() != null) {
			StateManager.getCurrentState().render(g);
		}
		DebugManager.renderDebugEntries(g, handler);
		//END
	}

	public GraphicsDisplay getDisplay() {
		return display;
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public CameraManager getCameraManager() {
		return cameraManager;
	}

	public void reset() {
		running = false;
		Launcher.main(null);
	}

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public FileHandler getFileHandler() {
		return fileHandler;
	}
}

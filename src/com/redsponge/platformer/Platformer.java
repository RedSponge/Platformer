package com.redsponge.platformer;

import com.redsponge.platformer.camera.CameraManager;
import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.input.KeyManager;
import com.redsponge.platformer.io.AssetsHandler;
import com.redsponge.platformer.io.FileHandler;
import com.redsponge.platformer.level.LevelUtils;
import com.redsponge.platformer.settings.Settings;
import com.redsponge.platformer.state.AbstractState;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.redutils.console.ConsoleMSG;
import com.redsponge.redutils.display.GameDisplay;

import java.awt.*;

public class Platformer implements Runnable {

	private GameDisplay display;
	private int width, height;
	private String title;
	
	private Thread thread;
	private boolean running;
	
	private Graphics g;
	
	private Handler handler;
	private KeyManager keyManager;
	
	private CameraManager cameraManager;
	private FileHandler fileHandler;
	
	public Platformer(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public void init() {
		ConsoleMSG.ADD.info("Registering Handler!");
		handler = new Handler(this);
		ConsoleMSG.ADD.info("Successfully Registered Handler!");
		
		ConsoleMSG.ADD.info("Registering Settings!");
		Settings.init();
		ConsoleMSG.ADD.info("Successfully Registered Settings!");
		
		ConsoleMSG.ADD.info("Initiating Assets");
		AssetsHandler.init();
		ConsoleMSG.ADD.info("Successfully Initiated State Manager");
		
		ConsoleMSG.ADD.info("Registering Game Display!");
		display = new GameDisplay(width, height, title);
		ConsoleMSG.ADD.info("Successfully Registered Game Display!");
		
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
		
		ConsoleMSG.ADD.info("Initiating File Handler!");
		fileHandler = new FileHandler();
		ConsoleMSG.ADD.info("Successfully Initiated File Handler!");
		
		ConsoleMSG.ADD.info("Initiating State Manager");
		StateManager.init(handler);
		StateManager.setCurrentState("level");
		ConsoleMSG.ADD.info("Successfully Initiated State Manager");

	}
	
	public synchronized void start() {
		if(running) {
			ConsoleMSG.FATAL.info("Trying to start a running thread! ABORTING!");
			return;
		}
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public void tick() {
		keyManager.tick();
		AbstractState state = StateManager.getCurrentState();
		if(state != null) {
			state.tick();
		}
	}
	
	public void render() {
		
		g = display.getGraphics();
		
		//DRAW
		g.clearRect(0, 0, width, height);
		AbstractState state = StateManager.getCurrentState();
		if(state != null) {
			state.render(g);
		}
		
		if(Settings.displayDebug) {
			int fontSize = 20;
			g.setFont(new Font("Courier new", Font.BOLD, fontSize));
			g.setColor(Color.BLACK);
			g.drawString("[player_x]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getX(), 5, fontSize*1);
			g.drawString("[player_y]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getY(), 5, fontSize*2);
			g.drawString("[player_speed_x]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getSpeedX(), 5, fontSize*3);
			g.drawString("[player_speed_y]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getSpeedY(), 5, fontSize*4);
			g.drawString("[player_onground]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().onGround(), 5, fontSize*5);
			g.drawString("[player_action]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getAction(), 5, fontSize*6);
			g.drawString("[player_facing]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getFacing(), 5, fontSize*7);
			g.drawString("[player_fly_jump]: " + Settings.allowFlyJump, 5, fontSize*8);
			g.drawString("[cam_offset_x]: " + cameraManager.getOffsetX(), 5, fontSize*9);
			g.drawString("[cam_offset_y]: " + cameraManager.getOffsetY(), 5, fontSize*10);
		}
		//END
		display.push();
	}
	
	public void run() {
		init();
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += (now - lastTime);
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				delta--;
				ticks++;
			}
			if(timer >= 1000000000) {
				if(Launcher.SHOWFPS) {
					ConsoleMSG.INFO.info("FPS: " + ticks);
				}
				timer = 0;
				ticks = 0;
			}
		}
		stop();
	}
	
	public synchronized void stop() {
		if(!running) {
			ConsoleMSG.FATAL.info("Trying to stop a stopped thread! ABORTING!");
			return;
		}
		try {
			thread.join();
			ConsoleMSG.ADD.info("thread stopped successfully!");
			running = false;
			ConsoleMSG.ADD.info("Game stopped successfully!");
		} catch(InterruptedException e) {
			ConsoleMSG.FATAL.info("Couldn't stop thread! ABORTING! stack trace:");
			e.printStackTrace();
		}
	}

	public GameDisplay getDisplay() {
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

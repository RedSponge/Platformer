package com.redsponge.platformer;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.input.KeyManager;
import com.redsponge.platformer.io.AssetsHandler;
import com.redsponge.platformer.settings.Settings;
import com.redsponge.platformer.state.AbstractState;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.redutils.console.ConsoleMSG;
import com.redsponge.redutils.display.GameDisplay;

public class Platformer implements Runnable {

	private GameDisplay display;
	private int width, height;
	private String title;
	
	private Thread thread;
	private boolean running;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private Handler handler;
	private KeyManager keyManager;
	
	public Platformer(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public void init() {
		handler = new Handler(this);
		
		Settings.init();
		keyManager = new KeyManager(handler);
		
		display = new GameDisplay(width, height, title);
		
		StateManager.init(handler);
		StateManager.setCurrentState("level");
		
		display.getFrame().addKeyListener(keyManager);
		
		AssetsHandler.init();
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
		
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		//DRAW
		g.clearRect(0, 0, width, height);
		AbstractState state = StateManager.getCurrentState();
		if(state != null) {
			state.render(g);
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
}

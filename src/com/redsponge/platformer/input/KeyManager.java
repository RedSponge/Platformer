package com.redsponge.platformer.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.settings.Settings;

public class KeyManager implements KeyListener {

	boolean keys[];
	public HashMap<String, Boolean> keyList;
	
	@SuppressWarnings("unused")
	private Handler handler;
	
	public KeyManager(Handler handler) {
		this.handler = handler;
		keys = new boolean[256];
		keyList = new HashMap<String, Boolean>();
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	public void tick() {
		keyList.put("move_right", keys[Settings.keys.get("move_right")]);
		keyList.put("move_left", keys[Settings.keys.get("move_left")]);
		keyList.put("jump", keys[Settings.keys.get("jump")]);
		keyList.put("duck", keys[Settings.keys.get("duck")]);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}

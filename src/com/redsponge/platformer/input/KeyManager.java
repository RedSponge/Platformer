package com.redsponge.platformer.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.settings.Settings;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.redutils.console.ConsoleMSG;

public class KeyManager implements KeyListener {

	boolean keys[];
	public HashMap<String, Boolean> keyList;

	private Handler handler;
	
	public KeyManager(Handler handler) {
		this.handler = handler;
		keys = new boolean[1024];
		keyList = new HashMap<String, Boolean>();
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		if(e.getKeyCode() == Settings.keys.get("toggle_debug")) {
			Settings.displayDebug = !Settings.displayDebug;
		}
		if(e.getKeyCode() == Settings.keys.get("toggle_player_bounding_box")) {
			handler.getPlayer().doRenderBoundingBox(!handler.getPlayer().isRenderBoundingBox());
		}
		if(e.getKeyCode() == Settings.keys.get("toggle_worldblocks_bounding_box")) {
			if(StateManager.getCurrentState() instanceof StateLevel) {
				((StateLevel) StateManager.getCurrentState()).doRenderWorldBlockBoundingBoxes(!((StateLevel)StateManager.getCurrentState()).isDoRenderWorldBlockBoundingBoxes());
			}
		}
		if(e.getKeyCode() == Settings.keys.get("toggle_fly_jump")) {
			Settings.allowFlyJump = !Settings.allowFlyJump;
			ConsoleMSG.INFO.info("Toggled Fly Jumping!");
		}
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	public void tick() {
		keyList.put("move_right", keys[Settings.keys.get("move_right")]);
		keyList.put("move_left", keys[Settings.keys.get("move_left")]);
		keyList.put("jump", keys[Settings.keys.get("jump")]);
		keyList.put("duck", keys[Settings.keys.get("duck")]);
		keyList.put("run", keys[Settings.keys.get("run")]);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}

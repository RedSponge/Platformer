package com.redsponge.platformer.settings;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Settings {
	
	public static Map<String, Integer> keys;
	public static boolean displayDebug;
	public static boolean allowFlyJump;
	
	
	public static void init() {
		
		keys = new HashMap<String, Integer>();
		
		keys.put("move_right", KeyEvent.VK_RIGHT);
		keys.put("move_left", KeyEvent.VK_LEFT);
		keys.put("jump", KeyEvent.VK_UP);
		keys.put("duck", KeyEvent.VK_DOWN);
		keys.put("run", KeyEvent.VK_X);
		
		keys.put("toggle_player_bounding_box", KeyEvent.VK_F1);
		keys.put("toggle_worldblocks_bounding_box", KeyEvent.VK_F2);
		keys.put("toggle_debug", KeyEvent.VK_F3);
		keys.put("toggle_fly_jump", KeyEvent.VK_F4);
		displayDebug = true;
		allowFlyJump = false;
	}
	
}

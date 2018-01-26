package com.redsponge.platformer.settings;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Settings {
	
	public static Map<String, Integer> keys;
	
	public static void init() {
		
		keys = new HashMap<String, Integer>();
		
		keys.put("move_right", KeyEvent.VK_RIGHT);
		keys.put("move_left", KeyEvent.VK_LEFT);
		keys.put("jump", KeyEvent.VK_UP);
		keys.put("duck", KeyEvent.VK_DOWN);
		keys.put("run", KeyEvent.VK_CONTROL);
		
	}
	
}

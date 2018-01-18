package com.redsponge.platformer.io;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.redsponge.platformer.handler.Handler;

public class AssetsHandler {
	
	public static BufferedImage nullImage;
	
	@SuppressWarnings("unused")
	private Handler handler;
	
	private static Map<String, BufferedImage> playerAssets;
	
	public static void init() {
		nullImage = getImage("/assets/textures/misc/null.png");
		registerPlayerAssets();
	}
	
	private static void registerPlayerAssets() {
		playerAssets = new HashMap<String, BufferedImage>();
		playerAssets.put("right_walking", getImage("/assets/textures/entities/player/idle_right.png"));
		playerAssets.put("left_walking", getImage("/assets/textures/entities/player/idle_left.png"));
		
		playerAssets.put("left_idle", getImage("/assets/textures/entities/player/idle_left.png"));
		playerAssets.put("right_idle", getImage("/assets/textures/entities/player/idle_right.png"));
	}
	
	public static BufferedImage getImage(String path) {
		try {
			return ImageIO.read(AssetsHandler.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nullImage;
	}
	
	public static Map<String, BufferedImage> getPlayerAssets() {
		return playerAssets;
	}
	
}

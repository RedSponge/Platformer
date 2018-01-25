package com.redsponge.platformer.io;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.redsponge.platformer.handler.Handler;

public class AssetsHandler {
	
	public static BufferedImage nullImage;
	
	@SuppressWarnings("unused")
	private Handler handler;
	
	
	public static void init() {
		nullImage = getImage("/assets/textures/misc/null.png");
	}
	
	public static BufferedImage getImage(String path) {
		try {
			return ImageIO.read(AssetsHandler.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nullImage;
	}
	
}

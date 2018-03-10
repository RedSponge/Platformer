package com.redsponge.platformer.world.material;

import com.redsponge.platformer.io.AssetsHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public enum BlockMaterial {
	
	BRICK(Color.BLACK, "material_brick", "brick_block.png"),
	GLASS(Color.WHITE, "material_glass", "glass_block.png"),
	DIRT(Color.YELLOW, "material_dirt", "dirt_block.png"),
	GRASS(Color.GREEN, "material_grass", "grass_block.png"),
	AIR(Color.WHITE, "material_air", null),
	LOG(Color.YELLOW, "material_log", "log_block.png"),
	LEAVES(Color.GREEN, "material_leaves", "leaves_block.png");
	
	private Color color;
	private String id;
	private String texturePath;
	private BufferedImage texture;
	
	
	private BlockMaterial(Color color, String id, String texturePath) {
		this.color = color;
		this.id = id;
		this.texturePath = texturePath;
		registerTexture();
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getId() {
		return id;
	}
	
	public String getTexturePath() {
		return texturePath;
	}
	
	public BufferedImage getTexture() {
		return texture;
	}

	public void registerTexture() {
		if(texturePath == null) {
			return;
		}
		this.texture = AssetsHandler.getImage("/assets/textures/blocks/" + texturePath);
	}
	
}

package com.redsponge.platformer.world.material;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.redsponge.platformer.io.AssetsHandler;

public enum BlockMaterial {
	
	BRICK(Color.BLACK, "material_brick", "/assets/textures/blocks/brick_block.png"),
	GLASS(Color.WHITE, "material_glass", "/assets/textures/blocks/glass_block.png"),
	DIRT(Color.YELLOW, "material_dirt", "/assets/textures/blocks/dirt_block.png"),
	GRASS(Color.GREEN, "material_grass", "/assets/textures/blocks/grass_block.png");
	
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
		this.texture = AssetsHandler.getImage(texturePath);
	}
	
}

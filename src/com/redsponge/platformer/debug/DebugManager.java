package com.redsponge.platformer.debug;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.world.entity.player.EntityPlayer;

public class DebugManager {
	
	public static void renderDebugEntries(Graphics g) {
		int fontSize = 20;
		Font font = new Font("Courier New", Font.BOLD, fontSize);
		Color fontColor = Color.BLACK;
		
		g.setColor(fontColor);
		g.setFont(font);
		EntityPlayer p = ((StateLevel)StateManager.getCurrentState()).getPlayer();
		//g.drawString("[playerX]: " + StateManager.);
	}
	
}

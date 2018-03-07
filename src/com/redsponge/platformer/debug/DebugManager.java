package com.redsponge.platformer.debug;

import com.redsponge.platformer.camera.CameraManager;
import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.settings.Settings;
import com.redsponge.platformer.state.StateLevel;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.world.entity.player.EntityPlayer;

import java.awt.*;

public class DebugManager {
	
	public static void renderDebugEntries(Graphics g, Handler handler) {
		int fontSize = 20;
		Font font = new Font("Courier New", Font.BOLD, fontSize);
		Color fontColor = Color.BLACK;
		
		g.setColor(fontColor);
		g.setFont(font);
		EntityPlayer p = ((StateLevel)StateManager.getCurrentState()).getPlayer();

		CameraManager cameraManager = handler.getCameraManager();

		if(Settings.displayDebug) {
			g.setFont(new Font("Courier new", Font.BOLD, fontSize));
			g.setColor(Color.BLACK);
			g.drawString("[player_x]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getX(), 5, fontSize*1);
			g.drawString("[player_y]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getY(), 5, fontSize*2);
			g.drawString("[player_speed_x]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getSpeedX(), 5, fontSize*3);
			g.drawString("[player_speed_y]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getSpeedY(), 5, fontSize*4);
			g.drawString("[player_onground]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().onGround(), 5, fontSize*5);
			g.drawString("[player_action]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getAction(), 5, fontSize*6);
			g.drawString("[player_facing]: " + ((StateLevel)StateManager.getCurrentState()).getPlayer().getFacing(), 5, fontSize*7);
			g.drawString("[player_fly_jump]: " + Settings.allowFlyJump, 5, fontSize*8);
			g.drawString("[cam_offset_x]: " + cameraManager.getOffsetX(), 5, fontSize*9);
			g.drawString("[cam_offset_y]: " + cameraManager.getOffsetY(), 5, fontSize*10);
			g.drawString("[player_health]: " + StateManager.getLevelState().getPlayer().getHealth(), 5, fontSize * 11);
		}
	}
	
}

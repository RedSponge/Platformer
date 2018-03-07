package com.redsponge.platformer;

import com.redsponge.platformer.version.Version;
import com.redsponge.redutils.console.ConsoleMSG;

public class Launcher {
	
	public static final int WIDTH = (int) (640*1.5), HEIGHT = (int) (480*1.2);
	public static final String TITLE = "Platformer";
	public static boolean SHOWFPS = false;
	
	public static void main(String[] args) {
		ConsoleMSG.ADD.info("STARTING GAME AT VERSION " +  Version.getString());
		try {
			Platformer p = new Platformer(TITLE + "-" + Version.getString(), WIDTH, HEIGHT);
			ConsoleMSG.ADD.info("GAME STARTED SUCCESSFULLY!");
		} catch(Exception e) {
			ConsoleMSG.ERROR.info("COULDN'T START GAME!");
			e.printStackTrace();
		}
	}
}

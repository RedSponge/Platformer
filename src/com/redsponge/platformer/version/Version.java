package com.redsponge.platformer.version;

public class Version {
	
	private static final boolean ALPHA = true, BETA = false;

	private static final String ver_num = "0.0.4.pear";
	
	public static String getString() {
		return ((ALPHA)?"alpha":(BETA)?"beta":"") + "-" + ver_num;
	}
	
}

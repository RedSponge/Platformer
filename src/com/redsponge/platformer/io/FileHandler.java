package com.redsponge.platformer.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileHandler {
	
	public File getFile(String path) {
		return new File(getClass().getResource(path).getFile().replaceAll("%20", " "));
	}
	
	public InputStream getFileInputStream(String path) {
		return getClass().getResourceAsStream(path);
	}
	
	public String readFile(String path) {
		String text = "";
		String line;
		try {
			InputStream fis = getClass().getResourceAsStream(path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			while((line = br.readLine()) != null) {
				text += line + "\n";
			}
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
}

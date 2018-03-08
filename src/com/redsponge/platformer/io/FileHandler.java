package com.redsponge.platformer.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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

	public String[] getFilePathsInJarDirectory(String path) {
		ZipFile zipFile = null;
		List<String> paths = new ArrayList<>();
		try {
			File f = new File(FileHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("%20"," "));
			zipFile = new ZipFile(f);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while(entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if(!entry.isDirectory() && entry.getName().startsWith(path)) {
					paths.add("/" + entry.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				zipFile.close();
			} catch(Exception e) {}
		}
		return paths.toArray(new String[0]);
	}
}

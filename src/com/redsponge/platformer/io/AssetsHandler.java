package com.redsponge.platformer.io;

import com.redsponge.platformer.handler.Handler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AssetsHandler {
	
	public static BufferedImage nullImage;
	private static Handler handler;

	public static void init(Handler handler) {
		nullImage = getImage("/assets/textures/misc/null.png");
		AssetsHandler.handler = handler;
	}
	
	public static BufferedImage getImage(String path) {
		try {
			return ImageIO.read(AssetsHandler.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nullImage;
	}

	public static BufferedImage getImage(File file) {
		try {
			return ImageIO.read(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return nullImage;
	}

	public static ImageIcon getImageIcon(String path) {
	    return new ImageIcon(getImage(path));
    }

    public static Image getAwtImage(String path) {
	    return getImageIcon(path).getImage();
    }

    public static List<Image> getIconImages() {
	    String iconPath = "/assets/textures/main/icon/";
	    List<Image> images = new ArrayList<>();
	    for(int size = 16; size < 128; size *= 2) {
            images.add(getAwtImage(iconPath + "icon_" + size + ".png"));
        }
        return images;
    }

	public static BufferedImage[] getImagesInDirectory(String path) {
		try {
			InputStream dir = handler.getFileHandler().getFileInputStream(path);
			File[] files = null;//new File().listFiles();
			List<BufferedImage> imgs = new ArrayList<>();
			System.out.println(files.length);
			for (File f : files) {
				if (getFileExtension(f).equalsIgnoreCase("png")) {
					imgs.add(getImage(f));
				}
			}
			return imgs.toArray(new BufferedImage[0]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".")+1);
		else return "";
	}
	
}

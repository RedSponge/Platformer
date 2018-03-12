package com.redsponge.platformer.io;

import com.redsponge.platformer.handler.Handler;

public class SoundsHandler {

    public static void init(Handler handler) {
        try {
            OggClip oggClip = new OggClip(handler.getFileHandler().getFileInputStream("/assets/sound/main/background.ogg"));
            OggClip oggClip1 = new OggClip(handler.getFileHandler().getFileInputStream("/assets/sound/main/background2.ogg"));
            //oggClip.play();
            oggClip1.play();
        } catch(Exception e) {
            e.printStackTrace();
        }
//        try {
//            AudioClip a = null;
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(handler.getFileHandler().getFile("/assets/sound/main/background.ogg").getAbsoluteFile());
//            Clip clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
//            clip.start();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
    }

}

package com.redsponge.platformer.io;

import com.redsponge.platformer.handler.Handler;

public class SoundsHandler {

    public static void init(Handler handler) {
        try {
            OggClip oggClip1 = new OggClip(handler.getFileHandler().getFileInputStream("/assets/sound/main/background2.ogg"));
            //oggClip1.play();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

package com.redsponge.platformer;

import com.redsponge.platformer.state.StateManager;

public class GameManager {

    private static Platformer game;

    public static void init(Platformer game) {
        GameManager.game = game;
    }

    public static void resetLevelState() {
        StateManager.getLevelState().reset();
    }

}

package com.redsponge.platformer.state;

public class StateNotStateLevelException extends RuntimeException {

    public StateNotStateLevelException() {
        super("State isn't an instance of StateLevel");
    }

}

package com.redsponge.platformer.timer;

@FunctionalInterface
public interface SchedulerTask {
    void execute();
}

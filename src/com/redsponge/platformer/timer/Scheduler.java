package com.redsponge.platformer.timer;

import com.redsponge.redutils.console.ConsoleMSG;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private static List<ScheduledTask> tasks;
    private static boolean inited = false;

    public static void init() {
        if(inited) {
            ConsoleMSG.WARNING.info("Tried to init already inited scheduler, Aborting!");
            return;
        }
        inited = true;
        tasks = new ArrayList<>();
    }

    public static void schedule(SchedulerTask task, int time) {
        tasks.add(new ScheduledTask(task, time));
    }

    public static void tick() {
        for(ScheduledTask task : tasks) {
            task.tick();
            if(task.done) {
                tasks.remove(task);
            }
        }
    }

}

class ScheduledTask {
    private int time;
    private SchedulerTask task;
    public ScheduledTask(SchedulerTask task, int time) {
        this.time = time;
        this.task = task;
        this.done = false;
    }

    public void tick() {
        time--;
        if(time <= 0) {
            task.execute();
            done = true;
        }
    }
    public boolean done;
}

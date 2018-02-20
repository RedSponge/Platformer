package com.redsponge.platformer.world.entity.enemy;

public class EnemyPropertyMap {

    private boolean bouncePlayerOnKill;

    public EnemyPropertyMap() {
        bouncePlayerOnKill = true;
    }

    public void setBouncePlayerOnKill(boolean bouncePlayerOnKill) {
        this.bouncePlayerOnKill = bouncePlayerOnKill;
    }

    public boolean BouncePlayerOnKill() {
        return bouncePlayerOnKill;
    }
}

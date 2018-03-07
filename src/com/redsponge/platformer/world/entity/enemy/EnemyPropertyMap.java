package com.redsponge.platformer.world.entity.enemy;

public class EnemyPropertyMap {

    public boolean bouncePlayerOnKill;
    public boolean canBeStomped;

    public int damage;

    public EnemyPropertyMap() {
        bouncePlayerOnKill = true;
        canBeStomped = true;
        damage = 1;
    }
}

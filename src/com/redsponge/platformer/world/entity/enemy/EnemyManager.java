package com.redsponge.platformer.world.entity.enemy;

public class EnemyManager {
    private AbstractEnemy enemy;
    private float absoluteX, absoluteY;

    public EnemyManager(AbstractEnemy enemy, float absoluteX, float absoluteY) {
        this.absoluteX = absoluteX;
        this.absoluteY = absoluteY;
        this.enemy = enemy;
    }

    public AbstractEnemy getEnemy() {
        return enemy;
    }

    public float getAbsoluteX() {
        return absoluteX;
    }

    public float getAbsoluteY() {
        return absoluteY;
    }
}

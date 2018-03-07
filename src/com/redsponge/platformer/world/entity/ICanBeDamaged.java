package com.redsponge.platformer.world.entity;

public interface ICanBeDamaged {
    void hurt(HurtCause cause);

    int getHealth();
}

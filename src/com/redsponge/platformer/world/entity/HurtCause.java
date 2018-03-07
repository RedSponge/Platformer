package com.redsponge.platformer.world.entity;

public class HurtCause {

    private EnumHurtType type;

    private IDamaging damager;
    private ICanBeDamaged damaged;

    public HurtCause(IDamaging damager, ICanBeDamaged damaged) {

    }

    public EnumHurtType getType() {
        return type;
    }

    public IDamaging getDamager() {
        return damager;
    }

    public ICanBeDamaged getDamaged() {
        return damaged;
    }

    public enum EnumHurtType {
        DEFAULT("default"),
        ENEMY("enemy");

        private String id;
        EnumHurtType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

}

package com.redsponge.platformer.world.entity;

import com.redsponge.platformer.utils.StringUtils;
import com.redsponge.platformer.world.entity.enemy.AbstractEnemy;
import com.redsponge.platformer.world.entity.player.EntityPlayer;

public class HurtCause {

    protected EnumHurtType type;

    protected IDamager damager;
    protected ICanBeDamaged damaged;

    public HurtCause(IDamager damager, ICanBeDamaged damaged) {
        this.damager = damager;
        this.damaged = damaged;
    }

    public EnumHurtType getHurtType() {
        return type;
    }

    public IDamager getDamager() {
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

    public static class HurtCauseCreator {
        public static HurtCause generate(EnumHurtType type, Object... args) {
            try {
                switch(type) {
                    case ENEMY:
                        return new HurtEnemy((AbstractEnemy) args[0], (EntityPlayer) args[1]);
                    default:
                        return null;

                }
            } catch(Exception e) {
                throw new CantCreateHurtCauseException(type, args);
            }
        }
    }

    static class CantCreateHurtCauseException extends RuntimeException{

        public CantCreateHurtCauseException(EnumHurtType type, Object[] args) {
            super("Could not create HurtCause " + type.toString() + " with given arguments " + StringUtils.arrayToString(args));
        }
    }

    public static class HurtEnemy extends HurtCause {
        public HurtEnemy(AbstractEnemy damager, EntityPlayer damaged) {
            super(damager, damaged);
            type = EnumHurtType.ENEMY;
        }
    }

}

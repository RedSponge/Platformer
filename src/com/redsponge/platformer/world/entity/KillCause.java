package com.redsponge.platformer.world.entity;

import com.redsponge.platformer.world.entity.enemy.AbstractEnemy;
import com.redsponge.platformer.world.entity.player.EntityPlayer;

import java.util.Arrays;

public class KillCause {

    protected AbstractEntity entity;

    protected EnumKillType killType;

    public static enum EnumKillType {
        DEFAULT("default"),
        STOMP("stomp");


        private String id;
        EnumKillType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public AbstractEntity getEntity() {
        return entity;
    }

    public KillCause(AbstractEntity entity) {
        this.entity = entity;
        this.killType = EnumKillType.DEFAULT;
    }

    public EnumKillType getKillType() {
        return killType;
    }

    public static class KillCauseCreator {
        public static KillCause generate(EnumKillType killType, Object... args) {
            try {
                switch (killType) {
                    case STOMP:
                        return new KillStomp((AbstractEnemy) args[0], (EntityPlayer) args[1]);
                    default:
                        return null;
                }
            } catch (Exception e) {
                throw new CantCreateKillCauseException(killType, args);
            }
        };
    }

    public static class KillStomp extends KillCause {

        protected EntityPlayer player;

        public KillStomp(AbstractEnemy enemy, EntityPlayer player) {
            super(enemy);
            this.player = player;
            killType = EnumKillType.STOMP;
        }

        public EntityPlayer getPlayer() {
            return player;
        }
    }

    public static class CantCreateKillCauseException extends RuntimeException{

        public CantCreateKillCauseException(EnumKillType killType, Object[] args) {
            super("Could not create KillCause " + killType.toString() + " with given arguments " + String.join(", ", Arrays.asList(args).toArray(new String[0])));
        }

    }
}




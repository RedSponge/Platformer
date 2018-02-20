package com.redsponge.platformer.world.entity.enemy;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.platformer.state.StateManager;
import com.redsponge.platformer.utils.MathUtils;
import com.redsponge.platformer.world.BoundingBox;
import com.redsponge.platformer.world.block.AbstractBlock;
import com.redsponge.platformer.world.entity.AbstractLivingEntity;
import com.redsponge.platformer.world.entity.Facing;
import com.redsponge.platformer.world.entity.KillCause;
import com.redsponge.platformer.world.entity.player.EntityPlayer;

import java.util.List;

public abstract class AbstractEnemy extends AbstractLivingEntity {

	protected float currentX;
	protected float currentY;
	protected EnemyPropertyMap propertyMap;
	
	protected boolean hasBeenOnGround;
	
	public AbstractEnemy(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		isGravityApplied = true;
		speedX = 3;
		direction = Facing.RIGHT;
		updateCurrentPosition();
		propertyMap = new EnemyPropertyMap();
		hasBeenOnGround = false;
	}
	
	public void tick() {
		boundingBox.tick();
		move();
		tickGravity();
		tickPlayer();
	}
	
	private void turn() {
		speedX *= -1;
		direction = direction.getOpposite();
	}

	public void tickPlayer() {
        EntityPlayer player = StateManager.getLevelState().getPlayer();
        if(MathUtils.twoRectCollision(boundingBox.asRectangle(), player.getBoundingBox().asRectangle())){
            if(playerOnMe(this, player)) {
                this.kill(KillCause.KillCauseCreator.generate(KillCause.EnumKillType.STOMP, this, player));
            } else {
                player.hurt();
            }
        }
    }

	public void kill(KillCause killCause) {
	    switch(killCause.getKillType().getId()) {
            case "stomp":
                KillCause.KillStomp cause = (KillCause.KillStomp) killCause;
                if(propertyMap.BouncePlayerOnKill()) {
                    cause.getPlayer().jump(false);
                }
                break;
        }
        StateManager.getLevelState().getWorldEnemies().remove(uuid);
    }


	public void moveX(BoundingBox box) {
		if(touchingBlocks(box, false)) {
			if(onGround) {
				turn();
			}
		}
        if(x <= 0 - handler.getCameraManager().getOffsetX()) {
            turn();
        }
		x += speedX;
	}
	
	public void updateOnGround(List<AbstractBlock> worldBlocks) {
		if(jumping) {
			return;
		}
		for(AbstractBlock b : worldBlocks) {
			if(MathUtils.onTopOf(this, b)) {
				onTopOf = b;
				if(!hasBeenOnGround) {
					turn();
				}
				hasBeenOnGround = true;
				onGround = true;
				return;
			}
		}
		onTopOf = null;
		onGround = false;
		hasBeenOnGround = false;
	}
	
	public void updateCurrentPosition() {
		currentX = x;
		currentY = y;
	}

	public static boolean playerOnMe(AbstractEnemy enemy, EntityPlayer player) {
	    if(MathUtils.twoRectCollision(enemy.getBoundingBox().asRectangle(), player.getBoundingBox().asRectangle())) {
	        if(player.isFalling()) {
                return true;
            }
        }
        return false;
    }
	
	public float getCurrentX() {
		return currentX;
	}
	
	public float getCurrentY() {
		return currentY;
	}
}

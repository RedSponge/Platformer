package com.redsponge.platformer.gfx.animation;

public class AnimationTransition {

    private Animation transition;
    private Animation finalAnimation;

    public AnimationTransition(Animation transition, Animation finalAnimation) {
        this.transition = transition;
        transition.setPartOfTransition(true);
        this.finalAnimation = finalAnimation;
    }

    public Animation getFinalAnimation() {
        return finalAnimation;
    }

    public Animation getTransitionAnimation() {
        return transition;
    }

    public boolean isDone() {
        return transition.hasBeenDoneOnce();
    }

    public void reset() {
        transition.reset();
    }

}

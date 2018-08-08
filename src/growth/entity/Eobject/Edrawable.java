package growth.entity.Eobject;

import growth.screen.render.Animation;

import java.util.ArrayList;

public abstract class Edrawable extends Eobject{

    /**
     * Entity state
     * These static final variable counting the different state of the entity
     */
    public static final int IDLE = 0;

    /**
     * Animation priority
     * This variable contains the priority of the idle animation.
     */
    public static final int IDLE_P = 0;

    /**
     * Current priority.
     * This variable contains the priority of the current animation played.
     */
    protected int priority;

    protected int animationPlayed;

    /**
     * Animations table.
     * This ArrayList contains all of the animations use by the entity.
     */
    protected ArrayList<Animation> animations;

    public Edrawable(){
        super();
        animations = new ArrayList<>();
    }

    public void update(){
        animationPlayed = IDLE;
        priority = IDLE_P;
    }

    public abstract void display();

    public void unload() {
        for(Animation animation: animations) if(!(animation == null)) animation.unload();

        super.unload();
    }

    /**
     * Set the new animation if the priority of the new animation is
     * better than the priority of the current animation.
     *
     * @param animationID The ID of the maybe new animation.
     * @param priorityValue The value.
     */
    public void setAnimations(int animationID, int priorityValue){
        if(priorityValue > priority){
            animations.get(animationPlayed).reset();
            animationPlayed = animationID;
        }
    }

    /**
     * Set a animation without counting the priority.
     * @param animationID The ID of the new aniamtion.
     */
    public void setAnimations(int animationID){
        animationPlayed = animationID;
    }

    public void setAnimationSpeed(float speed){ animations.get(animationPlayed).setSpeed(speed);}
}

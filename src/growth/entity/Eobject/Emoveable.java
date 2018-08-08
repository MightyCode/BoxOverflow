package growth.entity.Eobject;

import growth.entity.module.Module;
import growth.util.math.Vec2;

import java.util.ArrayList;

public abstract class Emoveable extends Edrawable{

    protected boolean facing;

    protected boolean falling;

    /**
     * Modules.
     * This list contains the module using by the entity.
     */
    protected ArrayList<Module> modules;

    protected Vec2 speed;

    public Emoveable(){
        super();
        speed = new Vec2();
        modules = new ArrayList<>();
    }

    public void update(){
        super.update();
        for(Module module : modules){
            module.update();
        }
    }

    public void died(){
        unload();
    }

    public Vec2 getSpeed() { return speed; }

    public void setSpeed(Vec2 speed) { this.speed = speed; }

    public boolean getFalling() {
        return falling;
    }

    public void setFalling(boolean newState){ falling = newState;}
}

package growth.entity.Eobject;

import growth.screen.screens.GameScreen;
import growth.util.math.Vec2;

public abstract class Eobject {

    protected Vec2 pos, size;

    public Eobject(){
        pos = new Vec2();
        size = new Vec2();
    }

    public abstract void update();

    public void display(){}

    public Vec2 getPos() { return pos; }

    public void setPos(Vec2 pos) { this.pos = pos; }

    public Vec2 getSize() { return size; }

    public void setSize(Vec2 size) { this.size = size; }

    public void unload(){ GameScreen.entityManager.removeEntity(this); }
}

package growth.util.math;

/**
 * Vector 2d.
 * This class is use to manipulate 2d coordinates.
 */
public class Vec2 {

    /**
     * Vector X component.
     */
    private float x;

    /**
     * Vector Y component.
     */
    private float y;

    public Vec2() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(float y) {
        x = this.y =y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void equal(Vec2 vec){
        x = vec.getX();
        y = vec.getY();
    }

    public Vec2 multiply(float f, boolean b){
        return new Vec2(x*f,y*f);
    }

    public void remove(Vec2 vec){
        x -= vec.getX();
        y -= vec.getY();
    }

    public Vec2 remove(float f, boolean b){
        return new Vec2(x-f,y-f);
    }

    public Vec2 remove(Vec2 vec, boolean b){
        return new Vec2(x-vec.getX(),y-vec.getY());
    }

    public Vec2 copy(){
        return new Vec2(this.getX(), this.getY());
    }
}

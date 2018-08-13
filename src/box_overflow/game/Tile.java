package box_overflow.game;


import box_overflow.screen.render.texture.Texture;

public class Tile {

    public static final int EMPTY = 0;
    public static final int SOLID = 1;
    public static final int BEGIN = 2;
    public static final int END = 3;
    public static final int HOLE = 4;
    public static final int DEATH = 5;

    public boolean block;

    private Texture image;
    private int type;

    public Tile(){}

    public Tile(String path, int type, boolean block){
        image = new Texture(path);
        this.type = type;
        this.block = block;
    }

    public void bind(){
        image.bind();
    }

    public int getType(){
        return type;
    }

    public void unload(){
        if(image != null)
        image.unload();
    }

    public boolean getBlock(){return block;}
}

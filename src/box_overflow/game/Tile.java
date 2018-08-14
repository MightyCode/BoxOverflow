package box_overflow.game;


import box_overflow.screen.render.texture.Texture;

public class Tile {

    public static final int STATES = 7;
    public static final int EMPTY = 0;
    public static final int SOLID = 1;
    public static final int BEGIN = 2;
    public static final int END = 3;
    public static final int HOLE = 4;
    public static final int DEATH = 5;
    public static final int POSEABLE = 6;

    private Texture image;
    private boolean types[];

    public Tile(String path, int... type){
        this(type);
        image = new Texture(path);

    }

    public Tile(int ... type){
        types = new boolean[STATES];
        for(boolean typ:types){
           typ = false;
        }

        for (int aType : type) {
            types[aType] = true;
        }
    }

    public void bind(){
        image.bind();
    }

    public boolean isType(int typeChoose){
        return types[typeChoose];
    }

    public void unload(){
        if(image != null)
        image.unload();
    }
}

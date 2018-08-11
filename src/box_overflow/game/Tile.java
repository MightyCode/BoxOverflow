package box_overflow.game;


import box_overflow.screen.render.texture.Texture;

public class Tile {
    private Texture image;
    private int type;

    public Tile(){}

    public Tile(String path, int type){
        image = new Texture(path);
        this.type = type;
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
}

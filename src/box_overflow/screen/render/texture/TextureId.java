package box_overflow.screen.render.texture;

public class TextureId {
    private String path;
    private int id;

    TextureId(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

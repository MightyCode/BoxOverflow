package box_overflow.screen.render.texture;

import java.util.ArrayList;

public class TextureManager {
    private ArrayList<TextureId> textureIds;

    public TextureManager(){
        textureIds = new ArrayList<>();
    }

    public void add(TextureId texId){
        textureIds.add(texId);
    }

    public void remove(TextureId texId){
        textureIds.remove(texId);
    }

    public void state(){

        if(textureIds.size()>0){
            System.out.println("\u001B[31m\nRemaining textures : " + textureIds.size() + "");
            System.out.println("\n::::::::::::::::::::");
            for(TextureId texx : textureIds) {
                System.out.println("Texture " + texx.getId() + ", from " + texx.getPath());
            }
            if(textureIds.size()>0) System.out.println("::::::::::::::::::::\n\u001B[30m");
        }else{
            System.out.println("\u001B[32m\nNo remaining textures\u001B[30m\n");
        }
    }
}

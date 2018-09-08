package box_overflow.screen.render.texture;

import box_overflow.main.Window;

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
        Window.console.println("\u001B[34m\n"+ textureIds.size()+" Textures currently load :");
        Window.console.println("\n::::::::::::::::::::");
        for(TextureId texx : textureIds) {
            Window.console.println("Texture " + texx.getId() + ", from " + texx.getPath());
        }
        Window.console.println("::::::::::::::::::::\n\u001B[30m");
    }

    public void endState(){
        if(textureIds.size()>0){
            Window.console.println("\u001B[31m\nRemaining textures don't unload : " + textureIds.size() + "");
            Window.console.println("\n::::::::::::::::::::");
            for(TextureId texx : textureIds) {
                Window.console.println("Texture " + texx.getId() + ", from " + texx.getPath());
            }
            Window.console.println("::::::::::::::::::::\n\u001B[30m");
        } else{
            Window.console.println("\u001B[32m\nNo remaining textures\u001B[30m\n");
        }
    }
}

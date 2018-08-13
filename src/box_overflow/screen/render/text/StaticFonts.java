package box_overflow.screen.render.text;

public class StaticFonts {

    public static FontFace IBM = new FontFace("IBM");
    public static FontFace IBM_Stretch = new FontFace("IBM_Stretch");
    public static FontFace monofonto = new FontFace("monofonto");
    public static FontFace minecraft = new FontFace("minecraft");

    public static void unload(){
        IBM.unload();
        IBM_Stretch.unload();
        monofonto.unload();
        minecraft.unload();
    }
}

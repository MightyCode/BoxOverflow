package box_overflow.screen.overlay;

import box_overflow.entity.gui.GUIButton;
import box_overflow.main.Config;
import box_overflow.main.Window;
import box_overflow.screen.GameManager;
import box_overflow.screen.render.Render;
import box_overflow.screen.render.text.FontRenderer;
import box_overflow.screen.render.text.StaticFonts;
import box_overflow.screen.render.texture.Texture;
import box_overflow.screen.render.texture.TextureRenderer;
import box_overflow.screen.screens.Screen;
import box_overflow.util.TextManager;
import box_overflow.util.math.Color4;
import box_overflow.util.math.Vec2;

public class HelpOverlay extends Overlay{
    private Texture background;
    private FontRenderer title, helpMovement, helpReset, rule, helpEscape;
    private Texture right,left,up,down,r;
    private GUIButton quit;

    public HelpOverlay(Screen screen) {
        super(screen);
        Render.setClearColor(new Color4(0.9f,0.9f,0.9f,1.0f));
        title = new FontRenderer(TextManager.HELP, 0, StaticFonts.monofonto, Window.width * 0.05f,
                new Vec2(Window.width * 0.5f, Window.height * 0.1f), Color4.BLACK.copy());
        rule = new FontRenderer(TextManager.HELP, 3, StaticFonts.monofonto, Window.width * 0.03f,
                new Vec2(Window.width * 0.5f, Window.height * 0.25f), Color4.BLACK.copy());
        helpMovement = new FontRenderer(TextManager.HELP, 1, StaticFonts.monofonto, Window.width * 0.03f,
                new Vec2(Window.width * 0.20f, Window.height * 0.55f), Color4.BLACK.copy());
        helpReset = new FontRenderer(TextManager.HELP, 2, StaticFonts.monofonto, Window.width * 0.03f,
                new Vec2(Window.width * 0.80f, Window.height * 0.55f), Color4.BLACK.copy());
        right = new Texture("/textures/menu/help-right.png");
        left = new Texture("/textures/menu/help-left.png");
        up = new Texture("/textures/menu/help-up.png");
        down = new Texture("/textures/menu/help-down.png");
        r = new Texture("/textures/menu/help-r.png");
        background = new Texture("/textures/menu/background.png");

        helpEscape = new FontRenderer(TextManager.HELP, 5, StaticFonts.monofonto, Window.width * 0.025f,
                new Vec2(Window.width * 0.5f, Window.height * 0.40f), Color4.BLACK.copy());

        Vec2 size = new Vec2(Window.width / 4f, Window.height / 20f);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;
        quit = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.90f),
                size,
                TextManager.OPTIONS,5,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                Window.gameManager.setState(0);
            }
        };
    }

    public void update() {
        if(GameManager.inputsManager.inputPressed(0))Window.gameManager.setState(0);
        quit.update();
    }


    public void display() {
        Render.clear();
        background.bind();
        TextureRenderer.imageC(0,0,Window.width,Window.height);
        title.renderC();
        rule.renderC();
        helpMovement.renderC();
        helpReset.renderC();
        right.bind();
        TextureRenderer.imageC(Window.width*0.23f,Window.height*0.67f, Window.width*0.05f,Window.width*0.05f);
        left.bind();
        TextureRenderer.imageC(Window.width*0.10f,Window.height*0.67f, Window.width*0.05f,Window.width*0.05f);
        up.bind();
        TextureRenderer.imageC(Window.width*0.165f,Window.height*0.60f, Window.width*0.05f,Window.width*0.05f);
        down.bind();
        TextureRenderer.imageC(Window.width*0.165f,Window.height*0.73f, Window.width*0.05f,Window.width*0.05f);
        r.bind();
        TextureRenderer.imageC(Window.width*0.78f,Window.height*0.65f, Window.width*0.05f,Window.width*0.05f);
        quit.display();
        helpEscape.renderC();
    }


    public void unload() {
        title.unload();
        helpMovement.unload();
        helpReset.unload();
        quit.unload();
        right.unload();
        left.unload();
        up.unload();
        down.unload();
        r.unload();
        rule.unload();
        background.unload();
        quit.unload();
        helpEscape.unload();
    }
}

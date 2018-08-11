package box_overflow.screen.overlay;

import box_overflow.entity.gui.GUIButton;
import box_overflow.main.Config;
import box_overflow.main.Window;
import box_overflow.screen.GameManager;
import box_overflow.screen.render.shape.ShapeRenderer;
import box_overflow.screen.render.text.FontRenderer;
import box_overflow.screen.render.text.StaticFonts;
import box_overflow.screen.screens.GameScreen;
import box_overflow.screen.screens.Screen;
import box_overflow.util.TextManager;
import box_overflow.util.math.Color4;
import box_overflow.util.math.Vec2;

public class WinOverlay extends Overlay{

    private FontRenderer win;

    private GUIButton next, retry, menu;

    public WinOverlay(Screen screen){
        super(screen);
        // Title
        win = new FontRenderer(TextManager.WIN,0, StaticFonts.IBM, Window.width*0.05f, new Vec2(), Color4.WHITE);
        win.setPos(new Vec2(Window.width * 0.5f, Window.height * 0.20f));

        Vec2 size = new Vec2(Window.width / 4f, Window.height / 20f);
        Color4 backgroundColor = new Color4(0.40f, 0.65f, 0.65f, 0.5f);
        Color4 hoverColor = new Color4(0.40f, 0.65f, 0.65f, 0.95f);
        Color4 textColor = new Color4(0.8f, 0.8f, 0.8f, 1.0f);
        Color4 hoverTextColor = Color4.WHITE;

        next = new GUIButton(
                new Vec2(Window.width / 2, Window.height/2.4f), size,
                TextManager.WIN,1,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                Config.setCurrentMap(Config.getCurrentMap()+1);
                GameScreen.lvm.load();
                GameScreen.setState(GameScreen.STATE_NORMAL);
            }
        };

        retry = new GUIButton(
                new Vec2(Window.width / 2, Window.height/2f),
                size,
                TextManager.WIN,2,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                GameScreen.setState(GameScreen.STATE_NORMAL);
                GameScreen.lvm.load();
            }
        };

        menu = new GUIButton(
                new Vec2(Window.width / 2, Window.height/1.71f),
                size,
                TextManager.WIN,3,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                Screen.setState(GameScreen.STATE_NORMAL);
                Window.gameManager.setScreen(GameManager.MENUSCREEN);
            }
        };
    }

    public void update() {
        next.update();
        retry.update();
        menu.update();
    }


    public void display() {
        ShapeRenderer.rectC(new Vec2(), new Vec2(Window.width, Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.5f));
        ShapeRenderer.rectC(new Vec2(0.1f * Window.width, 0.15f * Window.height),
                new Vec2(0.8f * Window.width, 0.75f * Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.4f));

        win.render();
        next.display();
        retry.display();
        menu.display();
    }


    public void unload() {
        next.unload();
        retry.unload();
        menu.unload();
    }
}

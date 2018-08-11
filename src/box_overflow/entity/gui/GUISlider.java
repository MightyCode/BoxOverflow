package box_overflow.entity.gui;

import box_overflow.inputs.MouseManager;
import box_overflow.screen.render.Animation;
import box_overflow.screen.render.text.FontFace;
import box_overflow.screen.render.text.FontRenderer;
import box_overflow.screen.render.texture.TextureRenderer;
import box_overflow.util.math.Color4;
import box_overflow.util.math.Math;
import box_overflow.util.math.Vec2;

public class GUISlider extends GUIComponent{

    /**
     * The colors of text of the check box.
     */
    private Color4 textColor;
    private Color4 hoverTextColor;

    /**
     * The colors of the slider.
     */

    protected float value;

    Animation slider, slider_hover;

    /**
     * The font renderer use by the check box.
     */
    private FontRenderer fontRenderer;

    private Vec2 cursorSize, cursorPos;

    private float minValue, maxValue;

    private int oldSizeText;

    /**
     * GUICheck box class constructor with the number of the global string.
     *
     * @param pos Position of the button.
     * @param size Size of the button.
     * @param number The number of the global string to display.
     * @param font The font use by its font renderer.
     * @param textColor The normal text color.
     * @param hoverTextColor The color of the text when the button is hovering.
     */
    public GUISlider(Vec2 pos, Vec2 size, int screen, int number, FontFace font, Color4 textColor, Color4 hoverTextColor, float minValue, float maxValue, int value){
        super(new Vec2(pos.getX() - (size.getX() / 2), pos.getY() - (size.getY() / 2)),size);

        // Loading animations
        animations.add(new Animation("/textures/menu/GUISlider.png"));
        animations.add(new Animation("/textures/menu/GUISlider-hover.png"));

        // Button of the slider
        slider = new Animation("/textures/menu/GUISliderButton.png");
        slider_hover = new Animation("/textures/menu/GUISliderButton-hover.png");

        this.textColor = textColor;
        this.hoverTextColor = hoverTextColor;

        fontRenderer = new FontRenderer(screen, number, font, size.getY()*0.4f, pos, textColor);
        fontRenderer.setPos(new Vec2(this.pos.getX() + size.getX()/2, this.pos.getY()+size.getX()*0.1f));
        fontRenderer.setText(fontRenderer.getText()+value);
        oldSizeText = fontRenderer.getText().length();

        this.minValue = minValue;
        this.maxValue = maxValue;

        this.value = value;
        cursorSize = new Vec2(this.size.getY()*0.30f);
        cursorPos = new Vec2();
        cursorPos.setX(Math.map(value,minValue,maxValue,
                this.pos.getX()+this.size.getX()*0.1f - cursorSize.getX()/2f,this.pos.getX()+ this.size.getX()*0.9f - cursorSize.getX()/2f));
        cursorPos.setY(this.pos.getY() +  this.size.getY()*0.7f - cursorSize.getY()/2);
    }

    /**
     * Update the button.
     */
    public void update() {
        super.update();
        if(oldSizeText == fontRenderer.getText().length()){
            fontRenderer.setText(fontRenderer.getText().substring(0,fontRenderer.getText().length() - String.valueOf((int)value).length()));
            value = Math.map(cursorPos.getX(),
                    this.pos.getX()+this.size.getX()*0.1f - cursorSize.getX()/2,
                    this.pos.getX()+ this.size.getX()*0.9f - cursorSize.getX()/2,minValue,maxValue);
        }
        fontRenderer.setText(fontRenderer.getText() + (int)value);

        oldSizeText = fontRenderer.getText().length();
        if (lock) return;

        mouseOver = mouseOver();
        if (mouseOver){
            animationPlayed = HOVER;
            if (MouseManager.button(0)) {
                cursorPos.setX(MouseManager.mouseX() - cursorSize.getX()/2);
                if(MouseManager.mouseX() < pos.getX() + size.getX()*0.1f){
                    cursorPos.setX(pos.getX() + size.getX()*0.1f - cursorSize.getX()/2);
                }else if(MouseManager.mouseX() > pos.getX() + size.getX()*0.9f){
                    cursorPos.setX(pos.getX() + size.getX()*0.9f - cursorSize.getX()/2);
                }
                action();
            }
        } else{
            animationPlayed = UNHOVER;
        }
    }

    /**
     * Display the button.
     */
    public void display() {
        animations.get(animationPlayed).bind();
        TextureRenderer.imageC(pos,size);

        slider.bind();
        if(mouseOver) slider_hover.bind();
        TextureRenderer.imageC(cursorPos, cursorSize);

        fontRenderer.render();
    }

    /**
     * Free the memory.
     */
    public void unload(){
        super.unload();
        slider.unload();
        slider_hover.unload();
        fontRenderer.unload();
    }
}

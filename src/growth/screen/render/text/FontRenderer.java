package growth.screen.render.text;

import growth.util.math.Color4;
import growth.util.math.Vec2;
import growth.screen.GameManager;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings("JavaDoc")
public class FontRenderer {

    /**
     * Font used for rendering.
     */
    private FontFace font;

    /**
     * Text that will be rendered.
     */
    private String text;

    /**
     * Size of the rendered text (in pixels).
     */
    private float size;

    /**
     * Position of the text.
     */
    private Vec2 originPos, pos;

    /**
     * Color of the text.
     */
    private Color4 color;

    /**
     * Width of text.
     */
    private float width;

    /**
     * Text mesh position data.
     */
    private List<Vec2> mesh;

    /**
     * Text mesh texture coordinates.
     */
    private List<Vec2> texture;

    /**
     *
     */
    private int wordNumber;

    private int screenNumber;


    /**
     *
     * @param text
     * @param font
     * @param size
     * @param pos
     * @param color
     */
    @SuppressWarnings("JavaDoc")
    public FontRenderer(String text, FontFace font, float size, Vec2 pos, Color4 color) {
        this.font = font;
        this.text = text;
        this.size = size;
        this.pos = new Vec2();
        calc();
        setPos(pos);
        this.color = color;
    }

    public FontRenderer(int screen, int wordNumber, FontFace font, float size, Vec2 pos, Color4 color) {
        this.screenNumber = screen;
        this.wordNumber = wordNumber;
        this.font = font;
        this.text = GameManager.textManager.getWord(this, screen, wordNumber);
        this.size = size;
        calc();
        setPos(pos);
        this.color = color;
    }

    /**
     * Calculate mesh position data and texture coordinates.
     */
    private void calc() {
        mesh = new ArrayList<>();
        texture = new ArrayList<>();
        String[] lines = text.split("\n");

        float lineY = 0;
        float currentX = 0;

        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                FontChar fontChar = font.getFontFile().getCharacter((int) c);

                mesh.add(new Vec2(
                        currentX + fontChar.getxOffset(),
                        lineY + fontChar.getyOffset()
                ));
                mesh.add(new Vec2(
                        currentX + fontChar.getxOffset() + fontChar.getWidth(),
                        lineY + fontChar.getyOffset()
                ));
                mesh.add(new Vec2(
                        currentX + fontChar.getxOffset() + fontChar.getWidth(),
                        lineY + fontChar.getyOffset() + fontChar.getHeight()
                ));
                mesh.add(new Vec2(
                        currentX + fontChar.getxOffset(),
                        lineY + fontChar.getyOffset() + fontChar.getHeight()
                ));

                texture.add(new Vec2(
                        fontChar.getxAtlas(),
                        fontChar.getyAtlas()
                ));
                texture.add(new Vec2(
                        fontChar.getxAtlas() + fontChar.getWidthAtlas(),
                        fontChar.getyAtlas()
                ));
                texture.add(new Vec2(
                        fontChar.getxAtlas() + fontChar.getWidthAtlas(),
                        fontChar.getyAtlas() + fontChar.getHeightAtlas()
                ));
                texture.add(new Vec2(
                        fontChar.getxAtlas(),
                        fontChar.getyAtlas() + fontChar.getHeightAtlas()
                ));

                currentX += fontChar.getxAdvance();
            }

            width = Math.max(0, currentX);

            currentX = 0;
            lineY += font.getFontFile().getLineHeight();
        }
    }

    /**
     * Render the text.
     */
    public void render() {
        font.getTexture().bind();
        glColor4f(color.getR(), color.getG(), color.getB(), color.getA());
        glBegin(GL_QUADS);
            for (int i = 0; i < mesh.size(); i++) {
                glTexCoord2f(texture.get(i).getX(), texture.get(i).getY());
                glVertex2f(mesh.get(i).getX() * size + pos.getX() - GameManager.CAMERA.getPosX(), mesh.get(i).getY() * size + pos.getY() - GameManager.CAMERA.getPosY());
            }
        glEnd();
    }

    /**
     * Set the text to be rendered.
     *
     * @param text Text to render.
     */
    public void setText(String text) {
        this.text = text;
        calc();
        setPos(originPos);
    }

    /**
     * Get currently rendered text.
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Get font used to render.
     *
     * @return font
     */
    public FontFace getFont() {
        return font;
    }

    /**
     * Set font used to render.
     *
     * @param font Font of the rendered text.
     */
    public void setFont(FontFace font) {
        this.font = font;
        setText(text);
    }

    public int getSreenNumber() { return screenNumber; }

    public void setWordNumber(int screen, int word) {
        screenNumber = screen;
        wordNumber = word;
        update();
    }

    /**
     * Get text size.
     *
     * @return size
     */
    public float getSize() {
        return size;
    }

    /**
     * Set text size.
     *
     * @param size Size of the rendered text.
     */
    public void setSize(float size) {
        this.size = size;
    }

    /**
     * Get position of the text.
     *
     * @return position
     */
    public Vec2 getPos() {
        return pos;
    }

    /**
     * Set position of the text.
     *
     * @param pos Position of the text on screen.
     */
    public void setPos(Vec2 pos) {
        this.pos = new Vec2();
        originPos = new Vec2(pos.getX(), pos.getY());
        this.pos.setPosition(originPos.getX() - (getWidth()/2), originPos.getY() - size/2);
    }

    public void setOpacity(float newOpacity){
        color.setA(newOpacity);
    }

    /**
     * Get text color.
     *
     * @return color
     */
    public Color4 getColor() {
        return color;
    }

    /**
     * Set text color.
     *
     * @param color Color of the rendered text.
     */
    public void setColor(Color4 color) {
        this.color = color;
    }

    /**
     * Get text width;
     *
     * @return width;
     */
    public float getWidth() {
        return width * size;
    }

    public void update(){
        setText(GameManager.textManager.getWord(screenNumber, wordNumber));
    }

    public void unload(){
        GameManager.textManager.remove(this);
    }
}

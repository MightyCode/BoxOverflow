package growth.screen.render.text;

public class FontChar {

    /**
     * Ascii code of the character.
     */
    private int id;

    /**
     * X position of the top left corner of the character in texture coordinates.
     */
    private float xAtlas;

    /**
     * Y position of the top left corner of the character in texture coordinates.
     */
    private float yAtlas;

    /**
     * Width of the character in screen coordinates.
     */
    private float width;

    /**
     * Height of the character in screen coordinates.
     */
    private float height;

    /**
     * Width of the character in texture coordinates.
     */
    private float widthAtlas;

    /**
     * Height of the character in texture coordinates.
     */
    private float heightAtlas;

    /**
     * X offset between the character and print line in screen coordinates.
     */
    private float xOffset;

    /**
     * Y offset between the character and print line in screen coordinates.
     */
    private float yOffset;

    /**
     * How much to add to X cursor position after printing the character.
     */
    private float xAdvance;

    public FontChar(int id, float xAtlas, float yAtlas, float width, float height, float widthAtlas, float heightAtlas, float xOffset, float yOffset, float xAdvance) {
        this.id = id;
        this.xAtlas = xAtlas;
        this.yAtlas = yAtlas;
        this.width = width;
        this.height = height;
        this.widthAtlas = widthAtlas;
        this.heightAtlas = heightAtlas;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xAdvance = xAdvance;
    }

    /**
     * Get character Ascii code.
     *
     * @return ASCII code.
     */
    public int getId() {
        return id;
    }

    /**
     * Get xAtlas.
     *
     * @return xAtlas.
     */
    public float getxAtlas() {
        return xAtlas;
    }

    /**
     * Get yAtlas.
     *
     * @return yAtlas.
     */
    public float getyAtlas() {
        return yAtlas;
    }

    /**
     * Get character Width.
     *
     * @return Width.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Get character Height.
     *
     * @return Height.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Get character atlas Width.
     *
     * @return Atlas Width.
     */
    public float getWidthAtlas() {
        return widthAtlas;
    }

    /**
     * Get character atlas Height.
     *
     * @return Atlas Height.
     */
    public float getHeightAtlas() {
        return heightAtlas;
    }

    /**
     * Get xOffset.
     *
     * @return xOffset.
     */
    public float getxOffset() {
        return xOffset;
    }

    /**
     * Get yOffset.
     *
     * @return yOffset.
     */
    public float getyOffset() {
        return yOffset;
    }

    /**
     * Get xAdvance.
     *
     * @return xAdvance.
     */
    public float getxAdvance() {
        return xAdvance;
    }
}

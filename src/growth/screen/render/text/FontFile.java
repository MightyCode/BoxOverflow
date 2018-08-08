package growth.screen.render.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FontFile {

    /**
     * Character padding.
     */
    private int paddingLeft,
                paddingRight,
                paddingTop,
                paddingBottom;

    /**
     * Font size in atlas.
     */
    private int size;

    /**
     * Height of each line of text.
     */
    private float lineHeight;

    /**
     * Atlas Size.
     */
    private int atlasWidth,
                atlasHeight;

    /**
     * Number of characters in the Atlas.
     */
    private int numChars;

    /**
     * Characters.
     */
    private Map<Integer, FontChar> characters;

    /**
     * Desired padding between characters.
     */
    private static final int NEEDED_PADDING = 2;

    public FontFile(String path) {
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(path)));

            String line;
            Map<String, String> values = new HashMap<String, String>();

            line = bufferedReader.readLine();
            values = decodeLine(line);

            size = Integer.parseInt(values.get("size"));

            String[] padding = values.get("padding").split(",");

            paddingTop = Integer.parseInt(padding[0]);
            paddingRight = Integer.parseInt(padding[1]);
            paddingBottom = Integer.parseInt(padding[2]);
            paddingLeft = Integer.parseInt(padding[3]);

            line = bufferedReader.readLine();
            values = decodeLine(line);

            lineHeight = (float) (Integer.parseInt(values.get("lineHeight")) - paddingTop - paddingBottom) / size;

            atlasWidth = Integer.parseInt(values.get("scaleW"));
            atlasHeight = Integer.parseInt(values.get("scaleH"));

            bufferedReader.readLine();

            line = bufferedReader.readLine();
            values = decodeLine(line);

            numChars = Integer.parseInt(values.get("count"));

            characters = new HashMap<Integer, FontChar>(numChars);

            for (int i = 0; i < numChars; i++) {
                line = bufferedReader.readLine();
                values = decodeLine(line);

                int id = Integer.parseInt(values.get("id"));
                float xTex = (float) (Integer.parseInt(values.get("x")) + paddingLeft - NEEDED_PADDING) / atlasWidth;
                float yTex = (float) (Integer.parseInt(values.get("y")) + paddingTop - NEEDED_PADDING) / atlasHeight;
                float widthChar = (float) (Integer.parseInt(values.get("width")) - paddingLeft - paddingRight + (2 * NEEDED_PADDING)) / size;
                float heightChar = (float) (Integer.parseInt(values.get("height")) - paddingTop - paddingBottom + (2 * NEEDED_PADDING)) / size;
                float widthAtlas = (float) (Integer.parseInt(values.get("width")) - paddingLeft - paddingRight + (2 * NEEDED_PADDING)) / atlasWidth;
                float heightAtlas = (float) (Integer.parseInt(values.get("height")) - paddingTop - paddingBottom + (2 * NEEDED_PADDING)) / atlasHeight;
                float xoffset = (float) (Integer.parseInt(values.get("xoffset")) + paddingLeft - NEEDED_PADDING) / size;
                float yoffset = (float) (Integer.parseInt(values.get("yoffset")) + paddingTop - NEEDED_PADDING) / size;
                float xadvance = (float) (Integer.parseInt(values.get("xadvance")) - paddingLeft - paddingRight) / size;

                characters.put(id, new FontChar(
                        id,
                        xTex,
                        yTex,
                        widthChar,
                        heightChar,
                        widthAtlas,
                        heightAtlas,
                        xoffset,
                        yoffset,
                        xadvance
                ));
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Extract values in font file line.
     *
     * @param line Line from font file.
     * @return Values array.
     */
    private Map<String, String> decodeLine(String line) {
        String[] parts = line.split(" ");
        Map<String, String> values = new HashMap<String, String>();

        for (String part : parts) {
            String[] valueData = part.split("=");

            if (valueData.length == 2) {
                values.put(valueData[0], valueData[1]);
            }
        }

        return values;
    }

    /**
     * Get character from its ASCII code.
     *
     * @param code ASCII code of the character.
     * @return Font Character.
     */
    public FontChar getCharacter(int code) {
        return characters.get(code);
    }

    /**
     * Get height of text line.
     *
     * @return lineHeight.
     */
    public float getLineHeight() {
        return lineHeight;
    }
}

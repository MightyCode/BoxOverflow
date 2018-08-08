package growth.util;

import growth.main.Config;
import growth.screen.render.text.FontRenderer;

import java.util.ArrayList;

/**
 * TextManager.
 * This class contains every sentences and word use on the game.
 * It is used to change the language during the game.
 */
public class TextManager {

    public static final int MENU = 0;
    public static final int PAUSE = 1;
    public static final int DEATH = 2;
    public static final int OPTIONS = 3;
    public static final int ZONE = 4;
    public static final int LOCATION = 5;

    /**
     * The sentences of the game.
     */
    private String[][] word;

    /**
     * Font renders using the word.
     */
    private ArrayList<FontRenderer> users;

    /**
     * Class constructor, load the word.
     */
    public TextManager() {
        word = XmlReader.loadWord();
        users = new ArrayList<>();
    }

    /**
     * Method to a font renderer get a sentence and register to a
     * @param newUser The new users.
     * @param number The place number of the sentence on the table.
     * @return The sentence (String).
     */
    public String getWord(FontRenderer newUser, int screen, int number) {
        users.add(newUser);
        return word[screen][number];
    }

    /**
     * Get a sentence without register.
     * @param number The place number of the sentence on the table.
     * @return The sentence (String).
     */
    public String getWord(int screen, int number) {
        return word[screen][number];
    }

    /**
     * Change the current language use to another language and update every font renderer.
     * @param newLanguage The diminutive of the new language.
     */
    public void changeLanguage(String newLanguage) {
        // Change the param
        XmlReader.changeValue(Config.CONFIG_PATH, "language", newLanguage, "general");
        Config.setLanguage(newLanguage);
        // Reload the new language
        word = XmlReader.loadWord();
        // Update every text users object
        for (FontRenderer user : users) {
            user.update();
        }
    }

    /**
     * To delete a saved renderer.
     * @param renderer The renderer to delete.
     */
    public void remove(FontRenderer renderer) {
        users.remove(renderer);
    }
}

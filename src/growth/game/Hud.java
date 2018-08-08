package growth.game;

import growth.main.Window;
import growth.screen.render.shape.ShapeRenderer;
import growth.screen.render.text.FontRenderer;
import growth.screen.render.text.StaticFonts;
import growth.screen.render.texture.Texture;
import growth.screen.render.texture.TextureRenderer;
import growth.util.TextManager;
import growth.util.math.Color4;
import growth.util.math.Math;
import growth.util.math.Vec2;

import static java.lang.Math.*;

/**
 * Class of the game's hud.
 *
 * @author MightyCode
 * @version of game : 1
 */
public class Hud {

    /**
     * Maximum health.
     * This variable contains the max health of the player.
     */
    private int maxHealth;

    /**
     * Current health.
     * This variable contains the current health of the player.
     */
    private int currentHealth;

    /**
     * Heart id.
     * This table contains the type of each heart displayed.
     */
    private int[]heartType;

    /**
     * Heart position.
     * This table contains the position of each heart displayed.
     */
    private Vec2[] heartPosDisplayed;

    /**
     * Heart size.
     * This table contains the size of each heart displayed.
     */
    private Vec2[] heartSizeDisplayed;

    /**
     * Current heart use.
     * This variable contains the number of the last useful heart.
     */
    private int currentHeartUse;

    /**
     * Heart size
     * These variables contain the base size of heart.
     */
    private Vec2 heartSize;

    /**
     * Heart size use on calcs.
     */
    private Vec2 heartSizeT;

    /**
     * Space between two heart.
     * This variable contains the number of pixel between two heart.
     */
    private float spaceBetweenTwoHeart;

    /**
     * Number of heart..
     * This variable contains the number of displayed.
     */
    private int numHeart;

    /**
     * Sin counter.
     * This  variable contains the counter to apply the animation of the current heart use.
     */
    private float sinCounter;


    /**
     * Hud class constructor.
     * Charge the textures.
     * And set the param
     */
    public Hud(){
        // Set the size of heart and the size, position of the acorn counter
        heartSize = new Vec2(Window.height * 0.06f,Window.height * 0.06f);
        heartSizeT = new Vec2();

        spaceBetweenTwoHeart = heartSize.getX()*1.4f;

        // Set the heart's bar
        currentHealth = 0;
        setMaxHealth(maxHealth = 0);
        sinCounter = 0;
    }

    /**
     * Update the Hud.
     */
    public void update(){
        Vec2 oldSize = heartSizeDisplayed[currentHeartUse].copy();

        heartSizeDisplayed[currentHeartUse].equal(heartSizeT.multiply((float)sin(sinCounter)*0.07f+1.15f,true));
        heartPosDisplayed[currentHeartUse].remove(heartSizeDisplayed[currentHeartUse].remove(oldSize, true).multiply(0.5f,true));

        sinCounter+= PI*2/120;
        if(sinCounter > PI*2)sinCounter = 0;
    }

    /**
     * Display the Hud.
     */
    public void display(){
        for(int i = 0; i < numHeart; i++) {
            float color = 0.8f;
            if(heartType[i] == 1) color = 0.5f;
            else if(heartType[i] == 2) color = 0.1f;
            ShapeRenderer.rectC(heartPosDisplayed[i], heartSizeDisplayed[i], new Color4(color));
        }
    }

    /**
     * Change display when the life of the player change.
     *
     * @param newHealth The new health point of the player.
     */
    public void setHearth(int newHealth){
        // Set the new current Health Point
        if(newHealth > maxHealth || newHealth < 0) return;
        currentHealth = newHealth;

        // Set the id of the hearth for each receptacle
        int i = 0;
        while(i < newHealth/2){
            heartType[i] = 2;
            heartSizeDisplayed[i] = new Vec2();
            heartSizeDisplayed[i].equal(heartSizeT);

            if(((double)newHealth)/2 == newHealth/2){
                if(i+1 == newHealth/2){
                    currentHeartUse = i;
                }
            }
            i++;
        }

        if(((double)newHealth)/2 != newHealth/2) {
            heartType[i] = 1;
            heartSizeDisplayed[i] = new Vec2();
            heartSizeDisplayed[i].equal(heartSizeT);
            currentHeartUse = i;
            i++;
        }

        while(i < maxHealth/2){
            heartType[i] = 0;
            heartSizeDisplayed[i] = new Vec2();
            heartSizeDisplayed[i] = (heartSizeT.multiply(0.9f,true));
            i++;
        }

        // i count the number of heart
        numHeart = i;

        float center = Window.width/2;

        // If the number of heart is pair
        if(((double)i)/2 == i/2){
           for(int a = 0; a < numHeart; a++) {
               // Set position of each heart
               heartPosDisplayed[a] = new Vec2();
               heartPosDisplayed[a].setX(center + (a-(numHeart/2)) * spaceBetweenTwoHeart);
               heartPosDisplayed[a].setY(Window.height*0.02f);
           }
        } else {
            for(int a = 0; a < numHeart; a++) {
                // Set position of each heart
                heartPosDisplayed[a] = new Vec2();
                heartPosDisplayed[a].setX(center - (heartSize.getX()/2) + (a-(numHeart/2)) * spaceBetweenTwoHeart);
                heartPosDisplayed[a].setY(Window.height*0.02f);
            }
        }
    }

    /**
     * Change display when the life of the player change.
     *
     * @param newMaxHealth The new maximum health point of the player.
     */
    public void setMaxHealth(int newMaxHealth){
        if(newMaxHealth <= 0) return;

        heartSizeT.equal(heartSize.multiply((1-newMaxHealth*0.01f),true));
        spaceBetweenTwoHeart = heartSizeT.getX()*1.4f;

        this.maxHealth = newMaxHealth;
        heartType = new int[(int) ceil((double) newMaxHealth / 2)];
        heartPosDisplayed = new Vec2[(int) ceil((double) newMaxHealth / 2)];
        heartSizeDisplayed = new Vec2[(int) ceil((double) newMaxHealth / 2)];

        if (currentHealth > maxHealth) currentHealth = maxHealth;
        setHearth(currentHealth);
    }

    /**
     * Unload the texture.
     */
    public void unload(){
    }
}

package ludum42.entity.type;

import ludum42.entity.Eobject.Echaracter;
import ludum42.screen.screens.GameScreen;
import ludum42.screen.screens.Screen;
import ludum42.util.math.Vec2;

/**
 * Player class.
 * This class is the class of player controlled by the played.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Player extends Echaracter {

	/**
	 * Player class constructor.
	 * Firstly call the mother class constructor.
	 * Init all of player variable.
	 */
	public Player(GameScreen gameScreen, Vec2 size) {
		// Call mother constructor
		super(gameScreen);

		/* Init player's variables */
		// Size, and boxSize
		this.pos = new Vec2();
		this.size = size.copy();
		collisionBox = new Vec2(this.size.getX()*0.65f, size.getY());

		setMaxHealthPoint(6);
		setHealthPoint(3);

		// Sprite and Animation
		facing = true;
	}

	/**
	 * Update the player.
	 */
	public void update(){
		super.update();

		//animations.get(animationPlayed).update();
	}

	public void display() {
		/*animations.get(animationPlayed).bind();
		if(animations.size()>0) {
			if (facing) {
				TextureRenderer.image(
						(pos.getX() - size.getX() / 2),
						(pos.getY() - size.getY() / 2),
						size.getX() * 1f, size.getY() * 1f);
			} else {
				TextureRenderer.image(
						(pos.getX() +  size.getX() * 0.5f),
						(pos.getY() - size.getY() / 2),
						- size.getX(), size.getY());
			}
		}*/
	}

	/**
	 * Change the current health value.
	 * @param newValue New current health value.
	 */
	public void setHealthPoint(int newValue){
		super.setHealthPoint(newValue);
		GameScreen.hud.setHearth(healthPoint);
	}

	/**
	 * Change the max value of health.
	 * @param newValue New maximum health value.
	 */
	public void setMaxHealthPoint(int newValue){
		super.setMaxHealthPoint(newValue);
		GameScreen.hud.setMaxHealth(maxHealthPoint);
	}

	/**
	 * When the player die
	 */
	public void died(){
		Screen.setState(GameScreen.STATE_DEATH);
		super.died();
	}
}
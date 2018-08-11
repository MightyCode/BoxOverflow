package box_overflow.entity.type;

import box_overflow.entity.Eobject.Echaracter;
import box_overflow.screen.GameManager;
import box_overflow.screen.render.shape.ShapeRenderer;
import box_overflow.screen.screens.GameScreen;
import box_overflow.util.math.Color4;
import box_overflow.util.math.Vec2;

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

		// Sprite and Animation
		facing = true;
	}

	/**
	 * Update the player.
	 */
	public void update() {
		super.update();
		int nextPosX = (int) pos.getX(), nextPosY = (int) pos.getY();

		if (GameManager.inputsManager.inputPressed(1)) {
			nextPosX--;
		} else if (GameManager.inputsManager.inputPressed(2)) {
			nextPosY--;
		} else if (GameManager.inputsManager.inputPressed(3)) {
			nextPosX++;
		} else if (GameManager.inputsManager.inputPressed(4)) {
			nextPosY++;
		}

		if (GameManager.inputsManager.inputPressed(5)) {
			GameScreen.lvm.load();
			nextPosX = 979246978;
			nextPosY = 9462259;
		}

		if(nextPosX != pos.getX() || nextPosY != pos.getY()) {
			if (!GameScreen.lvm.isSolid(nextPosX, nextPosY)) {
				GameScreen.lvm.addBlock((int) pos.getX(), (int) pos.getY());
				pos.setPosition(nextPosX, nextPosY);
			}
		}
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
		ShapeRenderer.rect(pos.getX()*GameScreen.tile,pos.getY()*GameScreen.tile,size.getX()*GameScreen.tile, size.getY()*GameScreen.tile,new Color4(0.2f,0.2f,0.2f,1f));
	}

	/**
	 * When the player die
	 */
	public void died(){
		GameScreen.lvm.load();
	}
}
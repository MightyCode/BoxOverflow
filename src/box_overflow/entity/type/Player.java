package box_overflow.entity.type;

import box_overflow.entity.Eobject.Echaracter;
import box_overflow.game.LevelManager;
import box_overflow.game.Tile;
import box_overflow.screen.GameManager;
import box_overflow.screen.render.Animation;
import box_overflow.screen.render.texture.TextureRenderer;
import box_overflow.screen.screens.GameScreen;
import box_overflow.util.math.Vec2;

/**
 * Player class.
 * This class is the class of player controlled by the played.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Player extends Echaracter {

	private Vec2 delta, initDelta;

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
		delta = new Vec2();
		initDelta = new Vec2();
		collisionBox = new Vec2(this.size.getX()*0.65f, size.getY());

		animations.add(new Animation("/textures/player/idle0/",2,60));
		animations.add(new Animation("/textures/player/idle1/",2,60));
		animations.add(new Animation("/textures/player/idle2/",2,60));
		animations.add(new Animation("/textures/player/idle3/",2,60));
		animations.add(new Animation("/textures/player/walk0/",8,LevelManager.TRANSITIONTIME/8));
		animations.add(new Animation("/textures/player/walk1/",8,LevelManager.TRANSITIONTIME/8));
		animations.add(new Animation("/textures/player/walk2/",8,LevelManager.TRANSITIONTIME/8));
		animations.add(new Animation("/textures/player/walk3/",8,LevelManager.TRANSITIONTIME/8));
		animations.add(new Animation("/textures/player/win/",2,30));
		animations.add(new Animation("/textures/player/death/",1,1));
	}

	/**
	 * Update the player.
	 */
	public void update() {
		super.update();

		if (GameManager.inputsManager.inputPressed(5) && gameScreen.getState() == GameScreen.STATE_NORMAL) {
			GameScreen.lvm.reset();
		}

		if (GameManager.inputsManager.inputPressed(6)){
			GameScreen.lvm.setPlay();
			return;
		}

		if(GameScreen.lvm.getDeath() || GameScreen.lvm.getBegin()){
			if(GameScreen.lvm.getDeath()) animationPlayed = 9;
			delta.setPosition(LevelManager.TRANSITIONTIME*initDelta.getX(),
					LevelManager.TRANSITIONTIME*initDelta.getY());
			return;
		}else if(GameScreen.lvm.getTransition()) {

			delta.setPosition(delta.getX() + ((float)GameScreen.tile/LevelManager.TRANSITIONTIME*initDelta.getX()),
					delta.getY() + ((float)GameScreen.tile/LevelManager.TRANSITIONTIME*initDelta.getY()));
			return;
		}else if(GameScreen.lvm.getWin()){
			animationPlayed = 8;
			return;
		}

		String nextPosition = "idle";

		if (GameManager.inputsManager.input(1)) {
			nextPosition = "left";
			animationPlayed = 0;
		} else if (GameManager.inputsManager.input(2)) {
			nextPosition = "up";
			animationPlayed = 1;
		} else if (GameManager.inputsManager.input(3)) {
			nextPosition = "right";
			animationPlayed = 2;
		} else if (GameManager.inputsManager.input(4)) {
			nextPosition = "down";
			animationPlayed = 3;
		}

		Vec2 temp = pos.copy();
		pos = GameScreen.lvm.newPos(pos.copy(), nextPosition);
		initDelta.setPosition(-this.pos.getX() + temp.getX(), -this.pos.getY() + temp.getY());
		delta = new Vec2(-initDelta.getX()*GameScreen.tile,-initDelta.getY()*GameScreen.tile);
		if(GameScreen.lvm.checkType(pos, Tile.DEATH)){
			GameScreen.lvm.death();
			delta = new Vec2();
		}
	}

	public void display() {
		animations.get(animationPlayed).bind();
		Vec2 temp = pos.copy().multiply(GameScreen.tile,true);
		Vec2 sizet = size.copy().multiply(GameScreen.tile,true);
		if(animationPlayed == 1 || animationPlayed == 3 || animationPlayed == 5 || animationPlayed == 7 || animationPlayed == 8) {
			sizet.setX(sizet.getX() * 1.20f);
		}


		if(animationPlayed >= 4 && animationPlayed <= 7){
			TextureRenderer.image(
					(temp.getX()  - delta.getX() - sizet.getX()*0.10f),
					(temp.getY() - sizet.getY()*0.33f - delta.getY()),
					sizet.getX() , sizet.getY());
		} else{
			TextureRenderer.image(
					(temp.getX()  - sizet.getX()*0.10f),
					(temp.getY() - sizet.getY()*0.33f),
					sizet.getX() , sizet.getY());
		}
	}

	public Vec2 getDelta(){
		return delta;
	}

	public void stop(){
		delta = new Vec2(0);
		if(animationPlayed >= 4) animationPlayed-=4;
	}

	public void start(){
		if(animationPlayed < 4) animationPlayed+=4;
	}

	public void setSide(int side){
		animationPlayed = side;
	}

	public void died(){
		delta = new Vec2();
		GameScreen.lvm.reset();
	}
}
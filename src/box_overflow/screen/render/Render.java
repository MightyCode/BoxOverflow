package box_overflow.screen.render;

import box_overflow.util.math.Color4;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

/**
 * Class with utility.
 * @author MightyCode
 * @version 1.1
 */
public abstract class Render {

	public static Color4 c;

	/**
	 * Set the 2D view.
	 */
	public static void glEnable2D() {
		int[] vPort = new int[4];

		glGetIntegerv(GL_VIEWPORT, vPort);

		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();

		glOrtho(0, vPort[2], vPort[3], 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();
	}

	/**
	 * Clear the screen with white color.
	 */
	public static void clear(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}


	public static void clear(Color4 ca){
		glClearColor(ca.getR(), ca.getG(), ca.getB(), ca.getA());
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		setClearColor(c);
	}
	/**
	 * Set the clear color.
	 */
	public static void setClearColor(Color4 ca){
		c = ca.copy();
		glClearColor(c.getR(), c.getG(), c.getB(), c.getA());
	}

	/**
	 * Set the 3D view.
	 */
	public static void glDisable2D() {
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();
	}

	public static void setViewPort(int width, int height){
		glViewport(0, 0, width, height);
	}
}

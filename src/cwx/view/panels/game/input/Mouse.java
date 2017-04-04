package cwx.view.panels.game.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

public final class Mouse implements MouseInputListener, MouseMotionListener {

	private int mouseButton = -1, mouseX, mouseY, mouseXDiff, mouseYDiff;
	private boolean mouseDown, mouseReleasing, mouseMoved;
	
	public static final Mouse MOUSE = new Mouse();
	
	/**
	 * This class is instantiated as a Singleton
	 */
	private Mouse() {}
	
	/**
	 * Updates the state of the mouse buttons and location
	 */
	public void update() {
		if (mouseButton > -1) mouseButton = -1;
		if (mouseDown && mouseReleasing) mouseDown = false;
		if (mouseReleasing) mouseReleasing = false;
		if (mouseMoved) mouseMoved = false;
		mouseXDiff = 0;
		mouseYDiff = 0;
	}
	
	/**
	 * Returns if the mouse is down or not
	 * @return If the mouse is down
	 */
	public boolean isMouseDown() {
		return mouseDown;
	}
	
	/**
	 * Returns if the mouse is releasing or not
	 * @return If the mouse is releasing
	 */
	public boolean isMouseReleasing() {
		return mouseReleasing;
	}
	
	/**
	 * Returns the mouse x difference
	 * @return The difference of the x position
	 */
	public int getMouseXDiff() {
		return mouseXDiff;
	}
	
	/**
	 * Returns the mouse y difference
	 * @return The difference of the y position
	 */
	public int getMouseYDiff() {
		return mouseYDiff;
	}
	
	/**
	 * Returns the mouse x
	 * @return The x position of the mouse
	 */
	public int getMouseX() {
		return mouseX;
	}
	
	/**
	 * Returns the mouse y
	 * @return The y position of the mouse
	 */
	public int getMouseY() {
		return mouseY;
	}
	
	/**
	 * Returns if the mouse moved or not
	 * @return If the mouse moved
	 */
	public boolean didMouseMove() {
		return mouseMoved;
	}

	/**
	 * When the mouse is pressed
	 */
	@Override
	public void mousePressed(MouseEvent me) {
		mouseButton = me.getButton();
		mouseDown = true;
		mouseXDiff = me.getX() - mouseX;
		mouseYDiff = me.getY() - mouseY;
		mouseX = me.getX();
		mouseY = me.getY();
	}

	/**
	 * When the mouse is released
	 */
	@Override
	public void mouseReleased(MouseEvent me) {
		mouseReleasing = true;
		mouseXDiff = me.getX() - mouseX;
		mouseYDiff = me.getY() - mouseY;
		mouseX = me.getX();
		mouseY = me.getY();
	}

	/**
	 * When the mouse is moved
	 */
	@Override
	public void mouseMoved(MouseEvent me) {
		mouseXDiff = me.getX() - mouseX;
		mouseYDiff = me.getY() - mouseY;
		mouseX = me.getX();
		mouseY = me.getY();
		mouseMoved = true;
	}

	/**
	 * When the mouse is dragged
	 */
	@Override
	public void mouseDragged(MouseEvent me) {
		mouseButton = me.getButton();
		mouseDown = true;
		mouseXDiff = me.getX() - mouseX;
		mouseYDiff = me.getY() - mouseY;
		mouseX = me.getX();
		mouseY = me.getY();
		mouseMoved = true;
	}
	
	/**
	 * When the mouse enters
	 */
	@Override
	public void mouseEntered(MouseEvent me) {}
	/**
	 * When the mouse exits
	 */
	@Override
	public void mouseExited(MouseEvent me) {}
	/**
	 * When the mouse is clicked
	 */
	@Override
	public void mouseClicked(MouseEvent me) {}
}

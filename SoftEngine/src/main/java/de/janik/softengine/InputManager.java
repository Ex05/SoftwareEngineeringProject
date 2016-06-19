package de.janik.softengine;
// <- Import ->

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON2;
import static java.awt.event.MouseEvent.BUTTON3;

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public class InputManager extends MouseAdapter implements KeyListener {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final Engine engine;

    private final boolean[] keys;
    private final boolean[] buttons;

    private Point mouse = new Point(-1, -1);

    // <- Static ->

    // <- Constructor ->
    public InputManager(final Engine engine) {
        this.engine = engine;

        keys = new boolean[65_535];

        buttons = new boolean[6];
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void mouseReleased(final MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        mouse.setLocation(e.getX() / engine.getDisplayScaleFactor(), engine.getScreenHeight() - (e.getY() / engine.getDisplayScaleFactor()));
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        keys[e.getKeyCode()] = false;

        engine.keyPressed(e);
    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

    // <- Getter & Setter ->
    public boolean isLeftMouseButtonDown() {
        return isMouseButtonDown(BUTTON1);
    }

    public boolean isRightMouseButtonDOwn() {
        return isMouseButtonDown(BUTTON3);
    }

    public boolean isMiddleMouseButtonDOwn() {
        return isMouseButtonDown(BUTTON2);
    }

    public boolean isMouseButtonDown(final int button) {
        return buttons[button];
    }

    public boolean isKeyUpDown() {
        return isKeyDown(VK_UP) || isKeyDown(VK_W);
    }

    public boolean isKeyDownDown() {
        return isKeyDown(VK_DOWN) || isKeyDown(VK_S);
    }

    public boolean isKeyLeftDown() {
        return isKeyDown(VK_LEFT) || isKeyDown(VK_A);
    }

    public boolean isKeyRightDown() {
        return isKeyDown(VK_RIGHT) || isKeyDown(VK_D);
    }

    public boolean isKeyDown(final int key) {
        return keys[key];
    }

    public Point getMousePosition() {
        return mouse;
    }

    // <- Static ->
}

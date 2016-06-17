package de.janik.softengine.entity;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.InputManager;
import de.janik.softengine.UI_Event;
import de.janik.softengine.UI_KeyEvent;
import de.janik.softengine.math.Vector;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public abstract class Entity implements Comparable<Entity> {
    // <- Public ->

    // <- Protected ->
    protected Vector location;

    protected final List<UI_Event> mousePressEvents;
    protected final List<UI_Event> mouseOverEvents;
    protected final List<UI_Event> mouseExitEvents;

    protected final List<UI_KeyEvent> keyPressEvents;

    protected int width;
    protected int height;

    // <- Private->
    private boolean mouseOver = false;

    // <- Static ->

    // <- Constructor ->
    public Entity(final int x, final int y) {
        location = new Vector(x, y, 0);

        mousePressEvents = new ArrayList<>(1);
        mouseOverEvents = new ArrayList<>(1);
        mouseExitEvents = new ArrayList<>(1);
        keyPressEvents = new ArrayList<>(1);
    }

    // <- Abstract ->
    public abstract void tick(final long ticks, final InputManager input);

    // <- Object ->
    public void move(final Direction direction, final int units) {
        switch (direction) {
            case LEFT: {
                location.x -= units;

                break;
            }
            case RIGHT: {
                location.x += units;

                break;
            }
            case UP: {
                location.y -= units;

                break;
            }
            case DOWN: {
                location.y += units;

                break;
            }
        }
    }

    @Override
    public int compareTo(final Entity other) {
        final int z = getZ();
        final int otherZ = other.getZ();

        return otherZ < z ? 1 : (otherZ > z ? -1 : 0);
    }

    public boolean handleMousePress(final InputManager input) {
        if (input.isLeftMouseButtonDown()) {
            final Point mousePos = input.getMousePosition();

            final int x = (int) mousePos.getX();
            final int y = (int) mousePos.getY();

            if ((x >= getX() && x <= getX() + getWidth()) && (y >= getY() && y <= getY() + getHeight())) {
                pressMouse();

                return true;
            }
        }

        return false;
    }

    public void handleMouseOver(final InputManager input) {
        final Point mousePos = input.getMousePosition();

        final int x = (int) mousePos.getX();
        final int y = (int) mousePos.getY();

        final boolean oldValue = mouseOver;

        mouseOver = (x >= getX() && x <= getX() + getWidth()) && (y >= getY() && y <= getY() + getHeight());

        if (!oldValue && mouseOver)
            mouseOver();

        if (oldValue && !mouseOver)
            mouseExit();
    }

    public void pressMouse() {
        for (final UI_Event e : mousePressEvents)
            e.onUI_Event();
    }

    public void mouseExit() {
        for (final UI_Event e : mouseExitEvents)
            e.onUI_Event();
    }

    public void mouseOver() {
        for (final UI_Event e : mouseOverEvents)
            e.onUI_Event();
    }

    public void pressKey(final int keyCode) {
        for (final UI_KeyEvent e : keyPressEvents)
            e.onUI_Event(keyCode);
    }

    public void onMousePress(final UI_Event e) {
        mousePressEvents.add(e);
    }

    public void onMouseOver(final UI_Event e) {
        mouseOverEvents.add(e);
    }

    public void onMouseExit(final UI_Event e) {
        mouseExitEvents.add(e);
    }

    public void onKeyPress(final UI_KeyEvent e) {
        keyPressEvents.add(e);
    }

    // <- Getter & Setter ->
    public void setLocation(final float x, final float y) {
        location.x = x;
        location.y = y;
    }

    public int getX() {
        return (int) location.x;
    }

    public void setX(final float x) {
        location.x = x;
    }

    public int getY() {
        return (int) location.y;
    }

    public int getZ() {
        return (int) location.z;
    }

    public void setY(final float y) {
        location.y = y;
    }

    public void setZ(final int z) {
        location.z = z;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    // <- Static ->
}

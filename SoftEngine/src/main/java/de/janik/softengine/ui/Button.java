package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->


import de.janik.softengine.InputManager;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.util.ColorARGB;

import java.awt.Font;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Button extends DrawableEntity {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final int DEFAULT_BUTTON_MOUSE_PRESS_COOLDOWN;

    private final Text text;

    private int buttonPressTimer;

    private boolean mousePressed = false;
    private boolean mouseReleased = true;

    // <- Static ->
    static {
        DEFAULT_BUTTON_MOUSE_PRESS_COOLDOWN = 2/*1/10s @20tps*/;
    }

    // <- Constructor ->
    public Button() {
        this(null);
    }

    public Button(final String text) {
        this(0, 0);

        if (text != null)
            setText(text);
    }

    public Button(int x, int y) {
        super(x, y);

        text = new Text();

        setSprite(text);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void tick(final long ticks, final InputManager input) {
        if (mousePressed && buttonPressTimer > 0)
            buttonPressTimer--;

        if (!input.isLeftMouseButtonDown())
            mouseReleased = true;
    }

    @Override
    public void pressMouse() {
        if (!mousePressed && buttonPressTimer == 0) {
            mousePressed = true;

            buttonPressTimer = DEFAULT_BUTTON_MOUSE_PRESS_COOLDOWN;

            if (mouseReleased) {
                super.pressMouse();

                mouseReleased = false;
            }
        }

        if (mousePressed && buttonPressTimer == 0)
            mousePressed = false;
    }

    // <- Getter & Setter ->
    public void setFont(final Font font) {
        text.setFont(font);
    }

    public void setTextSize(final int textSize) {
        text.setTextSize(textSize);
    }

    public void setText(final String text) {
        this.text.setText(text);
    }

    public void setTextColor(final ColorARGB color) {
        text.setColor(color);
    }

    public void setAntialiasing(final boolean antialiasing) {
        text.setAntialiasing(antialiasing);
    }

    public void setAntialiasing(final boolean antialiasing, final Text.Interpolation interpolation) {
        text.setAntialiasing(antialiasing, interpolation);
    }

    public Text getText() {
        return text;
    }

    // <- Static ->
}

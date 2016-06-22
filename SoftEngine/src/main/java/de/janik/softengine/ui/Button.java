package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.InputManager;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public class Button extends TextComponent {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final int DEFAULT_MOUSE_PRESS_COOLDOWN;

    private int buttonPressTimer;

    private boolean mousePressed = false;
    private boolean mouseReleased = true;

    // <- Static ->
    static {
        DEFAULT_MOUSE_PRESS_COOLDOWN = 2/*1/10s @20tps*/;
    }

    // <- Constructor ->
    public Button(final String text, final TextLocation textLocation) {
        this(text);

        setTextLocation(textLocation);
    }

    public Button(final String text) {
        this();

        if (text != null)
            setText(text);
    }

    public Button() {
        super();
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void tick(final long ticks, final InputManager input) {
        if (mousePressed && buttonPressTimer > 0)
            buttonPressTimer--;

        if (!input.isLeftMouseButtonDown())
            mouseReleased = true;

        super.tick(ticks, input);
    }

    @Override
    public void pressMouse() {
        if (!mousePressed && buttonPressTimer == 0) {
            mousePressed = true;

            buttonPressTimer = DEFAULT_MOUSE_PRESS_COOLDOWN;

            if (mouseReleased) {
                super.pressMouse();

                mouseReleased = false;
            }
        }

        if (mousePressed && buttonPressTimer == 0)
            mousePressed = false;
    }

    // <- Getter & Setter ->


    // <- Static ->
}

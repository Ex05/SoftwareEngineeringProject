package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.InputManager;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.math.Vector;
import de.janik.softengine.util.ColorARGB;

import java.awt.Font;

import static de.janik.softengine.ui.Button.TextLocation.ABSOLUTE;
import static de.janik.softengine.ui.Button.TextLocation.RIGHT;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Button extends DrawableEntity {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final int DEFAULT_MOUSE_PRESS_COOLDOWN;

    private final Rectangle background;

    private final Text text;

    private final Vector textPosition;

    private int buttonPressTimer;

    private boolean mousePressed = false;
    private boolean mouseReleased = true;

    private TextLocation textLocation = RIGHT;

    // <- Static ->
    static {
        DEFAULT_MOUSE_PRESS_COOLDOWN = 2/*1/10s @20tps*/;
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

    public Button(final String text, final TextLocation textLocation) {
        this(text);

        setTextLocation(textLocation);
    }

    public Button(int x, int y) {
        super(x, y);

        text = new Text();

        textPosition = new Vector();

        background = new Rectangle(0, 0);

        setSprite(background.getSprite());
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void tick(final long ticks, final InputManager input) {
        if (mousePressed && buttonPressTimer > 0)
            buttonPressTimer--;

        if (!input.isLeftMouseButtonDown())
            mouseReleased = true;

        switch (textLocation) {
            case LEFT:
                textPosition.setX(background.getWidth() - text.getWidth()).setY(background.getHeight() / 2 - text.getHeight() / 2);
                break;
            case RIGHT:
                textPosition.setX(0).setY(background.getHeight() / 2 - text.getHeight() / 2);
                break;
            case CENTER:
                textPosition.setX(background.getWidth() / 2 - text.getWidth() / 2).setY(background.getHeight() / 2 - text.getHeight() / 2);
                break;
            case ABSOLUTE:
                break;
        }

        getSprite().draw((int) textPosition.getX(), (int) textPosition.getY(), text);
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

    private void update() {
        if (background.getWidth() < text.getWidth())
            background.resize(text.getWidth(), background.getHeight());

        if (background.getHeight() < text.getHeight())
            background.resize(background.getWidth(), text.getHeight());

        setSprite(background.getSprite());
    }

    // <- Getter & Setter ->
    @Override
    public void setWidth(final int width) {
        super.setWidth(width);

        if (width != background.getWidth())
            background.resize(width, background.getHeight());

        update();
    }

    @Override
    public void setHeight(final int height) {
        super.setHeight(height);

        if (height != background.getHeight())
            background.resize(background.getWidth(), height);

        update();
    }

    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;

        if (width != background.getWidth() || height != background.getHeight())
            background.resize(width, height);

        update();
    }

    public void setFont(final Font font) {
        text.setFont(font);

        update();
    }

    public void setTextSize(final int textSize) {
        text.setTextSize(textSize);

        update();
    }

    public void setText(final String text) {
        this.text.setText(text);

        update();
    }

    public void setTextColor(final ColorARGB color) {
        text.setColor(color);

        update();
    }

    public void setBackgroundColor(final ColorARGB color) {
        background.setColor(color);
    }

    public void setAntialiasing(final boolean antialiasing) {
        text.setAntialiasing(antialiasing);

        update();
    }

    public void setAntialiasing(final boolean antialiasing, final Text.Interpolation interpolation) {
        text.setAntialiasing(antialiasing, interpolation);

        update();
    }

    public Text getText() {
        return text;
    }

    @Override
    public Bitmap getSprite() {
        return (Bitmap) super.getSprite();
    }

    public void setTextLocation(final int x, final int y) {
        textPosition.setX(x).setY(y);

        setTextLocation(ABSOLUTE);
    }

    public void setTextLocation(final TextLocation textLocation) {
        this.textLocation = textLocation;
    }

    // <- Static ->
    public enum TextLocation {
        LEFT,
        RIGHT,
        CENTER,
        ABSOLUTE
    }
}

package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.InputManager;
import de.janik.softengine.math.Vector;
import de.janik.softengine.util.ColorARGB;

import java.awt.Font;

import static de.janik.softengine.ui.TextLocation.ABSOLUTE;
import static de.janik.softengine.ui.TextLocation.CENTER;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public abstract class TextComponent extends UI_Component{
    // <- Public ->

    // <- Protected ->
    protected final Text text;

    protected TextLocation textLocation = CENTER;

    protected final Vector textPosition;

    // <- Private->
    // <- Static ->

    // <- Constructor ->
    public TextComponent() {
        text = new Text();

        textPosition = new Vector();
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void tick(final long ticks, final InputManager input) {
        switch (textLocation) {
            case LEFT:
                textPosition.setX(0).setY(background.getHeight() / 2 - text.getHeight() / 2);

                break;
            case RIGHT:
                textPosition.setX(background.getWidth() - text.getWidth()).setY(background.getHeight() / 2 - text.getHeight() / 2);

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
    protected void update() {
        if (background.getWidth() < text.getWidth())
            background.setSize(text.getWidth(), background.getHeight());

        if (background.getHeight() < text.getHeight())
            background.setSize(background.getWidth(), text.getHeight());

        background.fill();

        setSprite(background.getSprite());
    }


    // <- Getter & Setter ->
    public void setTextLocation(final int x, final int y) {
        textPosition.setX(x).setY(y);

        setTextLocation(ABSOLUTE);
    }

    public void setTextLocation(final TextLocation textLocation) {
        this.textLocation = textLocation;
    }

    @Override
    public void setWidth(final int width) {
        super.setWidth(width);

        if (width != background.getWidth())
            background.setSize(width, background.getHeight());

        update();
    }

    @Override
    public void setHeight(final int height) {
        super.setHeight(height);

        if (height != background.getHeight())
            background.setSize(background.getWidth(), height);

        update();
    }

    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;

        if (width != background.getWidth() || height != background.getHeight())
            background.setSize(width, height);

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

    // <- Static ->
}

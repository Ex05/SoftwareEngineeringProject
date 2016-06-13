package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->


import de.janik.softengine.InputManager;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.util.ColorARGB;

import java.awt.Font;

import static de.janik.softengine.ui.Text.Interpolation;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class TextField extends DrawableEntity {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final Text text;

    // <- Static ->

    // <- Constructor ->
    public TextField(final String text) {
        this(0, 0);

        setText(text);
    }

    public TextField(int x, int y) {
        super(x, y);

        text = new Text();

        setSprite(text);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void tick(final long ticks, final InputManager input) {
        // Unused
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

    public void setAntialiasing(final boolean antialiasing, final Interpolation interpolation) {
        text.setAntialiasing(antialiasing, interpolation);
    }

    // <- Static ->
}

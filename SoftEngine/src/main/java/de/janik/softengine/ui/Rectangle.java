package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.InputManager;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.util.ColorARGB;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Rectangle extends DrawableEntity {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final int[] pixels;

    private final Bitmap background;

    // <- Static ->

    // <- Constructor ->
    public Rectangle(final int width, final int height) {
        super(0, 0);

        pixels = new int[width * height];

        background = new Bitmap(width, height, pixels);

        setSprite(background);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void tick(final long ticks, final InputManager input) {

    }

    // <- Getter & Setter ->
    public void setColor(final ColorARGB color) {
        background.fill(color);

        /* background.getGraphics().setColor(ColorARGB.ToAWT_Color(color));
        background.getGraphics().fillRect(0,0,background.getWidth(), background.getHeight()); */
    }

    // <- Static ->
}

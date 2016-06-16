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
    private int[] pixels;

    private Bitmap background;

    private ColorARGB color = new ColorARGB(0, 0, 0, 0);

    // <- Static ->

    // <- Constructor ->
    public Rectangle(final int width, final int height) {
        super(0, 0);

        resize(width, height);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void tick(final long ticks, final InputManager input) {

    }

    public void resize(final int width, final int height) {
        pixels = new int[width * height];

        background = new Bitmap(width, height, pixels);

        setSprite(background);

        fill(color);
    }

    // <- Getter & Setter ->
    public void setColor(final ColorARGB color) {
        this.color = color;

        fill(color);

        /* background.getGraphics().setColor(ColorARGB.ToAWT_Color(color));
        background.getGraphics().fillRect(0,0,background.getWidth(), background.getHeight()); */
    }

    private void fill(final ColorARGB color) {
        background.fill(color);
    }

    @Override
    public Bitmap getSprite() {
        return (Bitmap) super.getSprite();
    }

    // <- Static ->
}

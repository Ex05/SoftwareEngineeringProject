package de.janik.softengine;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.ui.A_Sprite;
import de.janik.softengine.ui.Bitmap;
import de.janik.softengine.util.ColorARGB;

import java.util.List;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Screen extends A_Sprite {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final int[] pixel;

    private final Bitmap bitmap;

    // <- Static ->

    // <- Constructor ->
    public Screen(final Canvas canvas) {
        final int width = canvas.getWidth();
        final int height = canvas.getHeight();

        pixel = new int[width * height];

        bitmap = new Bitmap(width, height, pixel);
    }

    // <- Abstract ->

    // <- Object ->
    public void fillScreen(final ColorARGB color) {
        bitmap.fill(color);
    }

    public void render(final List<DrawableEntity> entities) {
        for (final DrawableEntity e : entities)
            if (e.isVisible())
                draw(e.getX(), getHeight() - e.getSprite().getHeight() - e.getY(), e.getSprite());
    }

    public void draw(final int xOffset, final int yOffset, final A_Sprite sprite) {
        bitmap.draw(xOffset, yOffset, sprite);
    }

    // <- Getter & Setter ->
    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int[] getPixel() {
        return pixel;
    }

    // <- Static ->
}

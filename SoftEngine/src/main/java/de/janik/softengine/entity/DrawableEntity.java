package de.janik.softengine.entity;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.InputManager;
import de.janik.softengine.ui.A_Sprite;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public abstract class DrawableEntity extends Entity implements Drawable {
    // <- Public ->

    // <- Protected ->
    protected A_Sprite sprite;

    // <- Private->
    private boolean visible = true;

    // <- Static ->

    // <- Constructor ->
    protected DrawableEntity(final A_Sprite sprite) {
        this(0, 0, sprite);
    }

    protected DrawableEntity(final int x, final int y) {
        super(x, y);
    }

    public DrawableEntity(final int x, final int y, final A_Sprite sprite) {
        super(x, y);

        this.sprite = sprite;
    }

    // <- Abstract ->
    public abstract void tick(final long ticks, final InputManager input);

    // <- Object ->

    @Override
    public void pressKey(final int keyCode) {
        if (isVisible())
            super.pressKey(keyCode);
    }

    // <- Getter & Setter ->
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    public A_Sprite getSprite() {
        return sprite;
    }

    public void setSprite(final A_Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public int getWidth() {
        return sprite.getWidth();
    }

    @Override
    public int getHeight() {
        return sprite.getHeight();
    }

    // <- Static ->
}

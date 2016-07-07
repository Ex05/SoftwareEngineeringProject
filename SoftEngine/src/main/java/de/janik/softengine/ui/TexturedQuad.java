package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.InputManager;
import de.janik.softengine.entity.DrawableEntity;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class TexturedQuad extends DrawableEntity {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    public TexturedQuad(final A_Sprite sprite) {
        super(sprite);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void tick(final long ticks, final InputManager input) {

    }

    // <- Getter & Setter ->
    // <- Static ->
}

package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.util.ColorARGB;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class DropShadow {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final ColorARGB colorARGB;
    private final int xOffset;
    private final int yOffset;

    // <- Static ->

    // <- Constructor ->
    public DropShadow(final ColorARGB colorARGB, final int xOffset, final int yOffset) {

        this.colorARGB = colorARGB;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public ColorARGB getColor() {
        return colorARGB;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    // <- Static ->
}

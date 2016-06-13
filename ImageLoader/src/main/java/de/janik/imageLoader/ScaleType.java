package de.janik.imageLoader;
// <- Import ->

// <- Static_Import ->

import java.awt.image.BufferedImage;

/**
 * Wrapper class for {@link BufferedImage Image} scaling method constants.
 *
 * @author Jan.Marcel.Janik [©2016]
 * @see BufferedImage
 */
public enum ScaleType {
    // <- Public ->
    /**
     * @see BufferedImage#SCALE_AREA_AVERAGING
     */
    AREA_AVERAGING(BufferedImage.SCALE_AREA_AVERAGING),
    /**
     * @see BufferedImage#SCALE_SMOOTH
     */
    SMOOTH(BufferedImage.SCALE_SMOOTH),
    /**
     * @see BufferedImage#SCALE_DEFAULT
     */
    DEFAULT(BufferedImage.SCALE_DEFAULT),
    /**
     * @see BufferedImage#SCALE_FAST
     */
    FAST(BufferedImage.SCALE_FAST),
    /**
     * @see BufferedImage#SCALE_REPLICATE
     */
    REPLICATE(BufferedImage.SCALE_REPLICATE);

    // <- Protected ->

    // <- Private->
    private final int type;

    // <- Static ->

    // <- Constructor ->
    ScaleType(final int type) {
        this.type = type;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public int getType() {
        return type;
    }

    // <- Static ->
}

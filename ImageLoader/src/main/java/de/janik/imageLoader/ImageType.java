package de.janik.imageLoader;
// <- Import ->

// <- Static_Import ->

import java.awt.image.BufferedImage;

/**
 * Wrapper class for {@link BufferedImage Image} image types.
 *
 * @author Jan.Marcel.Janik [©2016]
 * @see BufferedImage
 */
public enum ImageType {
    // <- Public ->
    /**
     * @see BufferedImage#TYPE_3BYTE_BGR
     */
    TYPE_3BYTE_BGR(BufferedImage.TYPE_3BYTE_BGR),
    /**
     * @see BufferedImage#TYPE_4BYTE_ABGR
     */
    TYPE_4BYTE_ABGR(BufferedImage.TYPE_4BYTE_ABGR),
    /**
     * @see BufferedImage#TYPE_4BYTE_ABGR_PRE
     */
    TYPE_4BYTE_ABGR_PRE(BufferedImage.TYPE_4BYTE_ABGR_PRE),
    /**
     * @see BufferedImage#TYPE_BYTE_BINARY
     */
    TYPE_BYTE_BINARY(BufferedImage.TYPE_BYTE_BINARY),
    /**
     * @see BufferedImage#TYPE_BYTE_GRAY
     */
    TYPE_BYTE_GRAY(BufferedImage.TYPE_BYTE_GRAY),
    /**
     * @see BufferedImage#TYPE_BYTE_INDEXED
     */
    TYPE_BYTE_INDEXED(BufferedImage.TYPE_BYTE_INDEXED),
    /**
     * @see BufferedImage#TYPE_CUSTOM
     */
    TYPE_CUSTOM(BufferedImage.TYPE_CUSTOM),
    /**
     * @see BufferedImage#TYPE_INT_ARGB
     */
    TYPE_INT_ARGB(BufferedImage.TYPE_INT_ARGB),
    /**
     * @see BufferedImage#TYPE_INT_ARGB_PRE
     */
    TYPE_INT_ARGB_PRE(BufferedImage.TYPE_INT_ARGB_PRE),
    /**
     * @see BufferedImage#TYPE_INT_BGR
     */
    TYPE_INT_BGR(BufferedImage.TYPE_INT_BGR),
    /**
     * @see BufferedImage#TYPE_INT_RGB
     */
    TYPE_INT_RGB(BufferedImage.TYPE_INT_RGB),
    /**
     * @see BufferedImage#TYPE_USHORT_555_RGB
     */
    TYPE_USHORT_555_RGB(BufferedImage.TYPE_USHORT_555_RGB),
    /**
     * @see BufferedImage#TYPE_USHORT_565_RGB
     */
    TYPE_USHORT_565_RGB(BufferedImage.TYPE_USHORT_565_RGB),
    /**
     * @see BufferedImage#TYPE_USHORT_GRAY
     */
    TYPE_USHORT_GRAY(BufferedImage.TYPE_USHORT_GRAY);

    // <- Protected ->

    // <- Private->
    private final int type;

    // <- Static ->

    // <- Constructor ->
    ImageType(final int type) {
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
package de.janik.softengine.ui;
// <- Import ->
// <- Static_Import ->

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

import static java.awt.image.DataBuffer.TYPE_INT;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Bitmap extends A_Sprite {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final int[] BIT_MASKS;

    private final int[] pixels;

    private final int width;
    private final int height;

    private final BufferedImage img;

    private final Graphics2D g;

    // <- Static ->
    static {
        BIT_MASKS = new int[]{0x00_FF_00_00, 0x00_00_FF_00, 0x00_00_00_00_FF, 0xFF_00_00_00};
    }

    // <- Constructor ->
    public Bitmap(final int width, final int height, final int[] pixels) {
        this.width = width;

        this.height = height;
        this.pixels = pixels;

        final SinglePixelPackedSampleModel sm = new SinglePixelPackedSampleModel(TYPE_INT, width, height, BIT_MASKS);

        final DataBufferInt db = new DataBufferInt(pixels, pixels.length);

        final WritableRaster raster = Raster.createWritableRaster(sm, db, null);

        img = new BufferedImage(ColorModel.getRGBdefault(), raster, false, new Hashtable<>());

        g = (Graphics2D) img.getGraphics();
    }

    // <- Abstract ->

    // <- Object ->
    public void draw(final int xOffset, final int yOffset, final A_Sprite sprite) {
        final int[] srcPixel = sprite.getPixel();

        int i = 0;
        for (int y = yOffset; y < yOffset + sprite.getHeight(); y++)
            for (int x = xOffset; x < xOffset + sprite.getWidth(); x++) {
                if ((x < 0 || x >= width) || y < 0 || y >= height) {
                    i++;

                    continue;
                }

                final int BIT_MASK = 0x00_00_00_FF;

                final int srcRGBA = srcPixel[i++];
                final int alpha = (srcRGBA >> 24) & BIT_MASK;

                final int dstPos = x + (y * width);

                if (alpha == 255)
                    pixels[dstPos] = srcRGBA;
                else if (alpha != 0) {
                    final int srcRed = (srcRGBA >> 24) & BIT_MASK;
                    final int srcGreen = (srcRGBA >> 16) & BIT_MASK;
                    final int srcBlue = (srcRGBA >> 8) & BIT_MASK;
                    final float srcAlpha = (float )alpha / 255.0f;

                    final int dstRGB = pixels[dstPos];

                    final int dstRed = (dstRGB >> 24) & BIT_MASK;
                    final int dstGreen = (dstRGB >> 16) & BIT_MASK;
                    final int dstBlue = (dstRGB >> 8) & BIT_MASK;
                    final int dstAlpha = dstRGB & BIT_MASK;

                    // TODO: Check if we need to range check these values.
                    // TODO: Implement premultiplied alpha blending.
                    final int blendRed = (int) ((srcAlpha * srcRed) + ((1 - srcAlpha) * dstRed));
                    final int blendGreen = (int) ((srcAlpha * srcGreen) + ((1 - srcAlpha) * dstGreen));
                    final int blendBlue = (int) ((srcAlpha * srcBlue) + ((1 - srcAlpha) * dstBlue));

                    pixels[dstPos] = (blendRed << 24) | (blendGreen << 16) | (blendBlue << 8) | dstAlpha;
                }
            }
    }

    // <- Getter & Setter ->
    public Graphics2D getGraphics() {
        return g;
    }

    public BufferedImage getImage() {
        return img;
    }

    @Override
    public int[] getPixel() {
        return pixels;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    // <- Static ->
}

package de.janik.softengine.util;
// <- Import ->

// <- Static_Import ->

import de.janik.imageLoader.ImageLoader;
import de.janik.softengine.ui.Animation;
import de.janik.softengine.ui.AnimationPreset;
import de.janik.softengine.ui.AnimationPreset.Preset;
import de.janik.softengine.ui.Bitmap;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;

import static de.janik.imageLoader.ImageType.TYPE_INT_ARGB;
import static de.janik.softengine.util.Constants.ANIMATION_FILE_FILE_EXTENSION;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class SpriteLoader {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final SpriteLoader LOADER;

    // <- Static ->
    static {
        LOADER = new SpriteLoader();
    }

    // <- Constructor ->
    // <- Abstract ->

    // <- Object ->1
    public Animation[] load(final AnimationPreset animationPreset, final String path) {
        final Preset[] presets = animationPreset.getPreset();

        final Animation[] animations = new Animation[presets.length];

        for (int i = 0; i < presets.length; i++) {
            final Preset preset = presets[i];

            final BufferedImage img = ImageLoader.GetInstance().setInputFile(path + preset.getName() + ANIMATION_FILE_FILE_EXTENSION).type(TYPE_INT_ARGB).load().asImage().get();

            final int[] srcPixel = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

            final int numImages = img.getWidth() / preset.getWidth();

            final Bitmap[] bitmaps = new Bitmap[numImages];

            final int width = preset.getWidth();
            final int height = img.getHeight();

            for (int j = 0; j < numImages; j++) {
                final int[] pixel = new int[width * height];

                for (int k = 0; k < height; k++)
                    System.arraycopy(srcPixel, j * width + k * img.getWidth(), pixel, k * width, width);

                bitmaps[j] = new Bitmap(width, height, pixel);
            }

            animations[i] = new Animation(preset.getName(), bitmaps);
        }

        return animations;
    }

    // <- Getter & Setter ->

    // <- Static ->
    public static SpriteLoader GetInstance() {
        return LOADER;
    }

    public Animation[] load(final String file) {
        final Animation[] animations = new Animation[1];

        final BufferedImage img = ImageLoader.GetInstance().setInputFile(file).type(TYPE_INT_ARGB).load().asImage().get();

        final int[] srcPixel = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

        final Bitmap[] bitmaps = new Bitmap[1];

        final int width = img.getWidth();
        final int height = img.getHeight();

        final int[] pixel = new int[width * height];

        for (int i = 0; i < height; i++)
            System.arraycopy(srcPixel, i * img.getWidth(), pixel, i * width, width);

        bitmaps[0] = new Bitmap(width, height, pixel);

        animations[0] = new Animation(new File(file).getName(), bitmaps);

        return animations;
    }
}

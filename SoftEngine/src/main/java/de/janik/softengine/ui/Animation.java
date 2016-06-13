package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import static de.janik.softengine.util.Util.*;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Animation extends A_Sprite {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final String name;

    private final Bitmap[] bitmaps;

    private int animationIndex = 0;

    // <- Static ->

    // <- Constructor ->
    public Animation(final String name, final Bitmap[] bitmaps) {
        this.name = name;
        this.bitmaps = bitmaps;
    }

    // <- Abstract ->

    // <- Object ->
    public void tickAnimation() {
        animationIndex++;

        if (animationIndex > bitmaps.length - 1)
            animationIndex = 0;
    }

    public void resetAnimation() {
        animationIndex = 0;
    }

    @Override
    public String toString() {
        return Format("%s [Name:%s, %d/%d]", getClass().getSimpleName(), name, animationIndex, bitmaps.length);
    }

    // <- Getter & Setter ->
    public String getName() {
        return name;
    }

    public final int[] getPixel() {
        return bitmaps[animationIndex].getPixel();
    }

    public final int[] getAnimationStep(final int step) {
        return bitmaps[step].getPixel();
    }

    public void setAnimationStep(final int step) {
        animationIndex = step - 1;

        if(animationIndex > bitmaps.length - 1)
            animationIndex = bitmaps.length - 1;
    }

    @Override
    public int getWidth() {
        return bitmaps[animationIndex].getWidth();
    }

    @Override
    public int getHeight() {
        return bitmaps[animationIndex].getHeight();
    }

    // <- Static ->
}

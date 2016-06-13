package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.util.SpriteLoader;

import static de.janik.softengine.util.Util.Format;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Sprite extends A_Sprite {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final Animation[] animations;

    private Animation current;
    // <- Static ->

    // <- Constructor ->
    public Sprite(final AnimationPreset preset, final String path) {
        animations = SpriteLoader.GetInstance().load(preset, path);

        current = animations[0];
    }

    // <- Abstract ->

    // <- Object ->
    public void play(final String name) {
        for (final Animation animation : animations)
            if (animation.getName().equals(name)) {
                current = animation;
                // TODO: Decide if resetting the animation here is a good thing or not.
                // current.resetAnimation();

                return;
            }

        throw new RuntimeException(Format("ERROR: Failed to find animation [%s].", name));
    }

    public void tickAnimation() {
        current.tickAnimation();
    }

    public void resetAnimation() {
        current.resetAnimation();
    }

    @Override
    public String toString() {
        return Format("%s [Current:%s, Total:%d]", getClass().getSimpleName(), current, animations.length);
    }

    // <- Getter & Setter ->
    public void setAnimationStep(final int step) {
        current.setAnimationStep(step);
    }

    @Override
    public final int[] getPixel() {
        return current.getPixel();
    }

    @Override
    public int getWidth() {
        return current.getWidth();
    }

    @Override
    public int getHeight() {
        return current.getHeight();
    }

    // <- Static ->
}

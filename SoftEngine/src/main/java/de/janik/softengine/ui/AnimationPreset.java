package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class AnimationPreset {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final List<Preset> presets;

    // <- Static ->

    // <- Constructor ->
    public AnimationPreset() {
        presets = new LinkedList<>();
    }

    // <- Abstract ->

    // <- Object ->
    public void add(final String animationName, final int width) {
        presets.add(new Preset(animationName, width));
    }

    // <- Getter & Setter ->
    public Preset[] getPreset() {
        // TODO: Decide which method is faster.
        return presets.toArray(new Preset[presets.size()]);

        // return presets.stream().toArray(AnimationPreset.Preset[]::new);
    }

    // <- Static ->
    public static final class Preset {
        private final String name;

        private final int width;

        public Preset(final String name, final int width) {
            this.name = name;
            this.width = width;
        }

        public String getName() {
            return name;
        }

        public int getWidth() {
            return width;
        }
    }
}

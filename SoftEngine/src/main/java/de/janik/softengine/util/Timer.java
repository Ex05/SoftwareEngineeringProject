package de.janik.softengine.util;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Timer {
    // <- Public ->
    public static final long MILLIS_PER_SECOND;

    public static final long NANOS_PER_SECOND;

    // <- Protected ->
    // <- Private->

    // <- Static ->
    static {
        MILLIS_PER_SECOND = 1_000;

        NANOS_PER_SECOND = 1_000_000_000;
    }

    // <- Constructor ->
    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->

    // <- Static ->
    public static long GetTime() {
        return System.nanoTime();
    }

    public static long GetLowResolutionTime() {
        return System.currentTimeMillis();
    }
}

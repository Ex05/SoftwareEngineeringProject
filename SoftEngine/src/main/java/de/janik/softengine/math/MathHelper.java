package de.janik.softengine.math;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public class MathHelper {
    // <- Public ->
    // <- Protected ->
    // <- Private->

    // <- Static ->

    // <- Constructor ->
    private MathHelper() {
        throw new IllegalStateException("Do not instantiate !~!");
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->

    // <- Static ->
    public static double Pow2(final double d) {
        // return Math.pow(f, 2);
        return d * d;
    }

    public static float Pow2(final float f) {
        return f * f;
    }

    public static int Pow2(final int i) {
        return i * i;
    }

    public static long Pow2(long l) {
        return l * l;
    }

    public static int Get2Fold(final int fold) {
        int ret = 2;
        while (ret < fold)
            ret <<= 1;

        return ret;
    }

    public static float Aspect(final int width, final int height) {
        return Aspect((float) width, (float) height);
    }

    public static float Aspect(final float width, final float height) {
        return (width >= height) ? (width / height) : (height / width);
    }

    public static float Tangent(final float angle) {
        return (float) Math.tan(angle);
    }

    public static float CoTangent(final float angle) {
        return (float) (1f / Math.tan(angle));
    }

    public static float DegreesToRadians(final float angle) {
        return angle * (float) (Math.PI / 180d);
    }

    public static float Clip(final float xOffset, final float a, final float b) {
        return xOffset < a ? a : (xOffset < b ? xOffset : b);
    }
}

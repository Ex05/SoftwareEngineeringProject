package de.janik.softengine.math;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public class Vector {
    // <- Public ->
    public static final int ELEMENTS = 3;

    public float x, y, z;

    // <- Protected ->
    // <- Private->

    // <- Static ->

    // <- Constructor ->
    public Vector() {
        this(0, 0, 0);
    }

    public Vector(final Vector v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public Vector(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // <- Abstract ->

    // <- Object ->
    public float length() {
        return (float) Math.sqrt((MathHelper.Pow2(x) + MathHelper.Pow2(y) + MathHelper.Pow2(z)));
    }

    public void add(final Vector v) {
        this.x += v.getX();
        this.y += v.getY();
        this.z += v.getZ();
    }

    public void add(final float scalar) {
        add(new Vector(scalar, scalar, scalar));
    }

    public void subtract(final Vector v) {
        this.x -= v.getX();
        this.y -= v.getY();
        this.z -= v.getZ();
    }

    public void subtract(final float scalar) {
        subtract(new Vector(scalar, scalar, scalar));
    }

    public void multiply(final Vector v) {
        this.x *= v.getX();
        this.y *= v.getY();
        this.z *= v.getZ();
    }

    public Vector multiply(final float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;

        return this;
    }

    public void divide(final Vector v) {
        this.x /= v.getX();
        this.y /= v.getY();
        this.z /= v.getZ();
    }

    public void divide(float scalar) {
        divide(new Vector(scalar, scalar, scalar));
    }

    public Vector normalVector(final Vector v) {
        return Normal(this, v);
    }

    public Vector normalize() {
        float length = length();

        this.x = x / length;
        this.y = y / length;
        this.z = z / length;

        return this;
    }

    @Override
    public String toString() {
        return "Vector [x=" + x + ", y=" + y + ", z=" + z + "]";
    }

    // <- Getter & Setter ->
    public float getX() {
        return x;
    }

    public Vector setX(final float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Vector setY(final float y) {
        this.y = y;
        return this;
    }

    public float getZ() {
        return z;
    }

    public Vector setZ(final float z) {
        this.z = z;
        return this;
    }

    // <- Static ->
    public static float Scalar(final Vector v) {
        return v.length();
    }

    public static Vector Normal(final Vector v1, final Vector v2) {
        return new Vector((v1.getY() * v2.getZ()) - (v2.getY() * v1.getZ()), (v1.getZ() * v2.getX()) - (v2.getZ() * v1.getX()), (v1.getX() * v2.getY()) - (v2.getX() * v1.getY()));
    }

    public static Vector Multiply(final Vector v1, final Vector v2) {
        final Vector v = new Vector(v1);
        v.multiply(v2);

        return v;
    }

    public static Vector Divide(final Vector v1, final Vector v2) {
        final Vector v = new Vector(v1);
        v.divide(v2);

        return v;
    }

    public static Vector Add(final Vector v1, final Vector v2) {
        final Vector v = new Vector(v1);
        v.add(v2);

        return v;
    }

    public static Vector Subtract(final Vector v1, final Vector v2) {
        final Vector v = new Vector(v1);
        v.subtract(v2);

        return v;
    }

    public static float Distance(final Vector a, final Vector b) {
        return Subtract(b, a).length();
    }

    public static Vector Normalize(final Vector v) {
        return v.normalize();
    }
}

package de.janik.softengine.util;
// <- Import ->

// <- Static_Import ->

import java.text.StringCharacterIterator;
import java.util.Random;

import static de.janik.softengine.util.Constants.PROPERTY_FILE_FILE_EXTENSION;
import static de.janik.softengine.util.Constants.PROPERTY_FILE_LOCATION;
import static java.io.File.separator;
import static java.lang.Float.*;
import static java.lang.String.*;
import static java.util.Locale.US;


/**
 * @author Jan.Marcel.Janik [©2015]
 */
public final class Util {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final Random RANDOM;

    // <- Static ->
    static {
        RANDOM = new Random();
    }

    // <- Constructor ->
    private Util() {
        throw new IllegalStateException("Do not instantiate.");
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->

    // <- Static ->
    public static byte[] IntegerToByteArray(final int value) {
        return new byte[]{(byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) (value)};
    }

    public static int ByteArrayToInteger(final byte... buffer) {
        final int mask = 0b00000000_00000000_00000000_11111111;

        return (buffer[0] & mask) << 24 |
                (buffer[1] & mask) << 16 |
                (buffer[2] & mask) << 8 |
                (buffer[3] & mask);
    }

    public static byte[] LongToByteArray(final long value) {
        return new byte[]{(byte) (value >> 56), (byte) (value >> 48), (byte) (value >> 40), (byte) (value >> 32), (byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) (value)};
    }

    public static long ByteArrayToLong(final byte... buffer) {
        final long mask = 0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_11111111;

        return (buffer[0] & mask) << 56 |
                (buffer[1] & mask) << 48 |
                (buffer[2] & mask) << 40 |
                (buffer[3] & mask) << 32 |
                (buffer[4] & mask) << 24 |
                (buffer[5] & mask) << 16 |
                (buffer[6] & mask) << 8 |
                (buffer[7] & mask);
    }

    public static byte[] FloatToByteArray(final float value) {
        return IntegerToByteArray(FloatToIntBits(value));
    }

    public static int FloatToIntBits(final float value) {
        return floatToIntBits(value);
    }

    public static float ByteArrayToFloat(final byte... buffer) {
        return intBitsToFloat(ByteArrayToInteger(buffer));
    }

    public static String Format(final String format, final Object... args) {
        return format(US, format, args);
    }

    public static int RandomU_Int() {
        final int r = RANDOM.nextInt();

        return (r < 0) ? -r : r;
    }

    public static String Path(final String... subdirectories) {
        final StringBuilder b = new StringBuilder();

        for (final String dir : subdirectories)
            b.append(dir).append(separator);

        return b.toString();
    }

    public static String GetResourcePath(final String... subdirectories) {
        final StringBuilder b = new StringBuilder();

        b.append("/");

        for (final String dir : subdirectories)
            b.append(dir).append("/");

        return b.toString();
    }

    public static String FormatNumber(final float value) {
        final String s = Format("%.2f", value);
        final String result;

        final int index = s.indexOf(".");

        if (index != -1) {
            final String a = s.substring(0, index);
            final int lengthS = s.length();
            final StringBuilder b = new StringBuilder(lengthS + lengthS / 3);

            final StringCharacterIterator it = new StringCharacterIterator(a);

            int i = 0;
            char c = it.current();
            while (c != StringCharacterIterator.DONE) {
                b.append(c);

                final int lengthA = a.length();
                if (lengthA > 3 && i < lengthA - 2)
                    if (i++ % 3 == 0)
                        b.append('_');

                c = it.next();
            }

            result = b.toString() + s.substring(index);
        } else
            result = s;

        return result;
    }

    public static int SecondsToTicks(final int tps, final float seconds) {
        return (int) ((float) tps * seconds);
    }

    public static String GetPropertyFileLocation(final String name) {
        return PROPERTY_FILE_LOCATION + name.toLowerCase().replaceAll("\\s", "_") + PROPERTY_FILE_FILE_EXTENSION;
    }
}

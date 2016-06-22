package de.janik.softengine.util;
// <- Import ->

// <- Static_Import ->

import static java.lang.Integer.*;

/**
 * A <b>A_RGB</b> color representation, color values are stored as 32bit floating-point values to eas integration into OpenGL based systems. A color may be represented by floating-point values in
 * the range from 0.0f - 1.0f or integer values in the range from 0 - 255.<br>
 *
 * @author Jan.Marcel.Janik [©2014]
 */
public class ColorARGB {
    // <- Public ->

    // <- Protected ->

    // <- Private->
    /**
     * Constant, used to access the alpha-component of the color.
     */
    private static final int _ALPHA;
    /**
     * Constant, used to access the red-component of the color.
     */
    private static final int _RED;
    /**
     * Constant, used to access the green-component of the color.
     */
    private static final int _GREEN;
    /**
     * Constant, used to access the blue-component of the color.
     */
    private static final int _BLUE;
    /**
     * Constant, representing 100% alpha (opaque).
     */
    private static final float F_ALPHA;

    /**
     * Array, used to store the 4 color-component's.
     */
    private float[] color;

    /**
     * The color 'transparent'.
     */
    public static final ColorARGB TRANSPARENT;
    /**
     * The color 'light pink'.
     */
    public static final ColorARGB LIGHT_PINK;
    /**
     * The color 'pink'.
     */
    public static final ColorARGB PINK;
    /**
     * The color 'crimson'.
     */
    public static final ColorARGB CRIMSON;
    /**
     * The color 'hot pink'.
     */
    public static final ColorARGB HOT_PINK;
    /**
     * The color 'deep pink'.
     */
    public static final ColorARGB DEEP_PINK;
    /**
     * The color 'orchid'.
     */
    public static final ColorARGB ORCHID;
    /**
     * The color 'violet'.
     */
    public static final ColorARGB VIOLET;
    /**
     * The color 'magenta'.
     */
    public static final ColorARGB MAGENTA;
    /**
     * The color 'purple'.
     */
    public static final ColorARGB PURPLE;
    /**
     * The color 'dark violet'.
     */
    public static final ColorARGB DARK_VIOLET;
    /**
     * The color 'indigo'.
     */
    public static final ColorARGB INDIGO;
    /**
     * The color 'slate blue'.
     */
    public static final ColorARGB SLATE_BLUE;
    /**
     * The color 'navy'.
     */
    public static final ColorARGB NAVY;
    /**
     * The color 'midnight blue'.
     */
    public static final ColorARGB MIDNIGHT_BLUE;
    /**
     * The color 'blue'.
     */
    public static final ColorARGB BLUE;
    /**
     * The color 'dark blue'.
     */
    public static final ColorARGB DARK_BLUE;
    /**
     * The color 'royal blue'.
     */
    public static final ColorARGB ROYAL_BLUE;
    /**
     * The color 'light steel blue'.
     */
    public static final ColorARGB LIGHT_STEEL_BLUE;
    /**
     * The color 'steel blue'.
     */
    public static final ColorARGB STEEL_BLUE;
    /**
     * The color 'sky blue'.
     */
    public static final ColorARGB SKY_BLUE;
    /**
     * The color 'light blue'.
     */
    public static final ColorARGB LIGHT_BLUE;
    /**
     * The color 'cadet blue'.
     */
    public static final ColorARGB CADET_BLUE;
    /**
     * The color 'dark turquise'.
     */
    public static final ColorARGB DARK_TURQUISE;
    /**
     * The color 'cyan'.
     */
    public static final ColorARGB CYAN;
    /**
     * The color 'dark slate gray'.
     */
    public static final ColorARGB DARK_SLATE_GRAY;
    /**
     * The color 'turquise'.
     */
    public static final ColorARGB TURQUISE;
    /**
     * The color 'awuamatina'.
     */
    public static final ColorARGB AQUAMARINA;
    /**
     * The color 'spring green'.
     */
    public static final ColorARGB SPRING_GREEN;
    /**
     * The color 'sea green'.
     */
    public static final ColorARGB SEA_GREEN;
    /**
     * The color 'light green'.
     */
    public static final ColorARGB LIGHT_GREEN;
    /**
     * The color 'dark see green'.
     */
    public static final ColorARGB DARK_SEE_GREEN;
    /**
     * The color 'lime green'.
     */
    public static final ColorARGB LIME_GREEN;
    /**
     * The color 'lime'.
     */
    public static final ColorARGB LIME;
    /**
     * The color 'green'.
     */
    public static final ColorARGB GREEN;
    /**
     * The color 'dark green'.
     */
    public static final ColorARGB DARK_GREEN;
    /**
     * The color 'lawn green'.
     */
    public static final ColorARGB LAWN_GREEN;
    /**
     * The color 'green yellow'.
     */
    public static final ColorARGB GREEN_YELLOW;
    /**
     * The color 'yellow green'.
     */
    public static final ColorARGB YELLOW_GREEN;
    /**
     * The color 'dark olive green'.
     */
    public static final ColorARGB DARK_OLIVE_GREEN;
    /**
     * The color 'ivory'.
     */
    public static final ColorARGB IVORY;
    /**
     * The color 'yellow'.
     */
    public static final ColorARGB YELLOW;
    /**
     * The color 'olive'.
     */
    public static final ColorARGB OLIVE;
    /**
     * The color 'dark khaki'.
     */
    public static final ColorARGB DARK_KHAKI;
    /**
     * The color 'khaki'.
     */
    public static final ColorARGB KHAKI;
    /**
     * The color 'gold'.
     */
    public static final ColorARGB GOLD;
    /**
     * The color 'cornsilk'.
     */
    public static final ColorARGB CORNSILK;
    /**
     * The color 'goldenrod'.
     */
    public static final ColorARGB GOLDENROD;
    /**
     * The color 'floral white'.
     */
    public static final ColorARGB FLORAL_WHITE;
    /**
     * The color 'orange'.
     */
    public static final ColorARGB ORANGE;
    /**
     * The color 'moccasin'.
     */
    public static final ColorARGB MOCCASIN;
    /**
     * The color 'antique white'.
     */
    public static final ColorARGB ANTIQUE_WHITE;
    /**
     * The color 'burly wood'.
     */
    public static final ColorARGB BURLY_WOOD;
    /**
     * The color 'dark orange'.
     */
    public static final ColorARGB DARK_ORANGE;
    /**
     * The color 'peru'.
     */
    public static final ColorARGB PERU;
    /**
     * The color 'chocolate'.
     */
    public static final ColorARGB CHOCOLATE;
    /**
     * The color 'saddle brown'.
     */
    public static final ColorARGB SADDLE_BROWN;
    /**
     * The color 'coral'.
     */
    public static final ColorARGB CORAL;
    /**
     * The color 'orange red'.
     */
    public static final ColorARGB ORANGE_RED;
    /**
     * The color 'tomato'.
     */
    public static final ColorARGB TOMATO;
    /**
     * The color 'snow white'.
     */
    public static final ColorARGB SNOW_WHITE;
    /**
     * The color 'rosy brown'.
     */
    public static final ColorARGB ROSY_BROWN;
    /**
     * The color 'red'.
     */
    public static final ColorARGB RED;
    /**
     * The color 'firebrick red'.
     */
    public static final ColorARGB FIREBRICK_RED;
    /**
     * The color 'dark red'.
     */
    public static final ColorARGB DARK_RED;
    /**
     * The color 'white'.
     */
    public static final ColorARGB WHITE;
    /**
     * The color 'smoke white'.
     */
    public static final ColorARGB SMOKE_WHITE;
    /**
     * The color 'light gray'.
     */
    public static final ColorARGB LIGHT_GRAY;
    /**
     * The color 'silver'.
     */
    public static final ColorARGB SILVER;

    /**
     * The color 'silver gray'
     */
    public static final ColorARGB SILVER_GRAY;

    /**
     * The color 'dark gray'.
     */
    public static final ColorARGB DARK_GRAY;
    /**
     * The color 'gray'.
     */
    public static final ColorARGB GRAY;
    /**
     * The color 'dim gray'.
     */
    public static final ColorARGB DIM_GRAY;
    /**
     * The color 'black'.
     */
    public static final ColorARGB BLACK;

    // <- Static ->
    static {
        _ALPHA = 0;
        _RED = 1;
        _GREEN = 2;
        _BLUE = 3;

        F_ALPHA = 1.0f;

        TRANSPARENT = Decode("#00_00_00_00");
        LIGHT_PINK = Decode("#FF_B6_C1");
        PINK = Decode("#FF_C0_CB");
        CRIMSON = Decode("#DC_14_3C");
        HOT_PINK = Decode("#FF_69_B4");
        DEEP_PINK = Decode("#FF_14_93");
        ORCHID = Decode("#DA_70_D6");
        VIOLET = Decode("#EE_82_EE");
        MAGENTA = Decode("#FF_00_FF");
        PURPLE = Decode("#80_00_80");
        DARK_VIOLET = Decode("#94_00_D3");
        INDIGO = Decode("#4B_00_82");
        SLATE_BLUE = Decode("#7B_68_EE");
        NAVY = Decode("#00_00_80");
        MIDNIGHT_BLUE = Decode("#19_19_70");
        BLUE = Decode("#00_00_FF");
        DARK_BLUE = Decode("#00_00_8B");
        ROYAL_BLUE = Decode("#41_69_E1");
        LIGHT_STEEL_BLUE = Decode("#B0_C4_DE");
        STEEL_BLUE = Decode("#46_82_B4");
        SKY_BLUE = Decode("#00_BF_FF");
        LIGHT_BLUE = Decode("#AD_D8_E6");
        CADET_BLUE = Decode("#5F_9E_A0");
        DARK_TURQUISE = Decode("#00_CE_D1");
        CYAN = Decode("#00_FF_FF");
        DARK_SLATE_GRAY = Decode("#2F_2F_2F");
        TURQUISE = Decode("#40_E0_D0");
        AQUAMARINA = Decode("#7F_FF_D4");
        SPRING_GREEN = Decode("#00_FF_7F");
        SEA_GREEN = Decode("#2E_8B_57");
        LIGHT_GREEN = Decode("#90_EE_90");
        DARK_SEE_GREEN = Decode("#8F_BC_8F");
        LIME_GREEN = Decode("#32_CD_32");
        LIME = Decode("#00_FF_00");
        GREEN = Decode("#00_80_00");
        DARK_GREEN = Decode("#00_64_00");
        LAWN_GREEN = Decode("#7F_FF_00");
        GREEN_YELLOW = Decode("#AD_FF_2F");
        YELLOW_GREEN = Decode("#9A_CD_32");
        DARK_OLIVE_GREEN = Decode("#55_6B_2F");
        IVORY = Decode("#FF_FF_F0");
        YELLOW = Decode("#FF_FF_00");
        OLIVE = Decode("#80_80_00");
        DARK_KHAKI = Decode("BD_B7_6B");
        KHAKI = Decode("#F0_E6_8C");
        GOLD = Decode("#FF_D7_00");
        CORNSILK = Decode("#FF_F8_DC");
        GOLDENROD = Decode("#DA_A5_20");
        FLORAL_WHITE = Decode("#FF_FA_F0");
        ORANGE = Decode("#FF_A5_00");
        MOCCASIN = Decode("#FF_E4_B5");
        ANTIQUE_WHITE = Decode("#FA_EB_D7");
        BURLY_WOOD = Decode("#DE_D8_87");
        DARK_ORANGE = Decode("#FF_8C_00");
        PERU = Decode("#CD_85_3F");
        CHOCOLATE = Decode("#D2_69_1E");
        SADDLE_BROWN = Decode("#8B_45_13");
        CORAL = Decode("#FF_7F_50");
        ORANGE_RED = Decode("#FF_45_00");
        TOMATO = Decode("#FF_63_47");
        SNOW_WHITE = Decode("#FF_FA_FA");
        ROSY_BROWN = Decode("#BC_8F_8F");
        RED = Decode("#FF_00_00");
        FIREBRICK_RED = Decode("#B2_22_22");
        DARK_RED = Decode("#8B_00_00");
        WHITE = Decode("#FF_FF_FF");
        SMOKE_WHITE = Decode("#F5_F5_F5");
        LIGHT_GRAY = Decode("#D3_D3_D3");
        SILVER = Decode("#C0_C0_C0");
        SILVER_GRAY = Decode("#A2_A2_A2");
        DARK_GRAY = Decode("#A9_A9_A9");
        GRAY = Decode("#80_80_80");
        DIM_GRAY = Decode("#69_69_69");
        BLACK = Decode("#00_00_00");
    }

    // <- Constructor ->

    /**
     * Create's a color with the given red, green, blue and alpha-component's.
     *
     * @param red   The red-component of the color.
     * @param green The green-component of the color.
     * @param blue  The blue-component of the color.
     * @param alpha The alpha-component of the color.
     */
    public ColorARGB(final float red, final float green, final float blue, final float alpha) {
        color = new float[4];

        color[_RED] = red;
        color[_GREEN] = green;
        color[_BLUE] = blue;
        color[_ALPHA] = alpha;
    }

    /**
     * Create's a color with the given red, green and blue-component's. <br>
     * The alpha-component will be initialized to the private-constant
     * 'F_ALPHA'.<br>
     * The resulting color will be <b>non transparent</b> (opaque)
     *
     * @param red   The red-component of the color.
     * @param green The green-component of the color.
     * @param blue  The blue-component of the color.
     * @see ColorARGB#F_ALPHA
     */
    public ColorARGB(final float red, final float green, final float blue) {
        this(red, green, blue, F_ALPHA);
    }

    /**
     * Create's a color with the given red, green, blue and alpha-component's.
     *
     * @param red   The red-component of the color.
     * @param green The green-component of the color.
     * @param blue  The blue-component of the color.
     * @param alpha The alpha-component of the color.
     */
    public ColorARGB(final int red, final int green, final int blue, final int alpha) {
        this(red / 255.0f, green / 255.0f, blue / 255.0f, alpha / 255.0f);
    }

    /**
     * Create's a color with the given red, green and blue-component's. <br>
     * The alpha-component will be initialized to the private-constant
     * 'F_ALPHA'.<br>
     * The resulting color will be <b>non transparent</b> (opaque)
     *
     * @param red   The red-component of the color.
     * @param green The green-component of the color.
     * @param blue  The blue-component of the color.
     * @see ColorARGB#F_ALPHA
     */
    public ColorARGB(final int red, final int green, final int blue) {
        this(red, green, blue, 255);
    }

    /**
     * Creates a new
     *
     * @param c The source-color.
     */
    public ColorARGB(ColorARGB c) {
        this(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    protected Object clone() {
        return new ColorARGB(this);
    }

    @Override
    public int hashCode() {
        return asInt(getAlpha()) + asInt(getRed()) + asInt(getGreen()) + asInt(getBlue());
    }

    @Override
    public boolean equals(final Object o) {
        boolean ret = true;

        if ((o != this) || (o == null) || (o.getClass() != getClass()))
            ret = false;
        else {
            ColorARGB other = (ColorARGB) o;

            if ((other.getAlpha() != getAlpha()) || (other.getRed() != getRed()) || (other.getGreen() != getGreen()) || (other.getBlue() != getBlue()))
                ret = false;
        }

        return ret;
    }

    @Override
    public String toString() {
        return "Color[Red: " + asInt(getRed()) + " Green: " + asInt(getGreen()) + " Blue: " + asInt(getBlue()) + " Alpha: " + asInt(getAlpha()) + "]";
    }

    /**
     * Convert's a float value from the range 0.0f - 1.0f to 0 - 255.
     *
     * @param color The color value to be converted
     * @return The integer-representation of the given color as a float-value.
     */
    private int asInt(final float color) {
        return (int) (color * 255 + 0.5f);
    }

    // <- Getter & Setter ->

    /**
     * Return's the red-component of the color.
     *
     * @return The red-component of the color.
     */
    public float getRed() {
        return color[_RED];
    }

    /**
     * Set's the red-component of the color.
     *
     * @param red The red-component of the color.
     */
    public void setRed(final float red) {
        color[_RED] = red;
    }

    /**
     * Return's the green-component of the color.
     *
     * @return The green-component of the color.
     */
    public float getGreen() {
        return color[_GREEN];
    }

    /**
     * Set's the green-component of the color.
     *
     * @param green The green-component of the color.
     */
    public void setGreen(final float green) {
        color[_GREEN] = green;
    }

    /**
     * Return's the blue-component of the color.
     *
     * @return The blue-component of the color.
     */
    public float getBlue() {
        return color[_BLUE];
    }

    /**
     * Set's the blue-component of the color.
     *
     * @param blue The blue-component of the color.
     */
    public void setBlue(final float blue) {
        color[_BLUE] = blue;
    }

    /**
     * Return's the alpha-component of the color.
     *
     * @return The alpha-component of the color.
     */
    public float getAlpha() {
        return color[_ALPHA];
    }

    /**
     * Set's the alpha-component of the color.
     *
     * @param alpha The alpha-component of the color.
     */
    public void setAlpha(final float alpha) {
        color[_ALPHA] = alpha;
    }

    public int getRGB() {
        return (asInt(getAlpha()) & 0xFF) << 24 |
                (asInt(getRed()) & 0xFF) << 16 |
                (asInt(getGreen()) & 0xFF) << 8 |
                (asInt(getBlue()) & 0xFF);
    }

    // <- Static ->

    /**
     * Convert's a color represented by a Color_ARGB object into a
     * <b>'java.awt.Color'</b> color
     *
     * @param color The color to be converted.
     * @return The <b>'java.awt.Color'</b> representation of a RGB_A color.
     */
    public static java.awt.Color ToAWT_Color(final ColorARGB color) {
        return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    /**
     * Convert's a String into a Color_ARGB object.<br>
     * Example-Calls:<br>
     * Decode("#FF_00_00_FF")<br>
     * Decode("FF0000FF")
     *
     * @param color The color string to be converted.
     * @return A Color_ARGB object.
     * @see Integer#decode(String)
     */
    public static ColorARGB Decode(String color) {
        color = color.replaceAll("\\s", "");

        if (color.startsWith("#"))
            color = color.substring(1, color.length());

        color = color.replace("_", "");

        for (int i = color.length(); i < 8; i += 2)
            color += "FF";

        int red = (decode("#" + color.substring(0, 2)));
        int green = (decode("#" + color.substring(2, 4)));
        int blue = (decode("#" + color.substring(4, 6)));
        int alpha = (decode("#" + color.substring(6, 8)));

        return new ColorARGB(red, green, blue, alpha);
    }
}

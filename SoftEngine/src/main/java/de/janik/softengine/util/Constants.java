package de.janik.softengine.util;
// <- Import ->

// <- Static_Import ->

import static de.janik.softengine.util.Util.GetResourcePath;
import static de.janik.softengine.util.Util.Path;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Constants {
    // <- Public ->
    public static final int BYTES_PER_PIXEL;

    public static final String ANIMATION_FILE_FILE_EXTENSION;
    public static final String FONT_SOURCE_CODE_PRO_LOCATION;

    public static final String PROPERTY_FILE_LOCATION;
    public static final String PROPERTY_FILE_FILE_EXTENSION;

    public static final String GAME_WINDOW_WIDTH_PROPERTY;
    public static final String GAME_WINDOW_HEIGHT_PROPERTY;
    public static final String GAME_WINDOW_POS_X_PROPERTY;
    public static final String GAME_WINDOW_POS_Y_PROPERTY;

    // <- Protected ->
    // <- Private->

    // <- Static ->
    static {
        BYTES_PER_PIXEL = Integer.BYTES;

        ANIMATION_FILE_FILE_EXTENSION = ".png";
        FONT_SOURCE_CODE_PRO_LOCATION = GetResourcePath("res", "font") + "SourceCodePro-Medium.ttf";

        PROPERTY_FILE_LOCATION = Path("res");
        PROPERTY_FILE_FILE_EXTENSION = ".properties";

        GAME_WINDOW_WIDTH_PROPERTY = "window_width";
        GAME_WINDOW_HEIGHT_PROPERTY = "window_height";
        GAME_WINDOW_POS_X_PROPERTY = "window_pos_x";
        GAME_WINDOW_POS_Y_PROPERTY = "window_pos_y";
    }

    // <- Constructor ->
    private Constants() {
        throw new IllegalStateException("Do not instantiate !~!");
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}

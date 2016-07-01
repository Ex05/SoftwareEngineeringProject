package de.presidente.jungle_king.util;
// <- Import ->

// <- Static_Import ->

import static de.janik.softengine.util.Util.Path;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Constants {
    // <- Public ->
    public static final String GAME_TITLE;
    public static final String IMAGE_PREGAME_LOBBY_MAP_PREVIEW_PATH;

    public static final int TICKS_PER_SECOND;

    // <- Protected ->
    // <- Private->

    // <- Static ->
    static {
        GAME_TITLE = "Jungle King [alpha v0.1]";
        IMAGE_PREGAME_LOBBY_MAP_PREVIEW_PATH = Path(".", "res", "img", "preGameLobbyIcon.png");

        TICKS_PER_SECOND = 60;
    }

    // <- Constructor ->
    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
package de.presidente.jungle_king;

// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.InputManager;
import de.janik.softengine.game.Game;
import de.janik.windowing.Window;
import de.presidente.jungle_king.net.ConnectionManager;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import static de.janik.softengine.util.Constants.FONT_SOURCE_CODE_PRO_LOCATION;
import static de.janik.windowing.WindowType.*;
import static de.presidente.jungle_king.util.Constants.GAME_TITLE;
import static de.presidente.jungle_king.util.Constants.TICKS_PER_SECOND;
import static de.presidente.jungle_king.util.Resources.SOURCE_CODE_PRO;
import static java.awt.Font.TRUETYPE_FONT;
import static java.awt.event.KeyEvent.VK_F10;
import static java.awt.event.KeyEvent.VK_F11;

/**
 * @author Jan.Marcel.Janik, Gorden Kappenberg [©2016]
 */
public final class JungleKingGame extends Game {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private ConnectionManager server;

    // <- Static ->

    // <- Constructor ->
    public JungleKingGame() {
        super(GAME_TITLE);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void init() {
        try {
            SOURCE_CODE_PRO = Font.createFont(TRUETYPE_FONT, getClass().getResourceAsStream(FONT_SOURCE_CODE_PRO_LOCATION));
        } catch (final FontFormatException | IOException e) {
            e.printStackTrace();
        }

        gameStates.add(new Intro(this));
        gameStates.add(new Login(this));

        server = new ConnectionManager("janik-bau.nrw", 5585);

        switchState(Intro.class);
    }

    @Override
    public Window constructWindow() {
        final Window window = new Window();

        window.setType(UTILITY).setSize(1280, 720);
        window.setAlwaysOnTop(true);

        return window;
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
        super.tick(ticks, engine);

        final InputManager input = engine.getInput();

        if (input.isKeyDown(VK_F11))
            engine.setBorderLessFullScreenMode();

        if (input.isKeyDown(VK_F10))
            engine.toggleFullScreen();
    }

    @Override
    public void destroy() {
        server.close();
    }

    @Override
    public int getDesiredTicksPerSecond() {
        return TICKS_PER_SECOND;
    }

    @Override
    public int getDisplayScaleFactor() {
        return 1;
    }

    // <- Getter & Setter ->

    // <- Static ->
}

package de.janik.softengine;
// <- Import ->

// <- Static_Import ->

import de.janik.propertyFile.PropertyFile;
import de.janik.propertyFile.exception.NoSuchEntryException;
import de.janik.softengine.game.Game;
import de.janik.windowing.Window;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static de.janik.softengine.util.Constants.GAME_WINDOW_HEIGHT_PROPERTY;
import static de.janik.softengine.util.Constants.GAME_WINDOW_POS_X_PROPERTY;
import static de.janik.softengine.util.Constants.GAME_WINDOW_POS_Y_PROPERTY;
import static de.janik.softengine.util.Constants.GAME_WINDOW_WIDTH_PROPERTY;
import static de.janik.softengine.util.Timer.GetLowResolutionTime;
import static de.janik.softengine.util.Timer.GetTime;
import static de.janik.softengine.util.Timer.MILLIS_PER_SECOND;
import static de.janik.softengine.util.Timer.NANOS_PER_SECOND;
import static de.janik.windowing.WindowType.BORDERLESS_NO_CONTROLS;
import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

/**
 * @author Jan.Marcel.Janik, Gorden.Kappenberg [©2016]
 */
public class Engine implements Runnable {
    // <- Public ->

    // <- Protected ->

    // <- Private->
    private final Window window;

    private final Canvas canvas;

    private final Screen screen;

    private final Game game;

    private final InputManager input;

    private final Thread thread;

    private volatile boolean running = false;

    private long ticks;

    private int tps, fps;
    private int framesPerSecond, ticksPerSecond;

    private boolean fullScreen = false;

    // <- Static ->

    // <- Constructor ->
    public Engine(final Game game) {
        this.game = game;

        game.setEngineCallback(this);

        window = game.constructWindow();

        window.addShutDownAction(this::stop);
        setWindowSize(window.getWidth(), window.getHeight());

        input = new InputManager(this);

        canvas = new Canvas(window, game.getDisplayScaleFactor());
        canvas.setDragableByMouse(true, window);
        canvas.addKeyListener(input);
        canvas.addMouseListener(input);

        screen = new Screen(canvas);

        window.setViewComponent(canvas);

        thread = new Thread(this, this.getClass().getName());

        ticks = 0;

        window.setTitle(game.getName());
    }

    // <- Abstract ->

    // <- Object ->
    private void init() {
        initDisplay();

        initProperties();

        game.init();
    }

    private void initProperties() {
        final PropertyFile properties = game.getProperties();

        if (!properties.contains(GAME_WINDOW_WIDTH_PROPERTY))
            properties.addIntProperty(GAME_WINDOW_WIDTH_PROPERTY);

        if (!properties.contains(GAME_WINDOW_HEIGHT_PROPERTY))
            properties.addIntProperty(GAME_WINDOW_HEIGHT_PROPERTY);

        if (!properties.contains(GAME_WINDOW_POS_X_PROPERTY))
            properties.addIntProperty(GAME_WINDOW_POS_X_PROPERTY);

        if (!properties.contains(GAME_WINDOW_POS_Y_PROPERTY))
            properties.addIntProperty(GAME_WINDOW_POS_Y_PROPERTY);
    }

    private void initDisplay() {
        final PropertyFile properties = game.getProperties();

        if (properties.contains(GAME_WINDOW_WIDTH_PROPERTY) && properties.contains(GAME_WINDOW_HEIGHT_PROPERTY))
            try {
                window.setLocation(properties.getProperty(GAME_WINDOW_WIDTH_PROPERTY).asInt().get(), properties.getProperty(GAME_WINDOW_HEIGHT_PROPERTY).asInt().get());
            } catch (final NoSuchEntryException | IOException e) {
                e.printStackTrace();
            }

        if (properties.contains(GAME_WINDOW_POS_X_PROPERTY) && properties.contains(GAME_WINDOW_POS_Y_PROPERTY))
            try {
                window.setLocation(properties.getProperty(GAME_WINDOW_POS_X_PROPERTY).asInt().get(), properties.getProperty(GAME_WINDOW_POS_Y_PROPERTY).asInt().get());
            } catch (final NoSuchEntryException | IOException e) {
                e.printStackTrace();
            }
        else
            window.center();
    }

    private void destroy() {
        window.dispose();

        final PropertyFile properties = game.getProperties();

        final Point location = window.getLocation();

        try {
            properties.getProperty(GAME_WINDOW_WIDTH_PROPERTY).asInt().set(window.getWidth());
            properties.getProperty(GAME_WINDOW_HEIGHT_PROPERTY).asInt().set(window.getHeight());
            properties.getProperty(GAME_WINDOW_POS_X_PROPERTY).asInt().set((int) location.getX());
            properties.getProperty(GAME_WINDOW_POS_Y_PROPERTY).asInt().set((int) location.getY());
        } catch (final NoSuchEntryException | IOException e) {
            e.printStackTrace();
        }

        game.destroy();
    }

    public void run() {
        init();

        window.setVisible(true);

        long lastTime = GetTime();

        long updateTimer = GetLowResolutionTime();

        double tickDelta = 0.0D;

        final double NANOS_PER_TICK = NANOS_PER_SECOND / game.getDesiredTicksPerSecond();

        while (this.running) {
            long currentTime = GetTime();

            tickDelta += (currentTime - lastTime) / NANOS_PER_TICK;

            lastTime = currentTime;

            while (tickDelta >= 1) {
                tick();

                if (GetLowResolutionTime() - updateTimer > MILLIS_PER_SECOND) {
                    updateTimer += MILLIS_PER_SECOND;

                    framesPerSecond = fps;
                    ticksPerSecond = tps;

                    tps = fps = 0;
                }

                tps++;
                tickDelta--;
            }

            render();
            fps++;
        }

        destroy();
    }

    private void tick() {
        ticks++;

        game.tick(ticks, this);
    }

    private void render() {
        screen.fillScreen(game.getBackgroundColor());

        game.render(screen);

        canvas.render(screen);
    }

    public synchronized void start() {
        if (running)
            return;

        running = true;

        thread.start();
    }

    public synchronized void stop() {
        if (!running)
            return;

        running = false;
    }

    public void toggleFullScreen() {
        setFullScreenExclusiveMode(!fullScreen);
    }

    public void keyPressed(final KeyEvent e) {
        game.keyPressed(e);
    }

    // <- Getter & Setter ->
    public void setWindowSize(final int width, final int height) {
        window.setSize(width * game.getDisplayScaleFactor(), height * game.getDisplayScaleFactor());
    }

    public Window getUnderlayingWindow() {
        return window;
    }

    public int getScreenWidth() {
        return canvas.getWidth();
    }

    public int getScreenHeight() {
        return canvas.getHeight();
    }

    public void setBorderLessFullScreenMode() {
        // TODO: When switching to fullScreen, make sure that "java.lang.IllegalStateException: Buffers have not been created" will not be thrown.

        if (!fullScreen) {
            final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            window.dispose();
            window.setType(BORDERLESS_NO_CONTROLS).setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
            window.setLocation(0, 0).setViewComponent(canvas).setVisible(true);

            fullScreen = true;
        }
    }

    public void setFullScreenExclusiveMode(final boolean fullScreen) {
        final GraphicsEnvironment ge = getLocalGraphicsEnvironment();
        final GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (fullScreen) {
            if (!gd.isFullScreenSupported()) {
                System.err.println("ERROR: FSE not supported!");

                return;
            }

            gd.setFullScreenWindow(window.getSwingComponent());

            // TODO: Change Display resolution ?
            /*final DisplayMode dm = new DisplayMode(1024, 768, 32, DisplayMode.REFRESH_RATE_UNKNOWN);
            gd.setDisplayMode(dm);*/
        } else
            gd.setFullScreenWindow(null);

        this.fullScreen = fullScreen;
    }

    public int getFps() {
        return framesPerSecond;
    }

    public int getTps() {
        return ticksPerSecond;
    }

    public long getTotalTicks() {
        return ticks;
    }

    public InputManager getInput() {
        return input;
    }

    public int getDisplayScaleFactor() {
        return game.getDisplayScaleFactor();
    }

    // <- Static ->
}

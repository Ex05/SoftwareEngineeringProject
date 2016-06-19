package de.janik.softengine.game;
// <- Import ->

// <- Static_Import ->

import de.janik.propertyFile.PropertyFile;
import de.janik.softengine.Engine;
import de.janik.softengine.InputManager;
import de.janik.softengine.Screen;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.entity.Entity;
import de.janik.softengine.util.ColorARGB;
import de.janik.windowing.Window;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.janik.softengine.util.ColorARGB.BLACK;
import static de.janik.softengine.util.Util.GetPropertyFileLocation;
import static java.util.Collections.sort;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public abstract class Game {
    // <- Public ->

    // <- Protected ->
    protected Engine engine;

    protected final String name;

    protected final List<Entity> entities;
    protected final List<DrawableEntity> drawableEntities;

    protected final List<KeyEvent> eventBuffer;

    protected PropertyFile properties;

    protected ColorARGB backgroundColor;

    protected List<State> gameStates;

    protected State currentState;

    // <- Private->
    private State nextState;

    // <- Static ->

    // <- Constructor ->
    protected Game(final String name) {
        this.name = name;

        try {
            properties = PropertyFile.CreateNewFile(GetPropertyFileLocation(name));
        } catch (final IOException e) {
            e.printStackTrace();
        }

        entities = new ArrayList<>(256);
        drawableEntities = new ArrayList<>(256);

        eventBuffer = new ArrayList<>(960 / getDesiredTicksPerSecond());

        backgroundColor = BLACK;

        gameStates = new ArrayList<>(2);
    }

    // <- Abstract ->
    public abstract void init();

    public abstract Window constructWindow();

    public abstract int getDesiredTicksPerSecond();

    public abstract int getDisplayScaleFactor();

    public abstract void destroy();

    // <- Object ->
    public void add(final DrawableEntity entity) {
        drawableEntities.add(entity);

        entities.add(entity);
    }

    public void add(final Entity entity) {
        entities.add(entity);
    }

    public void remove(final DrawableEntity entity) {
        drawableEntities.remove(entity);

        entities.remove(entity);
    }

    public void remove(final Entity entity) {
        entities.remove(entity);
    }

    public void removeAll() {
        drawableEntities.clear();

        entities.clear();
    }

    public void tick(final long ticks, final Engine engine) {
        final InputManager input = engine.getInput();

        if (nextState != null)
            _switchState(nextState);

        sort(entities);

        currentState.tick(ticks, engine);

        for (final Entity e : entities)
            e.handleMouseOver(input);

        for (int i = entities.size() - 1; i >= 0; i--) {
            final Entity e = entities.get(i);

            if (e.handleMousePress(input))
                break;
        }

        synchronized (eventBuffer) {
            for (final KeyEvent e : eventBuffer)
                for (final Entity entity : entities)
                    entity.pressKey(e);

            eventBuffer.clear();
        }

        for (final Entity e : entities)
            e.tick(ticks, input);
    }

    public void render(final Screen screen) {
        sort(drawableEntities);

        screen.render(drawableEntities);
    }

    public void switchState(final Class<?> state) {
        switchState(getState(state));
    }

    public void switchState(final State state) {
        nextState = state;
    }

    public void _switchState(final State state) {
        removeAll();

        state.init();

        currentState = state;
        nextState = null;
    }

    public void keyPressed(final KeyEvent e) {
        synchronized (eventBuffer) {
            eventBuffer.add(e);
        }
    }

    // <- Getter & Setter ->
    public PropertyFile getProperties() {
        return properties;
    }

    public State getState(final Class<?> state) {
        return gameStates.stream().filter(s -> s.getClass().equals(state)).findAny().orElse(null);
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngineCallback(final Engine engine) {
        this.engine = engine;
    }

    public String getName() {
        return name;
    }

    public ColorARGB getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(final ColorARGB backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    // <- Static ->
}

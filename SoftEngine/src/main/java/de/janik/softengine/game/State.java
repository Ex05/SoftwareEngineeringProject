package de.janik.softengine.game;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public abstract class State {
    // <- Public ->

    // <- Protected ->
    protected Engine engine;

    protected final Game game;

    // <- Private->
    // <- Static ->

    // <- Constructor ->
    public State(final Game game) {
        engine = game.getEngine();

        this.game = game;
    }

    // <- Abstract ->
    public abstract void init();

    public abstract void tick(final long ticks, final Engine engine);

    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}

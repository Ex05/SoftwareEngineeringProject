package de.janik.softengine.game;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.entity.Entity;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public abstract class State {
    // <- Public ->

    // <- Protected ->
    protected Engine engine;

    protected final Game game;

    // <- Private->
    private Entity actualFocused = null;
    // <- Static ->

    // <- Constructor ->
    public State(final Game game) {
        engine = game.getEngine();

        this.game = game;
    }

    // <- Abstract ->
    public abstract void init();

    public abstract void tick(final long ticks, final Engine engine);

    public void getFocus(Entity e) {
        if (actualFocused == null)
            actualFocused = e;
        else {
            actualFocused.setFocus(false);
            actualFocused = e;
            actualFocused.setFocus(true);
        }
    }

    ;
    // <- Object ->

    // <- Getter & Setter ->
    public Game getGame() {
        return game;
    }

    // <- Static ->
}

package de.presidente;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.game.Game;
import de.presidente.jungle_king.JungleKingGame;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Main {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    private Main() {
        throw new IllegalStateException("Do not instantiate.");
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->

    // <- Static ->
    public static void main(final String[] args) {
        final Game game = new JungleKingGame();

        final Engine engine = new Engine(game);
        engine.start();
    }
}

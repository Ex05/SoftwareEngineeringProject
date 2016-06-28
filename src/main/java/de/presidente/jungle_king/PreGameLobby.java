package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.game.State;

import static de.janik.softengine.util.ColorARGB.DARK_ORANGE;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class PreGameLobby extends State {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    public PreGameLobby(final JungleKingGame game) {
        super(game);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void init() {
        game.setBackgroundColor(DARK_ORANGE);
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
        super.tick(ticks, engine);
    }

    // <- Getter & Setter ->
    @Override
    public JungleKingGame getGame() {
        return (JungleKingGame) super.getGame();
    }


    // <- Static ->
}

package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.InputManager;
import de.janik.softengine.game.State;
import de.janik.softengine.util.ColorARGB;
import de.presidente.jungle_king.net.ConnectionManager;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Lobby extends State {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private ConnectionManager server;

    // <- Static ->

    // <- Constructor ->
    public Lobby(final JungleKingGame jungleKingGame) {
        super(jungleKingGame);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void init() {
        server = getGame().getServerConnection();

        game.setBackgroundColor(new ColorARGB(115, 195, 90));
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
        final InputManager inputManager = engine.getInput();


    }

    // <- Getter & Setter ->
    @Override
    public JungleKingGame getGame() {
        return (JungleKingGame) super.getGame();
    }

    // <- Static ->
}
